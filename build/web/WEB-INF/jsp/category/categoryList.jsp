
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">Category List</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/category/edit"/>
<c:url var="deleteUrl" value="/category/delete"/>
<c:url var="formUrl" value="/category/form"/>

<p align="center" > <a href="${formUrl}">Add Category</a></p>

<form:form method="GET">

    <table class="datatable">

        <thead>
            <tr>
                <th>Category Code</th>
                <th>Category Name</th> 
                <th>Description</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${categoryList}" var="category">
                <tr>
                    <td>${category.categoryCode}</td>
                    <td>${category.categoryName}</td>
                    <td>${category.categoryDescription}</td>
                    <td><a title="Edit" href="${editUrl}?categoryId=${category.categoryId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?categoryId=${category.categoryId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>