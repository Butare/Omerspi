
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h2 align="center">Department Records</h2>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/department/edit"/>
<c:url var="deleteUrl" value="/department/delete"/>
<c:url var="formUrl" value="/department/form"/>
<br/>
<p style="text-align:center;">  <a href="${formUrl}">Add Department</a></p>

<form:form modelAttribute="editDept" method="GET">
    <table class="datatable" id="departmentList" >

        <thead>
            <tr>

                <th></th>
                <th>Department Code</th>  
                <th>Department Name</th> 
                <th>Functional Location</th> 
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${departmentList}" var="department">
                <tr >           
                    <td></td>
                    <td>${department.departmentCode}</td>
                    <td>${department.departmentName}</td>
                    <td>${department.functionalLocation}</td>
                    <td><a title="Edit" href="${editUrl}?departmentId=${department.departmentRegistrationId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?departmentId=${department.departmentRegistrationId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>