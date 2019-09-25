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
          var getParam = function(key) {
            var query = window.location.search.substring(1);
            var vars = query.split('&');
            for (var i = 0; i < vars.length; i++) {
              var pair = vars[i].split('=');
              if (key === pair[0]) {
                return decodeURIComponent(pair[1]);
              }
            }
          }
          var data = {};
          <c:forEach var="v" items="${formCmp.values}">
            <c:choose>
              <c:when test="${v.source == 'static'}">
                data["${v.name}"] = "${v.value}";
              </c:when>
              <c:otherwise>
                data["${v.name}"] = getParam("${v.value}");
              </c:otherwise>
            </c:choose>
          </c:forEach>
          form.vals(data);
        </c:if>
      });
    </script>
  </c:when>
</c:choose>