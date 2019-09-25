package com.adobe.acs.commons.marketo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import com.adobe.acs.commons.marketo.impl.MarketoClientImpl;
import com.drew.lang.annotations.NotNull;

public class StaticResponseMarketoClient extends MarketoClientImpl {

  private String resourcePath;
  private Iterator<String> resourcePaths;

  public StaticResponseMarketoClient(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  public StaticResponseMarketoClient(String[] resourcePaths) {
    this.resourcePaths = Arrays.asList(resourcePaths).iterator();
    if (this.resourcePaths.hasNext()) {
      resourcePath = this.resourcePaths.next();
    }
  }

  protected @NotNull String doGet(@NotNull String url, String bearerToken) throws IOException {
    String resp = IOUtils.toString(StaticResponseMarketoClient.class.getResourceAsStream(resourcePath), StandardCharsets.UTF_8);
    if (resourcePaths != null && resourcePaths.hasNext()) {
      resourcePath = resourcePaths.next();
    }
    return resp;
  }
}
