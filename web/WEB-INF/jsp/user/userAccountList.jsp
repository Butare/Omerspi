
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">User List</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/user/edit"/>
<c:url var="deleteUrl" value="/user/delete"/>


<form:form modelAttribute="editUser" method="GET">
    <table class="datatable">

        <thead>
            <tr>

                <th>Username</th>  
                <th>Employee Code</th> 
                <th>Last Login Time</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>

                    <td>${user.userName}</td>
                    <td>${user.employee.employeeCode}</td>
                    <td>${user.lastLoginTime}</td>
                  <!--  <td><a title="Edit" href="${editUrl}?userId=${user.userRegistrationId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?userId=${user.userRegistrationId}"><img src="${deleteImgUrl}"></img></a></td>
                    --> </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>