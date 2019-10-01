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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.adobe.acs.commons.marketo.MarketoClient;
import com.adobe.acs.commons.marketo.StaticResponseMarketoClient;
import com.adobe.granite.ui.components.ds.DataSource;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;

import io.wcm.testing.mock.aem.junit.AemContext;

public class TestMarketoFormDataSource {

  @Rule
  public AemContext context = new AemContext();

  @Before
  public void init() {
    context.addModelsForPackage("com.adobe.acs.commons.marketo.models");
    context.load().json("/com/adobe/acs/commons/marketo/pages.json", "/content/page");
    context.load().json("/com/adobe/acs/commons/marketo/cloudconfig.json", "/etc/cloudservices/marketo/test");

    Resource resource = Mockito.mock(Resource.class);
    Mockito.when(resource.getPath()).thenReturn("/mnt/somewhere");
    context.request().setResource(resource);
    context.requestPathInfo().setSuffix("/content/page");
  }

  @Test
  public void testdoGet() throws IOException {

    Configuration config = Mockito.mock(Configuration.class);
    Mockito.when(config.getResource())
        .thenReturn(context.resourceResolver().getResource("/etc/cloudservices/marketo/test"));
    ConfigurationManager configurationManager = Mockito.mock(ConfigurationManager.class);
    Mockito.when(configurationManager.getConfiguration(Mockito.anyString(), Mockito.any())).thenReturn(config);
    context.registerAdapter(ResourceResolver.class, ConfigurationManager.class, configurationManager);

    MarketoFormDataSource mktoDS = new MarketoFormDataSource();

    MarketoClient client = new StaticResponseMarketoClient(new String[] {
        "/com/adobe/acs/commons/marketo/token-response.json", "/com/adobe/acs/commons/marketo/form-response.json",
        "/com/adobe/acs/commons/marketo/response-noassets.json" });
    mktoDS.bindMarketoClient(client);

    mktoDS.doGet(context.request(), context.response());

    assertNotNull(context.request().getAttribute(DataSource.class.getName()));
  }

  public void testInvalidResource() {

    ConfigurationManager configurationManager = Mockito.mock(ConfigurationManager.class);
    Mockito.when(configurationManager.getConfiguration(Mockito.anyString(), Mockito.any())).thenReturn(null);
    context.registerAdapter(ResourceResolver.class, ConfigurationManager.class, configurationManager);

    MarketoFormDataSource mktoDataSrc = new MarketoFormDataSource();

    MarketoClient client = new StaticResponseMarketoClient(new String[] {
        "/com/adobe/acs/commons/marketo/token-response.json", "/com/adobe/acs/commons/marketo/form-response.json",
        "/com/adobe/acs/commons/marketo/response-noassets.json" });
    mktoDataSrc.bindMarketoClient(client);

    mktoDataSrc.doGet(context.request(), context.response());

    assertNull(context.request().getAttribute(DataSource.class.getName()));
  }
}
