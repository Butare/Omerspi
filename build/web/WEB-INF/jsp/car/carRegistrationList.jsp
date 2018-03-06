
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<br/>
<h1 align="center">All Cars List</h1><br/>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/car/carRegistration/edit"/>
<c:url var="deleteUrl" value="/car/carRegistration/delete"/>
<c:url var="formUrl" value="/car/carRegistration/form"/>

<form:form  method="GET">

    <p align="center"> <a href="${formUrl}" > New Car</a></p>

    <table class="datatable">

        <thead>
            <tr>
              
                <th>Number Plate</th>  
                <th>Model</th> 
                <th>Cost</th> 
                <th>Manu. Year</th> 
                <th>Country</th> 
                <th>Car Status</th> 
                <th>Acquisition Date</th> 
                <th>Starting Date</th> 
                <th>Vendor</th> 
                <th>Car Condition</th> 
                <th>Service Status</th> 
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${carRegistrationList}" var="carRegistration">
                <tr>
              
                    <td>${carRegistration.plateNo}</td>
                    <td>${carRegistration.model}</td>
                    <td>${carRegistration.cost}</td>
                    <td>${carRegistration.manufacturedYear}</td>
                    <td>${carRegistration.country}</td>
                    <td>${carRegistration.status}</td>
                    <td>${carRegistration.acquiredDate}</td>
                    <td>${carRegistration.startDate}</td>
                    <td>${carRegistration.vendorName}</td>
                    <td>${carRegistration.carCondition}</td>
                    <td>${carRegistration.carServiceStatus}</td>

                    <td><a title="Edit" href="${editUrl}?carRegistrationId=${carRegistration.carRegistrationId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?carRegistrationId=${carRegistration.carRegistrationId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>