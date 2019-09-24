<%@include file="/libs/foundation/global.jsp" %><%@taglib prefix="sling2" uri="http://sling.apache.org/taglibs/sling" %>
<c:set var="mktoConfigMgr" value="${sling2:adaptTo(slingRequest,'com.adobe.acs.commons.marketo.models.MarketoClientConfigurationManager')}" />
<c:set var="mktoConfig" value="${mktoConfigMgr.configuration}" />
<c:choose>
  <c:when test="${mktoConfigMgr.showPlaceholder}">
    <div class="cq-placeholder" data-emptytext="Configure Marketo Form"></div>
  </c:when>
  <c:when test="${mktoConfig == null}">
    <div class="cq-placeholder" data-emptytext="Configure Marketo Cloud Configuration"></div>
  </c:when>
  <c:otherwise>
    <form id="mktoForm_${properties.formId}"></form>
    <script>
      MktoForms2.loadForm("${mktoConfig.serverInstance}", "${mktoConfig.munchkinId}", ${properties.formId}, function(form){
        <c:if test="${not empty properties.successUrl}">
          form.onSuccess(function(values, followUpUrl) {
            location.href = "${properties.successUrl}";
            return false;
          });
        </c:if>
      });
    </script>
  </c:otherwise>
</c:choose>