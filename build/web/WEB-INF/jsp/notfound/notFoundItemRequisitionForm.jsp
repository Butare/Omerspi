
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<h3 align="center">${notFoundItemRequisition.notFoundRequisitionId != null ? "Edit" : "New" } Not Found Requisition Form</h3>
<c:url var="saveUrl" value="/notFoundItemRequisition/saveOrUpdateNotFoundItemRequisition"/>

<form:form commandName="notFoundItemRequisition" method="POST" action="${saveUrl}">
    <form:hidden path="notFoundRequisitionId"/>
    <div class="centered"><table>

            <tr>
                <td>Description</td>
                <td><form:textarea cols="30" rows="2" path="description"/></td>
            </tr>
            <tr>
                <td>Quantity</td>
                <td><form:input path="quantity"/></td>
            </tr>
            <tr>
                <td>Type</td>
                <td>
                    <form:select path="type">
                        <form:option value="" label="--Select--"/>
                        <form:options items="${typeList}" />
                    </form:select>
                </td>
            </tr>

            <tr>
                <td>Reason</td>
                <td><form:textarea rows="2" cols="30" path="reason"/></td>
            </tr>

            <tr colspan="2">
                <td><input type="submit" value=${notFoundItemRequistion.notFoundRequisitionId !=null?"Save Changes":"Save"}></td> 

            </tr>
        </table></div>  
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>