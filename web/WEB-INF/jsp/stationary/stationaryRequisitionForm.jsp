<%-- 
    Document   : stationaryRequisitionForm
    Created on : May 21, 2013, 3:13:09 PM
    Author     : JIMMY
--%>

<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/editItem"/>
<c:url var="deleteUrl" value="/deleteItem"/>
<c:url var="formUrl" value="addItemForm"/>
<c:url var="saveUrl" value="saveOrUpdateRequestedItems"/>
<p align="center"><a  href="${formUrl}">Add Item</a></p> 

<c:if test="${!empty requestedItemMap}">
    <h3 align="center">Requested Item(s) Form</h3>
    <form:form modelAttribute="requestedItemList" method="GET" action="saveOrUpdateRequestedItems" >
        <div class="centered">   <table style="border: 1px solid; width: 40%; text-align:center">

                <thead style="background:#d3dce3">
                    <tr>

                        <th>Item Name</th> 
                        <th>Quantity</th> 
                        <th>Edit</th> 
                        <th>Delete</th> 
                    </tr>
                </thead>
                <tbody style="background:#ccc">
                    <c:forEach items="${requestedItemMap}" var="entry">
                        <tr>
                            <td>${entry.key.itemName}</td>
                            <td>${entry.value}</td>
                            <td><a title="Edit" href="${editUrl}?itemId=${entry.key.itemId}"><img src="${editImgUrl}"></img></a></td>
                            <td><a title="Delete" href="${deleteUrl}?itemId=${entry.key.itemId}"><img src="${deleteImgUrl}"></img></a></td>
                        </tr>

                    </c:forEach>

                </tbody>
            </table></div>
       <br/> <p align="center"><input type="submit" value="Submit Requisition"/></p>
    </form:form>
</c:if>
<c:if test="${empty requestedItemMap}">
    <br/> <p align="center"> You have not added any item to your Stationary requisition </p>
</c:if>
</body>
</html>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
