
<%@include file="/WEB-INF/jsp/header/header.jsp"%>


<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/notFoundItemRequisition/edit"/>
<c:url var="deleteUrl" value="/stationaryRequisition/delete"/>
<c:url var="searchUrl" value="/searchRequestedItem"/>
<c:url var="hodResponseUrl" value="/stationaryRequisitionResponse/hodResponseForm.htm"/>
<c:url var="dafResponseUrl" value="/stationaryRequisitionResponse/dafResponseForm.htm"/>
<c:url var="dafSearchResponseUrl" value="/stationaryRequisition/searchResponse"/>
<c:url var="logisticCommentUrl" value="/stationaryRequisitionResponse/logisticCommentForm.htm"/>
<c:url var="serveUrl" value="/searchRequestedItemToServe"/>

<form:form modelAttribute="notFoundItemRequisition" method="GET">
    <c:if test="${!empty stationaryRequisitionSuperList}">
        <h3 align="center">Stationary Requisition List</h3>

        <table class="datatable" >

            <thead style="background:#d3dce3">
                <tr>
                    <th>Requisition ID</th>
                    <th>Employee Code</th>  
                    <th>Requested On</th> 
                    <th>Served On</th> 
                    <th>Status</th> 
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody style="background:#ccc">
                <c:forEach items="${stationaryRequisitionSuperList}" var="stationaryRequisitionSuper">
                    <tr>
                        <td>${stationaryRequisitionSuper.stationaryRequisitionId}</td>
                        <td>${stationaryRequisitionSuper.employee.employeeCode}</td>
                        <td>${stationaryRequisitionSuper.requisitionDate}</td>
                        <td>${stationaryRequisitionSuper.serviceDate}</td>
                        <td>${stationaryRequisitionSuper.status}</td>
                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <c:if test="${stationaryRequisitionSuper.status=='APPROVED BY DAF'}">
                                <td><a title="View Response" href="${dafSearchResponseUrl}?requisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>    
                                <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td> 
                                <td><a title="Click to Serve" href="${serveUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                    </c:if>
                                </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <c:if test="${stationaryRequisitionSuper.status=='LOGISTICS COMMENTED'}">
                                <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td> 
                                <td><a title="Click to Serve" href="${serveUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td> 
                            </c:if>
                        </omerspi:require>
                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <c:if test="${stationaryRequisitionSuper.status=='SERVED'}">
                                <td><a title="View Items" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td> 
                                <td><a href="${pageContext.request.contextPath}/stationaryRequisition/printStationaryRequisitionForm.htm?output=pdf&stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}">Print Form</a></td>
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <c:if test="${stationaryRequisitionSuper.status=='SENT BY HOD'}">
                                <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td><a title="Click to Respond" href="${dafResponseUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <c:if test="${stationaryRequisitionSuper.status=='APPROVED BY HOD'}">
                                <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td><a title="View HoD Response" href="${dafSearchResponseUrl}?requisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <c:if test="${stationaryRequisitionSuper.status=='APPROVED BY DAF'||stationaryRequisitionSuper.status=='REJECTED BY DAF'}">
                                <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td> 
                                <td></td>
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            <c:if test="${stationaryRequisitionSuper.status=='SENT BY PROFESSIONAL'}">
                                <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td>  <a title="Click to Respond" href="${hodResponseUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td> 
                            </c:if>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            <c:if test="${stationaryRequisitionSuper.status=='APPROVED BY HOD'||stationaryRequisitionSuper.status=='REJECTED BY HOD'}">
                                <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisitionSuper.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
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
        <p style="text-align: center;">  <c:if test="${empty stationaryRequisitionSuperList}">
                Sorry, ${sessionScope["omerspi-user"]} ,You did n't made any stationary requisition Before!!
            </c:if></p>
        </omerspi:require>

    <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
        <c:if test="${empty stationaryRequisitionSuperList}">
            <p align="center"> No related requisition found!</p>
        </c:if>   
    </omerspi:require>

    <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
        <c:if test="${empty stationaryRequisitionSuperList}">
            <p align="center"> No related requisition found!</p>
        </c:if>   
    </omerspi:require>

    <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
        <c:if test="${empty stationaryRequisitionSuperList}">
            <p align="center"> No related requisition found!</p>
        </c:if>   
    </omerspi:require>

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>