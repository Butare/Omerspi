
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="commentImgUrl" value="/images/comment.png"/>

<c:url var="logisticCommentUrl" value="/stationaryRequisitionResponse/logisticCommentForm.htm"/>
<c:url var="serveUrl" value="/carRequisitionService.htm"/>
<c:url var="dafResponseUrl" value="/carRequisitionResponse/HodAccepted/dafResponseForm.htm"/>
<c:url var="dafStationaryResponseUrl" value="/stationaryRequisitionResponse/HodAccepted/dafResponseForm.htm"/>

<omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
    <c:url var="formUrl" value="/notFoundItemRequisition/list"/>
</omerspi:require>
<omerspi:require privilege="ADD REQUISITION HOD">
    <c:url var="formUrl" value="/notFoundItemRequisition/list"/>
</omerspi:require>

<omerspi:require privilege="RESPOND REQUISITION FROM EMPLOYEE">
    <c:url var="formUrl" value="/notFoundItemRequisition/ByDepartment/list"/>
</omerspi:require>
<omerspi:require privilege="RESPOND REQUISITION FROM HOD">
    <c:url var="formUrl" value="/notFoundItemRequisition/accepted/AllDepartments/list"/>
</omerspi:require>

<omerspi:require privilege="RESPOND REQUISITION FROM DAF">
    <c:url var="formUrl" value="/notFoundItemRequisition/Accepted/DAF/list"/>
</omerspi:require>
<br/>
<p style="text-align: center;">  <a href="${formUrl}">Not Found Item Requisition List</a></p><br/>
<c:if test="${!empty responseByRequisitionList}">
    <form:form modelAttribute="carRequisitionResponse" method="GET">
    
        <h3 align="center">Not Found Item Requisition Response(s)</h3>
        <div class="centered">  <table align="center" style="border: 1px solid; width: 100%; text-align:center">

                <thead style="background:#d3dce3">
                    <tr>

                        <th>HoD Employee Code</th> 
                        <th>HoD Response</th> 
                        <th>HoD Comment</th> 
                        <th>HoD Response Date</th> 
                        <th>DAF Employee Code</th> 
                        <th>DAF Response</th> 
                        <th>DAF Comment</th>
                        <th>DAF Response Date</th>
                        <th>Logistic Employee Code</th>
                        <th>Logistic Comment</th>

                    </tr>
                </thead>
                <tbody style="background:#ccc">
                    <c:forEach items="${responseByRequisitionList}" var="response">
                        <tr>

                            <td>${response.employeeByHodEmployeeRegistrationId.employeeCode}</td>
                            <td>${response.hodResponse}</td>
                            <td>${response.hodComment}</td>
                            <td>${response.hodResponseDate}</td>

                            <td>${response.employeeByDafEmployeeRegistrationId.employeeCode}</td>
                            <td>${response.dafResponse}</td>
                            <td>${response.dafComment}</td>
                            <td>${response.dafResponseDate}</td>
                            <td>${response.employeeByLogisticEmployeeRegistrationId.employeeCode}</td>
                            <td>${response.logisticComment}</td>

                            <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                                <c:if test="${response.notfounditemrequisition.status=='SENT BY PROFESSIONAL'}">
                                    <td><a title="HOD Click to Respond" href="${dafStationaryResponseUrl}?requisitionResponseId=${response.requisitionResponseId}"><img src="${viewImgUrl}"></img></a></td>
                                        </c:if>
                                    </omerspi:require>
                                    <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                                        <c:if test="${response.notfounditemrequisition.status=='SENT BY HOD'||response.notfounditemrequisition.status=='APPROVED BY HOD'}">
                                    <td><a title="DAF Click to Respond" href="${dafStationaryResponseUrl}?requisitionResponseId=${response.requisitionResponseId}"><img src="${viewImgUrl}"></img></a></td>
                                        </c:if>
                                    </omerspi:require>

                            <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                                <c:if test="${response.notfounditemrequisition.status=='APPROVED BY DAF'}">
                                    <td><a title="Click to Comment" href="${logisticCommentUrl}?requisitionResponseId=${response.requisitionResponseId}"><img src="${commentImgUrl}"></img></a></td> 
                                        </c:if>
                                    </omerspi:require>
                        </tr>

                    </c:forEach>
                </tbody>
            </table></div>

    </form:form>
</c:if>
<c:if test="${empty responseByRequisitionList}">
    No Related Requisition Found.
</c:if>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>