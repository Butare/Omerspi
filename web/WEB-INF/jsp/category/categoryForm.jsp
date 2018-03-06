
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">${category.categoryId!=null?"Edit ":"New "} Category Form</h3><br/>
<c:url var="saveUrl" value="/category/form"/>
<form:form commandName="category" method="POST" action="${saveUrl}">
    <form:hidden path="categoryId"/>
    <div class="centered"> <table>
            <tr>
                <td>Category Code</td>
                <td><form:input path="categoryCode"/></td>
                <td><form:errors path="categoryCode" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Category Name</td>  
                <td> <form:input path="categoryName"/></td>
                <td><form:errors path="categoryName" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><form:input path="categoryDescription"/></td>
                <td><form:errors path="categoryDescription" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td><input type="submit" value=${category.categoryId != null ? "Edit Category":"Add Category"} /></td>
            </tr>
        </table></div> 

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>