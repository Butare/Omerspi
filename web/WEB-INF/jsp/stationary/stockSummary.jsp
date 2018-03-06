<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<form:form  method="GET" >
    <table class="datatable">

        <thead>
            <tr>
                <th>Category</th>
                <th>Item Name</th>
                <th>Item Quantity</th> 

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${stationarySummary}" var="items">
                <tr>
                    <td>${items.categorytype.category.categoryName}</td>
                    <td>${items.itemName}</td>
                    <td>${items.totalQuantity}</td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>