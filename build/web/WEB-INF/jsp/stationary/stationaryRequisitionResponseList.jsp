
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="commentImgUrl" value="/images/comment.png"/>

<c:url var="logisticCommentUrl" value="/stationaryRequisitionResponse/logisticCommentForm.htm"/>
<c:url var="serveUrl" value="/carRequisitionService.htm"/>
<c:url var="dafResponseUrl" value="/carRequisitionResponse/HodAccepted/dafResponseForm.htm"/>
<c:url var="dafStationaryResponseUrl" value="/stationaryRequisitionResponse/HodAccepted/dafResponseForm.htm"/>

<script>
    function goBack(){   
        window.history.back();
    }
    
</script>

<c:if test="${stationaryRequisition!=null}">
         <h3 align="center" >Stationary Requisition</h3>
        <div class="centered">  <table style="border: 1px solid; width: 70%">
                <thead style="background:darkseagreen">
                    <tr>

                        <th>Requester Code</th>
                        <th>Requester Names</th>
                        <th>Requested On</th>
                        <th>Requisition Status</th>
                      
                    </tr>
                </thead>  
                <tbody>
                    <tr>
                        <td>${stationaryRequisition.employee.employeeCode}</td>
                        <td>${stationaryRequisition.employee.firstName} ${stationaryRequisition.employee.lastName}</td>
                        <td>${stationaryRequisition.requisitionDate}</td>
                        <td>${stationaryRequisition.status}</td>

                    </tr>
                </tbody>
            </table></div>
        </c:if>
        <br/>
        
<p style="text-align: center;">  <input type="button" value="Back" onclick="goBack()"></p><br/>
<c:if test="${!empty responseByRequisitionList}">
    <form:form modelAttribute="carRequisitionResponse" method="GET">
        <h3 align="center">Requisition Response(s)</h3><br/>
        <div class="centered">  <table align="center" style="border: 1px solid; width: 100%; text-align:center">

                <thead style="background:#1c94c4">
                    <tr>
                        <th>Stationary Requisition ID</th>  
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
                            <td>${response.stationaryrequisition.stationaryRequisitionId}</td>
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

                            <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                                <c:if test="${response.stationaryrequisition.status=='SENT BY HOD'||response.stationaryrequisition.status=='APPROVED BY HOD'}">
                                    <td><a title="DAF Click to Respond" href="${dafStationaryResponseUrl}?requisitionResponseId=${response.requisitionResponseId}"><img src="${viewImgUrl}"></img></a></td>

                                </c:if>
                            </omerspi:require>

                            <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                                <c:if test="${response.stationaryrequisition.status=='APPROVED BY DAF'}">
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