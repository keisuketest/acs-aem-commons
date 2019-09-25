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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.wcm.testing.mock.aem.junit.AemContext;

public class TestMarketoFormCmp {
  @Rule
  public AemContext context = new AemContext();

  @Before
  public void init() {
    context.addModelsForPackage("com.adobe.acs.commons.marketo.models");
    context.load().json("/com/adobe/acs/commons/marketo/pages.json", "/content/page");
    context.load().json("/com/adobe/acs/commons/marketo/cloudconfig.json", "/etc/cloudservices/marketo/test");
  }

  @Test
  public void testConfig() {
    Resource resource = context.resourceResolver().getResource("/content/page/about-us/jcr:content/root/marketo-form");
    context.request().setResource(resource);
    MarketoFormCmp mfc = context.request().adaptTo(MarketoFormCmp.class);
    assertNotNull(mfc);
    assertNotNull(mfc.getFormId());
    assertNull(mfc.getScript());
    assertNull(mfc.getSuccessUrl());
    assertNull(mfc.getValues());
  }

  @Test
  public void testAdvancedConfig() {
    Resource resource = context.resourceResolver()
        .getResource("/content/page/about-us/jcr:content/root/marketo-form-1");
    context.request().setResource(resource);
    MarketoFormCmp mfc = context.request().adaptTo(MarketoFormCmp.class);
    assertNotNull(mfc);
    assertEquals("123", mfc.getFormId());
    assertEquals("alert('hi')", mfc.getScript());
    assertEquals("/content/anotherpage.html", mfc.getSuccessUrl());
    assertNotNull(mfc.getValues());

    assertEquals(2, mfc.getValues().size());

    assertEquals("Name", mfc.getValues().get(0).getName());
    assertEquals("static", mfc.getValues().get(0).getSource());
    assertEquals("Perficient Digital", mfc.getValues().get(0).getValue());

    assertEquals("Twitter", mfc.getValues().get(1).getName());
    assertEquals("request", mfc.getValues().get(1).getSource());
    assertEquals("@PRFTDigital", mfc.getValues().get(1).getValue());
  }

  @Test
  public void testInvalidResource() {
    Resource resource = context.resourceResolver().getResource("/content/page/about-us/jcr:content/root");
    context.request().setResource(resource);
    MarketoFormCmp mfc = context.request().adaptTo(MarketoFormCmp.class);
    assertNotNull(mfc);
    assertNull(mfc.getFormId());
  }
}
