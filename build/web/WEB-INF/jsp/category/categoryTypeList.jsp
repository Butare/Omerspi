
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">Category Type List</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/categoryType/edit"/>
<c:url var="deleteUrl" value="/categoryType/delete"/>
<c:url var="formUrl" value="/categoryType/form"/>

<p align="center" > <a href="${formUrl}">Add Category Type</a></p>

<form:form method="GET">

    <table class="datatable">

        <thead>
            <tr>
                <th>Category Name</th> 
                <th>Category Type</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${categoryTypeList}" var="categoryType">
                <tr>
                    <td>${categoryType.category.categoryName}</td>
                    <td>${categoryType.categoryTypeName}</td>
                    <td><a title="Edit" href="${editUrl}?categoryTypeId=${categoryType.categoryTypeId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?categoryTypeId=${categoryType.categoryTypeId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
