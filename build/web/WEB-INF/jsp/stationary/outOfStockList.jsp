<%@include file="/WEB-INF/jsp/header/header.jsp" %>


<c:url var="formUrl" value="/stationary/form"/>

<form:form  method="GET" >

    <p align="center"> <a href="${formUrl}" > Add </a></p>

    <table class="datatable">

        <thead>
            <tr>
                <th>Category</th>
                <th>Item Name</th>
                <th>Minimum Qty</th>
                <th>Remaining Qty</th> 

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${outOfStock}" var="stock">
                <tr>

                    <td>${stock.item.category.categoryName}</td>
                    <td>${stock.item.itemName}</td>
                    <td>${stock.minimumQty}</td>
                    <td>${stock.item.itemQty}</td>

                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>