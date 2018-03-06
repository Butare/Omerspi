
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<h3 align="center">${privilege.privilege!=null?"Edit ":"New "} Privilege Form</h3>
<c:url var="saveUrl" value="/privilege/form"/>

<form:form commandName="privilege" method="POST" action="${saveUrl}">
    <div class="centered"> <table>

            <tr>
                <td>Privilege</td>
                <c:if test="${privilege.privilege!=null}">
                    <td> <form:input path="privilege" readonly="false"/></td>   
                </c:if>
                <c:if test="${privilege.privilege==null}">
                    <td> <form:input path="privilege" readonly="false"/></td>   
                </c:if>
                <td><form:errors path="privilege" cssClass="error-validator"/></td>
            </tr>

            <tr>
                <td>Privilege Description</td>  
                <td> <form:input path="previlageDescription"/></td>
                <td><form:errors path="previlageDescription" cssClass="error-validator"/></td>
            </tr>

            <tr>
                <td><input type="submit" value=${privilege.privilege != null ? "Edit Privilege":"Add Privilege"} /></td>
            </tr>
        </table></div> 

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
