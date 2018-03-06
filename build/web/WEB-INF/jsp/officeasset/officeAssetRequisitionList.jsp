
<%@include file="/WEB-INF/jsp/header/header.jsp"%>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/notFoundItemRequisition/edit"/>
<c:url var="deleteUrl" value="/officeAsset/delete"/>
<c:url var="searchUrl" value="/officeAssetService/searchRequestedItemToServe"/>
<c:url var="searchResponseUrl" value="/officeAssetRequisition/searchResponse"/>
<c:url var="formUrl" value="/officeAsset/addOfficecAssetForm"/>



<form:form  method="GET">
    <c:if test="${!empty officeAssetRequisitionByEmployeeList}">
        <h3 align="center">Office Asset Requisition List</h3>
        <p align="center"><a href="${formUrl}">New Office asset Requisition</a></p>
        <table class="datatable" >

            <thead >
                <tr>
                    <th>Requester Name</th> 
                    <th>Beneficiary Name</th>
                    <th>Room number</th>
                    <th>Requested On</th>                
                    <th>Status</th> 
                    <th>Served On</th> 
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody style="background:#ccc">
                <c:forEach items="${officeAssetRequisitionByEmployeeList}" var="officeAssetRequisition">
                    <tr>
                        <td>${officeAssetRequisition.employee.firstName}&nbsp;${officeAssetRequisition.employee.lastName}</td>
                        <td>${officeAssetRequisition.beneficiary}</td>
                        <td>${officeAssetRequisition.roomNumber}</td>
                        <td>${officeAssetRequisition.requisitionDate}</td>
                        <td>${officeAssetRequisition.status}</td>
                        <td>${officeAssetRequisition.serviceDate}</td>


                        <c:if test="${officeAssetRequisition.status=='SENT BY HOD'||officeAssetRequisition.status=='SENT BY PROFESSIONAL'}">
                            <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisition.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="Delete" href="${deleteUrl}?officeAssetRequisitionId=${officeAssetRequisition.officeAssetRequisitionId}"><img src="${deleteImgUrl}"></img></a></td>
                            <td></td>
                        </c:if> 
                        <c:if test="${officeAssetRequisition.status=='APPROVED BY HOD'||officeAssetRequisition.status=='REJECTED BY HOD'||officeAssetRequisition.status=='APPROVED BY DAF'||officeAssetRequisition.status=='REJECTED BY DAF'||officeAssetRequisition.status=='LOGISTICS COMMENTED'}">
                            <td><a title="View Requested Item" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisition.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Response" href="${searchResponseUrl}?requisitionId=${officeAssetRequisition.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td></td>
                        </c:if>
                        <c:if test="${officeAssetRequisition.status=='SERVED'}">
                            <td><a title="View Service" href="${searchUrl}?officeAssetRequisitionId=${officeAssetRequisition.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Response" href="${searchResponseUrl}?requisitionId=${officeAssetRequisition.officeAssetRequisitionId}"><img src="${viewImgUrl}"></img></a></td>                          
                            <td></td>
                        </c:if>

                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty officeAssetRequisitionByEmployeeList}">      
        <p style="text-align: center;">No Related requisition(s) found</p>
        <p align="center"><a href="${formUrl}">New Office asset Requisition</a></p>
    </c:if>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>