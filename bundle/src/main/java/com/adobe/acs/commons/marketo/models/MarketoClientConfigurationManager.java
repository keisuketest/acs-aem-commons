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

import java.util.Arrays;
import java.util.Optional;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;

/**
 * A Model for retrieving a MarketoClientConfiguration for a particular request.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class MarketoClientConfigurationManager {

  private static final Logger log = LoggerFactory.getLogger(MarketoClientConfigurationManager.class);

  private ConfigurationManager configurationManager;

  private SlingHttpServletRequest request;

  public MarketoClientConfigurationManager(SlingHttpServletRequest request) {
    this.request = request;
    configurationManager = request.getResourceResolver().adaptTo(ConfigurationManager.class);
  }

  public MarketoClientConfiguration getConfiguration() {
    log.trace("getConfiguration");

    InheritanceValueMap pageProperties = null;
    if (request.getResource().getPath().startsWith("/mnt")) {
      Resource suffixResource = request.getRequestPathInfo().getSuffixResource();
      log.debug("Using suffix resource: {}", suffixResource);
      pageProperties = getPageProperties(suffixResource);
    } else {
      pageProperties = getPageProperties(request.getResource());
    }
    if (pageProperties != null) {
      log.debug("Loaded page properties: {}", pageProperties);
      String[] services = pageProperties.getInherited("cq:cloudserviceconfigs", new String[] {});
      if (log.isDebugEnabled()) {
        log.debug("Loaded cloudService configs: {}", Arrays.toString(services));
      }
      Configuration cfg = configurationManager.getConfiguration("marketo", services);
      if (cfg != null) {
        log.debug("Loaded configuration: {}", cfg);
        Resource content = cfg.getResource().getChild(JcrConstants.JCR_CONTENT);
        if (content != null) {
          log.debug("Getting marketo configuration from: {}", content);
          return content.adaptTo(MarketoClientConfiguration.class);
        } else {
          log.warn("No content resource found under {}", cfg.getResource());
          return null;
        }
      } else {
        if (log.isDebugEnabled()) {
          log.debug("Failed to load configuration from: {}", Arrays.toString(services));
        }
        return null;
      }
    } else {
      log.debug("Unable to load page properties");
      return null;
    }
  }

  private InheritanceValueMap getPageProperties(Resource resource) {
    if (resource != null) {
      PageManager pageMgr = resource.getResourceResolver().adaptTo(PageManager.class);
      return Optional.ofNullable(pageMgr).map(pm -> pm.getContainingPage(resource))
          .map(p -> new HierarchyNodeInheritanceValueMap(p.getContentResource())).orElse(null);
    } else {
      return null;
    }
  }
}
