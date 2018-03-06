<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">Car Type List</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/carType/edit"/>
<c:url var="deleteUrl" value="/carType/delete"/>
<c:url var="formUrl" value="/carType/form"/>

<p align="center" > <a href="${formUrl}">Add Car Type</a></p>

<form:form method="GET">


    <table class="datatable">

        <thead>
            <tr>
                <th>Type Name</th> 
                <th>Daily Cost</th>
                <th>hourly Cost</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${carTypeList}" var="carType">
                <tr>
                    <td>${carType.typeName}</td>
                    <td>${carType.dailyCost}</td>
                    <td>${carType.hourlyCost}</td>
                    <td><a title="Edit" href="${editUrl}?carTypeId=${carType.carTypeId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?carTypeId=${carType.carTypeId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
