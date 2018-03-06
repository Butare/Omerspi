<%-- 
    Document   : stationaryRequisitionForm
    Created on : May 21, 2013, 8:54:17 AM
    Author     : JIMMY
--%>

<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        $('#category').change(function() {
            var categoryId = $(this).val();
            location.href='addItemForm?categoryId='+categoryId;
        });
        
        $('#categoryType').change(function() {
            var categoryId=$('#category').val();
            var categoryTypeId = $(this).val();
            location.href='addItemForm?categoryId='+categoryId+'&categoryTypeId='+categoryTypeId;
        });
        
        $("#qty").keydown(function (e) {
            if (e.shiftKey || e.ctrlKey || e.altKey) { // if shift, ctrl or alt keys held down
                e.preventDefault();         // Prevent character input
            } else {
                var n = e.keyCode;
                if (!((n == 8)              // backspace
                    || (n == 46)                // delete
                    || (n >= 35 && n <= 40)     // arrow keys/home/end
                    || (n >= 48 && n <= 57)     // numbers on keyboard
                    || (n >= 96 && n <= 105))   // number on keypad
            ) {
                    e.preventDefault();
                    // alert("in if");
                    // Prevent character input
                }
            }
        });
        $("#qty").bind({
            paste: function(e){
                e.preventDefault(e);  
            }
        });
        
    });
</script>

<c:url var="saveUrl" value="addItem"/>

<h3 align="center">Add Item </h3>
<form action="${saveUrl}" method="POST">

    <div class="centered"> <table colspan="2">
            <tr>
                <td>Category Name</td>
                <td>
                    <select id="category">
                        <option value="" label="--Select--"/>

                        <c:forEach items="${categoryList}" var="category">
                            <option label="${category.categoryName}" value="${category.categoryId}" ${category == selectedCategory ? "selected" : ""} />
                        </c:forEach>
                    </select>
                </td> 

            </tr> 
            <tr>
                <td>Item Type</td>
                <td> 
                    <select id="categoryType">
                        <option value="" label="--Select--"/>
                        <c:forEach items="${categoryTypeList}" var="categoryType">
                            <option label="${categoryType.categoryTypeName}" value="${categoryType.categoryTypeId}" ${categoryType == selectedCategoryType ? "selected" : ""}  />
                        </c:forEach>
                    </select>
                </td>
            </tr> 
            <tr>
                <td>Item Name</td>
                <td><select name="itemId">

                        <option value="" label="--Select--"/>
                        <c:forEach items="${stationaryList}" var="stationary">
                            <option label="${stationary.items.itemName}" value="${stationary.items.itemId}" />
                        </c:forEach>
                    </select>

                </td>
            </tr> 

            <tr>
                <td>Quantity</td>
                <td> 
                    <input name="quantity" style="width: 210px" id="qty" />
                </td>
            </tr> 
            <tr><td><input type="submit" value="Add Item"/></td></tr> 
        </table>
    </div>
</form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>