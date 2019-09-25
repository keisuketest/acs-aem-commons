package com.adobe.acs.commons.marketo.models;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class })
public class FormValue {

  @ValueMapValue
  private String name;

  @ValueMapValue
  private String source;

  @ValueMapValue
  private String value;

  public String getName() {
    return StringEscapeUtils.escapeEcmaScript(name);
  }

  public String getSource() {
    return source;
  }

  public String getValue() {
    return StringEscapeUtils.escapeEcmaScript(value);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
