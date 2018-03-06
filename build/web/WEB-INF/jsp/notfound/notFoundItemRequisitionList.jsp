
<%@include file="/WEB-INF/jsp/header/header.jsp"%>


<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/notFoundItemRequisition/edit"/>
<c:url var="deleteUrl" value="/notFoundItemRequisition/delete"/>
<c:url var="searchUrl" value="/searchMemo"/>
<c:url var="formUrl" value="/notFoundItemRequisition/form"/>
<c:url var="viewResponseUrl" value="/notFoundItemRequisition/searchResponse"/>
<br/>

<p style="text-align: center;">  <a href="${formUrl}">New Not Found Request</a></p>
<form:form modelAttribute="notFoundItemRequisition" method="GET">
    <c:if test="${!empty notFoundItemList}">
        <h3 align="center">Not Found Requisition List</h3>
        <br/>
        <table class="datatable" >

            <thead>
                <tr>
                    <th>Requisition ID</th>
                    <th>Employee Code</th>  
                    <th>Description</th> 
                    <th>Type</th> 
                    <th>Reason</th> 
                    <th>Requested On</th> 
                    <th>Status</th> 
                    <th>Served On</th>
                    <th></th>
                    <th></th>
                    <th></th>

                </tr>
            </thead>
            <tbody>
                <c:forEach items="${notFoundItemList}" var="notFoundItemRequisition">
                    <tr>
                        <td>${notFoundItemRequisition.notFoundRequisitionId}</td>
                        <td>${notFoundItemRequisition.employee.employeeCode}</td>
                        <td>${notFoundItemRequisition.description}</td>
                        <td>${notFoundItemRequisition.type}</td>
                        <td>${notFoundItemRequisition.reason}</td>
                        <td>${notFoundItemRequisition.requestedOn}</td>
                        <td>${notFoundItemRequisition.status}</td>
                        <td>${notFoundItemRequisition.servedOn}</td>
                        <c:if test="${notFoundItemRequisition.status=='SENT BY HOD'||notFoundItemRequisition.status=='SENT BY PROFESSIONAL'}">
                            <td><a title="Edit" href="${editUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${editImgUrl}"></img></a></td>
                            <td><a title="Delete" href="${deleteUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${deleteImgUrl}"></img></a></td>
                            <td><a title="View Memo" href="${searchUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                </c:if> 
                                <c:if test="${notFoundItemRequisition.status=='APPROVED BY HOD'||notFoundItemRequisition.status=='REJECTED BY HOD'||notFoundItemRequisition.status=='APPROVED BY DAF'||notFoundItemRequisition.status=='REJECTED BY DAF'||notFoundItemRequisition.status=='LOGISTICS COMMENTED'}">
                            <td><a title="View Memo" href="${searchUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Response" href="${viewResponseUrl}?requisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td></td>
                        </c:if>


                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <p style="text-align: center;">  <c:if test="${empty notFoundItemList}">
            Sorry, ${sessionScope["omerspi-user"]} ,You did n't made any Asset Requisition Before!!
        </c:if></p>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>