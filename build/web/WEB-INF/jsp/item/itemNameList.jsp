
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
        <h3 align="center">Item Names List</h3>
        <c:url var="editImgUrl" value="/images/edit.png" />
        <c:url var="deleteImgUrl" value="/images/erase.png"/>
        <c:url var="editUrl" value="/item/edit"/>
        <c:url var="deleteUrl" value="/item/delete"/>
        <c:url var="formUrl" value="/item/form"/>

        <p align="center"> <a href="${formUrl}">New Name</a></p>

        <form:form modelAttribute="itemList" method="GET" >
            <table class="datatable">

                <thead>
                    <tr>
                     
                        <th>Category Type</th>
                        <th>Item Name</th>                    
                        <th>Minimum Qty</th>
                        <th>Allowed</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${itemList}" var="item">
                        <tr>
                           
                            <td>${item.categorytype.categoryTypeName}</td>
                            <td>${item.itemName}</td>
                            <td>${item.minimumQty}</td>
                            <td>${item.isAllowed}</td>
                            <td><a title="Edit" href="${editUrl}?itemId=${item.itemId}"><img src="${editImgUrl}"></img></a></td>
                            <td><a title="Delete" href="${deleteUrl}?itemId=${item.itemId}"><img src="${deleteImgUrl}"></img></a></td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
        </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>