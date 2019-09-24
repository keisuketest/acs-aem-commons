package com.adobe.acs.commons.marketo.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class MarketoClientConfiguration {

  @ValueMapValue
  private String clientId;

  @ValueMapValue
  private String clientSecret;

  @ValueMapValue
  private String endpointHost;

  @ValueMapValue
  private String munchkinId;

  @ValueMapValue
  private String serverInstance;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MarketoClientConfiguration other = (MarketoClientConfiguration) obj;
    if (clientId == null) {
      if (other.clientId != null) {
        return false;
      }
    } else if (!clientId.equals(other.clientId)) {
      return false;
    }
    return true;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getEndpointHost() {
    return endpointHost;
  }

  public String getMunchkinId() {
    return munchkinId;
  }

  public String getServerInstance() {
    return serverInstance;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    return result;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public void setEndpointHost(String endpointHost) {
    this.endpointHost = endpointHost;
  }

  public void setMunchkinId(String munchkinId) {
    this.munchkinId = munchkinId;
  }

  public void setServerInstance(String serverInstance) {
    this.serverInstance = serverInstance;
  }

  @Override
  public String toString() {
    return "MarketoClientConfiguration [endpointHost=" + endpointHost + ", clientId=" + clientId + ", clientSecret="
        + clientSecret + ", munchkinId=" + munchkinId + "]";
  }
}
