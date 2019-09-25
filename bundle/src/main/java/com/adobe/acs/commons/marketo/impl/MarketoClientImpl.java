/*
 * #%L
 * ACS AEM Commons Bundle
 * %%
 * Copyright (C) 2019 Adobe
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.adobe.acs.commons.marketo.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AUTH;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.marketo.MarketoClient;
import com.adobe.acs.commons.marketo.models.MarketoClientConfiguration;
import com.adobe.acs.commons.marketo.models.MarketoError;
import com.adobe.acs.commons.marketo.models.MarketoForm;
import com.adobe.acs.commons.marketo.models.MarketoFormResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of the MarketoClient using the REST API.
 */
@Component(service = MarketoClient.class)
public class MarketoClientImpl implements MarketoClient {

  private static final Logger log = LoggerFactory.getLogger(MarketoClientImpl.class);
  private static final int PAGE_SIZE = 200;
  private ObjectMapper mapper = new ObjectMapper();

  protected @Nonnull String doGet(@Nonnull String url, String bearerToken) throws IOException {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpGet httpGet = new HttpGet(url);
      if (StringUtils.isNotBlank(bearerToken)) {
        httpGet.setHeader(AUTH.WWW_AUTH_RESP, "Bearer " + bearerToken);
      }
      try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
        try (InputStream content = httpResponse.getEntity().getContent()) {
          return IOUtils.toString(content, StandardCharsets.UTF_8);
        }
      }
    }
  }

  public @Nonnull String getApiToken(@Nonnull MarketoClientConfiguration config) throws IOException {
    log.trace("getApiToken");
    String url = String.format(
        "https://%s/identity/oauth/token?grant_type=client_credentials&client_id=%s&client_secret=%s",
        config.getEndpointHost(), config.getClientId(), config.getClientSecret());
    String response = doGet(url, null);
    Map<?, ?> responseData = mapper.readValue(response, Map.class);
    return (String) responseData.get("access_token");

  }

  private @Nullable MarketoForm[] getFormApiPage(@Nonnull MarketoClientConfiguration config, @Nonnull String token,
      int page) throws IOException {
    log.trace("getFormApiPage({})", page);
    int offset = PAGE_SIZE * page;

    String url = String.format("https://%s/rest/asset/v1/forms.json?maxReturn=%s&offset=%s&status=approved",
        config.getEndpointHost(), PAGE_SIZE, offset);

    String responseText = doGet(url, token);
    MarketoFormResponse response = mapper.readValue(responseText, MarketoFormResponse.class);
    if (response.getErrors() != null && response.getErrors().length > 0) {
      throw new IOException("Retrieved errors in response: "
          + Arrays.stream(response.getErrors()).map(MarketoError::getMessage).collect(Collectors.joining(", ")));
    }
    if (!response.isSuccess()) {
      throw new IOException("Retrieved non-success response");
    }
    return response.getResult();
  }

  @Override
  public List<MarketoForm> getForms(@Nonnull MarketoClientConfiguration config) throws IOException {
    String apiToken = getApiToken(config);
    List<MarketoForm> forms = new ArrayList<>();
    for (int i = 0; true; i++) {
      MarketoForm[] page = getFormApiPage(config, apiToken, i);
      if (page == null || page.length == 0) {
        break;
      } else {
        Arrays.stream(page).forEach(forms::add);
      }
    }
    return forms;
  }

}
