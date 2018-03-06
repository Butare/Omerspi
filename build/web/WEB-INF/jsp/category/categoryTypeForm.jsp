<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">${categoryType.categoryTypeId!=null?"Edit ":"New "} Category Type Form</h3><br/>
<c:url var="saveUrl" value="/categoryType/form"/>
<form:form commandName="categoryType" method="POST" action="${saveUrl}">
    <form:hidden path="categoryTypeId"/>
    <div class="centered"> <table>
            <tr>
                <td>Category Name</td>
                <td>
                    <form:select path="category">
                        <form:option value="" label="--Select--"/>
                        <form:options items="${categoryList}" itemValue="categoryId" itemLabel="categoryName"/>
                    </form:select>
                </td>
                <td><form:errors path="category" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Type Name</td>  
                <td> <form:input path="categoryTypeName"/></td>
                <td><form:errors path="categoryTypeName" cssClass="error-validator"/></td>
            </tr>

            <tr>
                <td><input type="submit" value=${categoryType.categoryTypeId != null ? "Edit Type":"Add Type"} /></td>
            </tr>
        </table></div> 

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>