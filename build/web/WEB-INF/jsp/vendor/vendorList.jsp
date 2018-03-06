
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">Company List</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/vendor/edit"/>
<c:url var="deleteUrl" value="/vendor/delete"/>
<c:url var="formUrl" value="/vendor/form"/>
<p align="center"> <a href="${formUrl}">Add Company</a></p>

<form:form modelAttribute="editVendor" method="GET">
    <table class="datatable" >

        <thead>
            <tr>
                <th>Vendor Name</th>  
                <th>Address</th> 
                <th>Email</th> 
                <th>Telephone</th> 
                <th>Tender Number</th> 
                <th>Tender Purpose</th>
                <th>St. Date</th>
                <th>end. Date</th>
                <th></th>
                <th></th>

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${vendorList}" var="vendor">
                <tr>
                    <td>${vendor.vendorName}</td>
                    <td>${vendor.address}</td>
                    <td>${vendor.email}</td>
                    <td>${vendor.telephone}</td>
                    <td>${vendor.tenderNumber}</td>
                    <td>${vendor.tenderPurpose}</td>
                    <td>${vendor.startingPeriod}</td>
                    <td>${vendor.endingPeriod}</td>
                    <td><a title="Edit" href="${editUrl}?vendorId=${vendor.vendorRegistrationId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?vendorId=${vendor.vendorRegistrationId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>