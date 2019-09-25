package com.adobe.acs.commons.marketo.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.WCMMode;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MarketoFormCmp {

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
    }
    if (!successUrl.contains(".")) {
      fullUrl += ".html";
    }
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
