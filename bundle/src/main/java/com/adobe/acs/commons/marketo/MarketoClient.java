package com.adobe.acs.commons.marketo;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import com.adobe.acs.commons.marketo.models.MarketoClientConfiguration;
import com.adobe.acs.commons.marketo.models.MarketoForm;

public interface MarketoClient {

  public @Nonnull String getApiToken(@Nonnull MarketoClientConfiguration config) throws IOException;

  public @Nonnull List<MarketoForm> getForms(@Nonnull MarketoClientConfiguration config) throws IOException;
}
