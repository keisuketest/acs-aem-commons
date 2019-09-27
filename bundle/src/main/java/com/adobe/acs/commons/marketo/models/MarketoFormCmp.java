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
package com.adobe.acs.commons.marketo.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.WCMMode;

/**
 * Model for retriving the configuration values for the Marketo form component
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MarketoFormCmp {

  private static final Logger log = LoggerFactory.getLogger(MarketoFormCmp.class);

  @ValueMapValue
  @Via("resource")
  private String formId;

  private SlingHttpServletRequest request;

  @ValueMapValue
  @Via("resource")
  private String script;

  @ValueMapValue
  @Via("resource")
  private String successUrl;

  @ChildResource
  @Via("resource")
  private List<FormValue> values;

  public MarketoFormCmp(SlingHttpServletRequest request) {
    this.request = request;
  }

  public String getFormId() {
    return formId;
  }

  public SlingHttpServletRequest getRequest() {
    return request;
  }

  public String getScript() {
    return script;
  }

  public String getSuccessUrl() {
    if (successUrl == null) {
      return null;
    }
    String fullUrl = successUrl;
    Externalizer externalizer = request.getResourceResolver().adaptTo(Externalizer.class);
    if (successUrl.startsWith("/") && externalizer != null) {
      fullUrl = externalizer.relativeLink(request, successUrl);
      log.debug("Externalized {} to {}", successUrl, fullUrl);
    }
    if (!successUrl.contains(".")) {
      fullUrl += ".html";
    }
    log.debug("Final URL: {}", fullUrl);
    return fullUrl;

  }

  public List<FormValue> getValues() {
    return values;
  }

  public boolean isEdit() {
    return WCMMode.fromRequest(request) == WCMMode.EDIT;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }

  public void setRequest(SlingHttpServletRequest request) {
    this.request = request;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public void setSuccessUrl(String successUrl) {
    this.successUrl = successUrl;
  }

  public void setValues(List<FormValue> values) {
    this.values = values;
  }
}
