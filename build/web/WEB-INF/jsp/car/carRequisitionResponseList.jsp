
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="commentImgUrl" value="/images/comment.png"/>

<c:url var="logisticCommentUrl" value="/car/carRequisitionResponse/logisticCommentForm.htm"/>
<c:url var="serveUrl" value="/car/carRequisitionService.htm"/>
<c:url var="dafResponseUrl" value="/car/carRequisitionResponse/HodAccepted/dafResponseForm.htm"/>
<c:url var="dafStationaryResponseUrl" value="/stationaryRequisitionResponse/HodAccepted/dafResponseForm.htm"/>

<script>
    function goBack(){
        window.history.back();
    }
    
</script>

 <c:if test="${carRequisition!=null}">
       <h3 align="center">Car Requisition</h3>
        <div class="centered">  <table style="border: 1px solid; width: 70%">
                <thead style="background:darkseagreen">
                    <tr>
                        <th>Employee Code</th>
                        <th>Reasons</th>
                        <th>Destination</th>
                        <th>Car Type</th>
                        <th>Departure Time</th>
                        <th>Expected Return Time</th>

                    </tr>
                </thead>  
                <tbody>
                    <tr>
                        <td>${carRequisition.employee.employeeCode}</td>
                        <td>${carRequisition.reason}</td>
                        <td>${carRequisition.destination}</td>
                        <td>${carRequisition.cartype.typeName}</td>
                        <td>${carRequisition.departureTime}</td>
                        <td>${carRequisition.expectedTimeReturn}</td>
                    </tr>
                </tbody>
            </table></div>
        </c:if>
<br/>
<p style="text-align: center;">  <input type="button" value="Back" onclick="goBack()" /></p>
<br/>
<c:if test="${!empty responseByRequisitionList}">
    <form:form modelAttribute="carRequisitionResponse" method="GET">
        <h3 align="center">Requisition Response(s)</h3><br/>
        <div class="centered">  <table align="center" style="border: 1px solid; width: 100%; text-align:center">

                <thead style="background:#1c94c4">
                    <tr>
                        <th>Car Requisition ID</th> 
                        <th>HoD Employee Code</th> 
                        <th>HoD Response</th> 
                        <th>HoD Comment</th> 
                        <th>HoD Response Date</th> 
                        <th>Logistic Employee Code</th>
                        <th>Logistic Comment</th>

                    </tr>
                </thead>
                <tbody style="background:#ccc">
                    <c:forEach items="${responseByRequisitionList}" var="response">
                        <tr>
                            <td>${response.carrequisition.carRequisitionId}</td>
                            <td>${response.employeeByHodEmployeeRegistrationId.employeeCode}</td>
                            <td>${response.hodResponse}</td>
                            <td>${response.hodComment}</td>
                            <td>${response.hodResponseDate}</td>
                            <td>${response.employeeByLogisticEmployeeRegistrationId.employeeCode}</td>
                            <td>${response.logisticComment}</td>
                                    <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                                        <c:if test="${response.carrequisition.carRequisitionStatus=='SENT BY HOD'||response.carrequisition.carRequisitionStatus=='APPROVED BY HOD'}">
                                    <td><a title="Click to Serve" href="${serveUrl}?carRequisitionId=${response.carrequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
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