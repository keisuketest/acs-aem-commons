<%--
  #%L
  ACS AEM Commons Package
  %%
  Copyright (C) 2013 Adobe
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
<%@include file="/libs/foundation/global.jsp"%>
<div>
    <h3>Marketo Settings</h3>
    <ul>
        <li><div class="li-bullet"><strong>Endpoint Host: </strong><br>${properties.endpointHost}</div></li>
        <li><div class="li-bullet"><strong>Server Instance: </strong><br>${properties.serverInstance}</div></li>
        <li><div class="li-bullet"><strong>Munchkin Id: </strong><br>${properties.munchkinId}</div></li>
        <li><div class="li-bullet"><strong>Client ID: </strong><br>${properties.clientId}</div></li>
        <li><div class="li-bullet"><strong>Client Secret: </strong><br><c:if test="${ not empty properties.clientSecret}">*****</c:if></div></li>
    </ul>
</div>