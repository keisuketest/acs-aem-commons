package com.adobe.acs.commons.marketo.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;

import io.wcm.testing.mock.aem.junit.AemContext;

public class TestMarketoClientConfiguration {

  @Rule
  public AemContext context = new AemContext();

  @Before
  public void init() {
    context.addModelsForPackage("com.adobe.acs.commons.marketo.models");
    context.load().json("/com/adobe/acs/commons/marketo/pages.json", "/content/page");
    context.load().json("/com/adobe/acs/commons/marketo/cloudconfig.json", "/etc/cloudservices/marketo/test");

    Configuration config = Mockito.mock(Configuration.class);
    Mockito.when(config.getResource())
        .thenReturn(context.resourceResolver().getResource("/etc/cloudservices/marketo/test"));
    ConfigurationManager configurationManager = Mockito.mock(ConfigurationManager.class);
    Mockito.when(configurationManager.getConfiguration(Mockito.anyString(), Mockito.any())).thenReturn(config);
    context.registerAdapter(ResourceResolver.class, ConfigurationManager.class, configurationManager);

  }

  @Test
  public void testConfigMgr() {
    context.request().setResource(context.resourceResolver().getResource("/content/page"));
    MarketoClientConfigurationManager mccm = context.request().adaptTo(MarketoClientConfigurationManager.class);

    assertNotNull(mccm);
    assertNotNull(mccm.getConfiguration());
  }

  @Test
  public void testConfig() {
    MarketoClientConfiguration mcc = context.resourceResolver().getResource("/etc/cloudservices/marketo/test/jcr:content")
        .adaptTo(MarketoClientConfiguration.class);
    assertNotNull(mcc);
    assertEquals("123",mcc.getClientId());
    assertEquals("456",mcc.getClientSecret());
    assertEquals("test.mktorest.com",mcc.getEndpointHost());
    assertEquals("123-456-789",mcc.getMunchkinId());
    assertEquals("//test.marketo.com",mcc.getServerInstance());
    
    MarketoClientConfiguration config = new MarketoClientConfiguration();
    config.setClientId("123");
    assertEquals(config,mcc);
    assertEquals(config.hashCode(),mcc.hashCode());
  }
}
