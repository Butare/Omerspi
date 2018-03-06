
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<script type="text/javascript">
    
    $(document).ready(function(){
        $('#category').change(function(){
            var categoryId=$(this).val();
            location.href='form?categoryId='+categoryId;
        })
    });
</script>

<c:url var="saveUrl" value="form"/>
<h3 align="center">${item.itemId != null ? "Edit" : "New" } Item Name</h3>
<form:form commandName="item" action="${saveUrl}" method="POST">
    <form:hidden path="itemId"/>
    <form:hidden path="totalQuantity"/>

    <div class="centered">  <table colspan="2">
            <tr>
                <td>Category Name</td>
                <td>
                    <select id="category">
                        <c:if test="${item.itemId == null}">
                            <option value="" label="--Select--"/>
                        </c:if>
                        <c:forEach items="${categoryList}" var="category">
                            <option label="${category.categoryName}" value="${category.categoryId}" ${category == selectedCategory ? "selected" : ""} />
                        </c:forEach>

                    </select>
                </td> 
            </tr> 
            <tr>
                <td>Item Type</td>
                <td> 
                    <form:select path="categorytype">
                        <form:option value="" label="--Select--"/>
                        <form:options items="${categoryTypeList}" itemLabel="categoryTypeName" itemValue="categoryTypeId" />
                    </form:select>
                </td>
                <td><form:errors path="categorytype" cssClass="error-validator"/></td>
            </tr> 
            <tr>
                <td>Item Name</td>
                <td><form:input path="itemName"/></td>
                <td><form:errors path="itemName" cssClass="error-validator"/></td>
            </tr> 
            <tr>
                <td>Minimum Qty</td>
                <td><form:input path="minimumQty"/></td>
                <td><form:errors path="minimumQty" cssClass="error-validator"/></td>
            </tr> 
            <tr>
                <td>Allowed?</td>
                <td><form:radiobutton path="isAllowed" value="true"/>Yes&nbsp;&nbsp;
                    <form:radiobutton path="isAllowed" value="false"/>No

                </td>
                <td><form:errors path="minimumQty" cssClass="error-validator"/></td>
            </tr> 


        </table></div>
    <br/>
    <p  align="center"> <input  type="submit" value="Add Item Name"/></p>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>