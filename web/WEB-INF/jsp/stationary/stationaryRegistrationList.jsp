
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">Stock List</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/stationary/edit"/>
<c:url var="deleteUrl" value="/stationary/delete"/>
<c:url var="formUrl" value="/stationary/form"/>

<form:form modelAttribute="editStationary" method="GET">

    <p align="center"> <a href="${formUrl}" > Purchased Item</a></p>

    <table class="datatable" >

        <thead>
            <tr>
                <th>Purchase Order</th> 
                <th>Delivery No.</th>
                <th>Supplier Name</th> 
                <th>Contract No.</th>
                <th>Item Name</th>
                <th>Specification</th> 
                <th>Quantity</th> 
                <th>Price/Unit</th> 
                <th>Total Price</th> 
                <th>Acquired On</th> 
                <th>Edit</th>
                <th>Delete</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${stationaryRegistrationList}" var="stationaryRegistration">
                <tr>
                    <td>${stationaryRegistration.purchaseOrder}</td>
                    <td>${stationaryRegistration.deliveryNoteNumber}</td>
                    <td>${stationaryRegistration.vendor.vendorName}</td>
                    <td>${stationaryRegistration.contractNumber}</td>
                    <td>${stationaryRegistration.items.itemName}</td>
                    <td>${stationaryRegistration.specification}</td>
                    <td>${stationaryRegistration.purchasedQty}</td>
                    <td>${stationaryRegistration.unitPrice}</td>
                    <td>${stationaryRegistration.purchasedQty*stationaryRegistration.unitPrice}</td>
                    <td>${stationaryRegistration.acquisitionDate}</td>


                    <td><a title="Edit" href="${editUrl}?stationaryId=${stationaryRegistration.stationaryRegistrationId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?stationaryId=${stationaryRegistration.stationaryRegistrationId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>