<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">Itenerary List</h3>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/itenerary/edit"/>
<c:url var="deleteUrl" value="/itenerary/delete"/>
<c:url var="formUrl" value="/itenerary/form"/>

<p align="center" > <a href="${formUrl}">Add Itenerary</a></p>

<form:form method="GET">


    <table class="datatable">

        <thead>
            <tr>
                <th>Itenerary Name</th> 
                <th>Itenerary Cost</th> 
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${iteneraryList}" var="itenerary">
                <tr>
                    <td>${itenerary.iteneraryDetail}</td>
                    <td>${itenerary.cost}</td>
                    <td><a title="Edit" href="${editUrl}?iteneraryId=${itenerary.iteneraryId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?iteneraryId=${itenerary.iteneraryId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>