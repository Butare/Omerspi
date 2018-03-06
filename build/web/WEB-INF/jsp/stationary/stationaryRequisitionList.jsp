
<%@include file="/WEB-INF/jsp/header/header.jsp"%>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/notFoundItemRequisition/edit"/>
<c:url var="deleteUrl" value="/stationaryRequisition/delete"/>
<c:url var="searchUrl" value="/searchRequestedItem"/>
<c:url var="searchResponseUrl" value="/stationaryRequisition/searchResponse"/>
<c:url var="formUrl" value="/addItemForm"/>

<p align="center"><a href="${formUrl}">New Stationary Requisition</a></p>

<form:form modelAttribute="notFoundItemRequisition" method="GET">
    <c:if test="${!empty stationaryRequisitionByEmployeeList}">
        <h3 align="center">Stationary Requisition List</h3><br/>

        <table class="datatable" >

            <thead >
                <tr>
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
                <c:forEach items="${stationaryRequisitionByEmployeeList}" var="stationaryRequisition">
                    <tr>
                        <td>${stationaryRequisition.employee.employeeCode}</td>
                        <td>${stationaryRequisition.requisitionDate}</td>
                        <td>${stationaryRequisition.serviceDate}</td>
                        <td>${stationaryRequisition.status}</td>

                        <c:if test="${stationaryRequisition.status=='SENT BY HOD'||stationaryRequisition.status=='SENT BY PROFESSIONAL'}">
                            <td><a title="Delete" href="${deleteUrl}?stationaryRequisitionId=${stationaryRequisition.stationaryRequisitionId}"><img src="${deleteImgUrl}"></img></a></td>
                            <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisition.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td></td>
                        </c:if> 
                        <c:if test="${stationaryRequisition.status=='APPROVED BY HOD'||stationaryRequisition.status=='REJECTED BY HOD'||stationaryRequisition.status=='APPROVED BY DAF'||stationaryRequisition.status=='REJECTED BY DAF'||stationaryRequisition.status=='LOGISTICS COMMENTED'}">
                            <td><a title="View Requested Item" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisition.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Response" href="${searchResponseUrl}?requisitionId=${stationaryRequisition.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td></td>
                        </c:if>
                        <c:if test="${stationaryRequisition.status=='SERVED'}">
                            <td><a title="View Service" href="${searchUrl}?stationaryRequisitionId=${stationaryRequisition.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Response" href="${searchResponseUrl}?requisitionId=${stationaryRequisition.stationaryRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td></td>
                        </c:if>

                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty stationaryRequisitionByEmployeeList}">
        <p style="text-align: center;">No Related requisition(s) found</p>
    </c:if>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>