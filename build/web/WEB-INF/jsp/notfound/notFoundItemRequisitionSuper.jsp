
<%@include file="/WEB-INF/jsp/header/header.jsp"%>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/notFoundItemRequisition/edit"/>
<c:url var="deleteUrl" value="/notFoundItemRequisition/delete"/>
<c:url var="searchUrl" value="/searchMemo"/>
<c:url var="formUrl" value="/notFoundItemRequisition/form"/>
<c:url var="hodResponseUrl" value="/notFoundItemRequisitionResponse/hodResponseForm.htm"/>
<c:url var="dafResponseUrl" value="/carRequisitionResponse/dafResponseForm.htm"/>
<c:url var="dafSearchResponseUrl" value="/notFoundItemRequisition/searchResponse"/>
<c:url var="logisticCommentUrl" value="/carRequisitionResponse/logisticCommentForm.htm"/>

<form:form modelAttribute="notFoundItemRequisition" method="GET">
    <c:if test="${!empty notFoundItemList}">
        <h3 align="center">Not Found Requisition List</h3>
   
        <table class="datatable" >

            <thead>
                <tr>
                    <th>Employee Code</th>  
                    <th>Description</th> 
                    <th>Type</th> 
                    <th>Reason</th> 
                    <th>Requested On</th> 
                    <th>Status</th> 
                    <th>Served On</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${notFoundItemList}" var="notFoundItemRequisition">
                    <tr>
                        <td>${notFoundItemRequisition.employee.employeeCode}</td>
                        <td>${notFoundItemRequisition.description}</td>
                        <td>${notFoundItemRequisition.type}</td>
                        <td>${notFoundItemRequisition.reason}</td>
                        <td>${notFoundItemRequisition.requestedOn}</td>
                        <td>${notFoundItemRequisition.status}</td>
                        <td>${notFoundItemRequisition.servedOn}</td>
                        <td><a title="View Memo" href="${searchUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <c:if test="${notFoundItemRequisition.status=='APPROVED BY DAF'}">
                            <td><a title="View Response" href="${dafSearchResponseUrl}?requisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>    
                                </c:if>
                                <c:if test="${notFoundItemRequisition.status=='SENT BY HOD'}">
                            <td><a title="Click to Respond" href="${dafResponseUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                </c:if>
                                <c:if test="${notFoundItemRequisition.status=='SENT BY PROFESSIONAL'}">
                            <td>  <a title="Click to Respond" href="${hodResponseUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                </c:if>
                                <c:if test="${notFoundItemRequisition.status=='APPROVED BY HOD'}">
                            <td><a title="View HoD Response" href="${dafSearchResponseUrl}?requisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>

                        </c:if>
                        <c:if test="${notFoundItemRequisition.status=='LOGISTICS COMMENTED'}">
                            <td><a title="View Response" href="${dafSearchResponseUrl}?requisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>

                        </c:if>


                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <p style="text-align: center;"> 
        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
            <c:if test="${empty notFoundItemList}">
                Sorry, ${sessionScope["omerspi-user"]} ,You did n't made any Asset Requisition Before!!
            </c:if>   
        </omerspi:require>
    </p>
    <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
        <c:if test="${empty notFoundItemList}">
            <p align="center"> No related records found!</p>
        </c:if>   
    </omerspi:require>
    <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
        <c:if test="${empty notFoundItemList}">
            <p align="center"> No related records found!</p>
        </c:if>   
    </omerspi:require>

    <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
        <c:if test="${empty notFoundItemList}">
            <p align="center"> No related records found!</p>
        </c:if>   
    </omerspi:require>

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>