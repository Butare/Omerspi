
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<h3 align="center" >${!empty memoByNotFoundList?"Memo":"No Memo Found"} </h3>

<script>
    function goBack()
    {
        window.history.back()
    }
</script>

<h3><p align="center"><input type="button" value="Back" onclick="goBack()"/></p></h3>

<c:if test="${!empty memoByNotFoundList}">
    <form:form modelAttribute="memoByNotFoundList" method="GET" >

        <div class="centered" >  <table style="border: 1px solid; width: 70%; text-align:center">

                <thead style="background:#d3dce3">
                    <tr>
                        <th>Memo ID</th>
                        <th>Requisition ID</th>
                        <th>Send To:</th> 
                        <th>Cc:</th>
                        <th>Subject</th>
                        <th>Due Date</th>
                    </tr> 

                </thead>
                <tbody style="background:#ccc">
                    <c:forEach items="${memoByNotFoundList}" var="memoByNotFound">
                        <tr>
                            <td>${memoByNotFound.memoId}</td>
                            <c:if test="${memoByNotFound.notfounditemrequisition.notFoundRequisitionId!=null}">
                                <td>${memoByNotFound.notfounditemrequisition.notFoundRequisitionId}</td>
                            </c:if>
                            <c:if test="${memoByNotFound.carrequisition.carRequisitionId!=null}">
                                <td>${memoByNotFound.carrequisition.carRequisitionId}</td>
                            </c:if>
                            <td>${memoByNotFound.sendTo}</td>
                            <td>${memoByNotFound.cc}</td>
                            <td>${memoByNotFound.subject}</td>
                            <td>${memoByNotFound.dueDate}</td>

                            <c:if test="${memoByNotFound.notfounditemrequisition.status=='SENT BY HOD'||memoByNotFound.notfounditemrequisition.status=='SENT BY PROFESSIONAL'}">
                                <td><a title="Edit" href="${editMemoUrl}?memoId=${memoByNotFound.memoId}"><img src="${editImgUrl}"></img></a></td>
                                <td><a title="Delete" href="${deleteUrl}?notFoundRequisitionId=${memoByNotFound.memoId}"><img src="${deleteImgUrl}"></img></a></td>
                                    </c:if> 
                                    <c:if test="${memoByNotFound.notfounditemrequisition.status=='APPROVED BY HOD'||memoByNotFound.notfounditemrequisition.status=='REJECTED BY HOD'||memoByNotFound.notfounditemrequisition.status=='APPROVED BY DAF'||memoByNotFound.notfounditemrequisition.status=='REJECTED BY DAF'||memoByNotFound.notfounditemrequisition.status=='LOGISTICS COMMENTED'}">
                                <td><a title="View Memo" href="${searchUrl}?notFoundRequisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td><a title="View Response" href="${viewResponseUrl}?requisitionId=${notFoundItemRequisition.notFoundRequisitionId}"><img src="${viewImgUrl}"></img></a></td>

                            </c:if>
                        </tr>

                    </c:forEach>
                </tbody>
            </table></div>
        </form:form>
    </c:if>
    <c:if test="${empty memoByNotFoundList}">
    <p align="center">   No records Found.</p>
</c:if>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
