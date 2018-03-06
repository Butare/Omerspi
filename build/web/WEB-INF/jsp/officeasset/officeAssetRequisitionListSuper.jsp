
<%@include file="/WEB-INF/jsp/header/header.jsp"%>

<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="searchUrl" value="/officeAssetService/searchRequestedItemToServe"/>
<c:url var="hodResponseUrl" value="/officeAssetRequisitionResponse/hodResponseForm.htm"/>
<c:url var="dafResponseUrl" value="/officeAssetRequisitionResponse/dafResponseForm.htm"/>
<c:url var="dafSearchResponseUrl" value="/officeAssetRequisition/searchResponse"/>
<c:url var="logisticCommentUrl" value="/stationaryRequisitionResponse/logisticCommentForm.htm"/>
<c:url var="serveUrl" value="/officeAssetService/searchRequestedItemToServe"/>

<form:form method="GET">
    <c:if test="${!empty officeAssetRequisitionSuperList}">
        <h3 align="center">Office Asset Requisition List</h3>

        <table class="datatable" >

            <thead style="background:#d3dce3">
                <tr>
                    <th>Requester Code</th> 
                    <th>Requester name</th>
                    <th>Beneficiary</th>
                    <th>Room number</th>
                    <th>Requested On</th> 
                    <th>Served On</th> 
                    <th>Status</th> 
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody style="background:#ccc">
                <c:forEach items="${officeAssetRequisitionSuperList}" var="officeAssetRequisitionSuper">
                    <tr>
                        <td>${officeAssetRequisitionSuper.employee.employeeCode}</td>
                        <td>${officeAssetRequisitionSuper.employee.firstName} ${officeAssetRequisitionSuper.employee.lastName}</td>
                        <td>${officeAssetRequisitionSuper.beneficiary}</td>
                        <td>${officeAssetRequisitionSuper.roomNumber}</td>
                        <td>${officeAssetRequisitionSuper.requisitionDate}</td>
                        <td>${officeAssetRequisitionSuper.serviceDate}</td>
                        <td>${officeAssetRequisitionSuper.status}</td>
                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <c:if test="${officeAssetRequisitionSuper.status=='APPROVED BY DAF'}">
                                <td><a title="View Response" href="${dafSearchResponseUrl}?requisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>    
                                <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td> 
                                <td><a title="Click to Serve" href="${serveUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                    </c:if>
                                </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <c:if test="${officeAssetRequisitionSuper.status=='LOGISTICS COMMENTED'}">
                                <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td> 
                                <td><a title="Click to Serve" href="${serveUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td> 
                            </c:if>
                        </omerspi:require>
                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <c:if test="${officeAssetRequisitionSuper.status=='SERVED'}">
                                <td><a title="View Items" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td> 
                                <td><a href="${pageContext.request.contextPath}/officeAssetRequisition/printOfficeAssetRequisitionForm.htm?output=pdf&officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}">Print Form</a></td>
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <c:if test="${officeAssetRequisitionSuper.status=='SENT BY HOD'}">
                                <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td><a title="Click to Respond" href="${dafResponseUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <c:if test="${officeAssetRequisitionSuper.status=='APPROVED BY HOD'}">
                                <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td><a title="View HoD Response" href="${dafSearchResponseUrl}?requisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <c:if test="${officeAssetRequisitionSuper.status=='APPROVED BY DAF'||officeAssetRequisitionSuper.status=='REJECTED BY DAF'}">
                                <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td> 
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            <c:if test="${officeAssetRequisitionSuper.status=='SENT BY PROFESSIONAL'}">
                                <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td>  <a title="Click to Respond" href="${hodResponseUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td> 
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            <c:if test="${officeAssetRequisitionSuper.status=='APPROVED BY HOD'||officeAssetRequisitionSuper.status=='REJECTED BY HOD'}">
                                <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisitionSuper.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td>
                                <td></td>
                            </c:if>
                        </omerspi:require>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
        <p style="text-align: center;">  <c:if test="${empty officeAssetRequisitionSuperList}">
                Sorry, ${sessionScope["omerspi-user"]} ,You did n't made any office asset requisition before!!
            </c:if></p>
        </omerspi:require>

    <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
        <c:if test="${empty officeAssetRequisitionSuperList}">
            <p align="center"> No related requisition found!</p>
        </c:if>   
    </omerspi:require>

    <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
        <c:if test="${empty officeAssetRequisitionSuperList}">
            <p align="center"> No related requisition found!</p>
        </c:if>   
    </omerspi:require>

    <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
        <c:if test="${empty officeAssetRequisitionSuperList}">
            <p align="center"> No related requisition found!</p>
        </c:if>   
    </omerspi:require>

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>