<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<h3 align="center">Privilege List</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/privilege/edit"/>
<c:url var="deleteUrl" value="/privilege/delete"/>
<c:url var="formUrl" value="/privilege/form"/>

<p align="center" > <a href="${formUrl}">Add Privilege</a></p>

<form:form method="GET">


    <table class="datatable">

        <thead>
            <tr>
                <th>Privilege</th> 
                <th>Description</th> 
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${privilegeList}" var="privilege">
                <tr>
                    <td>${privilege.privilege}</td>
                    <td>${privilege.previlageDescription}</td>
                    <td><a title="Edit" href="${editUrl}?privilege=${privilege.privilege}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?privilege=${privilege.privilege}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
