<%--
  #%L
  ACS AEM Commons Package
  %%
  Copyright (C) 2019 Adobe
  %%
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  #L%
  --%>
<%@include file="/libs/foundation/global.jsp" %><%@taglib prefix="sling2" uri="http://sling.apache.org/taglibs/sling" %>
<c:set var="mktoConfig" value="${sling2:adaptTo(slingRequest,'com.adobe.acs.commons.marketo.models.MarketoClientConfigurationManager').configuration}" />
<sling2:adaptTo adaptable="${slingRequest}" adaptTo="com.adobe.acs.commons.marketo.models.MarketoFormCmp" var="formCmp" />
<c:choose>
  <c:when test="${formCmp.edit && empty formCmp.formId}">
    <div class="cq-placeholder" data-emptytext="Configure Marketo Form"></div>
  </c:when>
  <c:when test="${mktoConfig == null}">
    <div class="cq-placeholder" data-emptytext="Configure Marketo Cloud Configuration"></div>
  </c:when>
  <c:when test="${not empty formCmp.formId}">
    <form id="mktoForm_${properties.formId}"></form>
    <script>
      MktoForms2.loadForm("${mktoConfig.serverInstance}", "${mktoConfig.munchkinId}", ${formCmp.formId}, function(form){
        <c:if test="${not empty properties.successUrl}">
          form.onSuccess(function(values, followUpUrl) {
            location.href = "${formCmp.successUrl}";
            return false;
          });
        </c:if>
        ${formCmp.script}
        <c:if test="${formCmp.values != null && fn:length(formCmp.values) > 0}">
          var values = {};
          <c:forEach var="v" items="${formCmp.values}">
            <c:choose>
              <c:when test="${v.source == 'static'}">
                values["${v.name}"] = "${v.value}";
              </c:when>
              <c:when test="${v.source == 'contextHub'}">
                values['${v.name}'] = ContextHub.getItem("${v.value}");
              </c:when>
              <c:when test="${v.source == 'jsVariable'}">
                values['${v.name}'] = ${v.value};
              </c:when>
              <c:otherwise>
                values["${v.name}"] = "${param[v.value]}";
              </c:otherwise>
            </c:choose>
          </c:forEach>
          form.vals(values);
        </c:if>
        <c:if test="${formCmp.hidden != null && fn:length(formCmp.hidden) > 0}">
          var hiddenFields = {};
          <c:forEach var="h" items="${formCmp.hidden}">
            <c:choose>
              <c:when test="${h.source == 'static'}">
                hiddenFields["${h.name}"] = "${h.value}";
              </c:when>
              <c:when test="${h.source == 'contextHub'}">
                hiddenFields['${h.name}'] = ContextHub.getItem("${h.value}");
              </c:when>
              <c:when test="${h.source == 'jsVariable'}">
                hiddenFields['${h.name}'] = ${h.value};
              </c:when>
              <c:otherwise>
                hiddenFields["${h.name}"] = "${param[h.value]}";
              </c:otherwise>
            </c:choose>
          </c:forEach>
          form.addHiddenFields(hiddenFields);
        </c:if>
      });
    </script>
  </c:when>
</c:choose>