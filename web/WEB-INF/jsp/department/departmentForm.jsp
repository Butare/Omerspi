
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<br/>
<h3 align="center">${department.departmentRegistrationId != null ? "Edit" : "New" } Department Form</h3>
<br/>
<h3><c:out value="${message}"/></h3>
<form:form commandName="department" action="form" method="POST">
    <form:hidden path="departmentRegistrationId"/>
    <div class="row-fluid">
        <div class="centered"> 
            <table>
                <tr>
                    <td>Department Code</td>
                    <td><form:input path="departmentCode" /></td>
                    <td><form:errors path="departmentCode" cssClass="error-validator"/></td>
                </tr>
                <tr>
                    <td><springs:message code="department.name"/></td>
                    <td><form:input path="departmentName"/></td>
                    <td><form:errors path="departmentName" cssClass="error-validator"/></td>
                </tr>
                <tr>
                    <td>Functional Location</td>
                    <td><form:input path="functionalLocation"/></td>
                    <td><form:errors path="functionalLocation" cssClass="error-validator"/></td>

                </tr>
                <tr colspan="2">
                    <td><input type="submit" value="${department.departmentRegistrationId != null ? "Save Changes" : "Save" }"/></td> 

                </tr>
            </table>  
        </div> 
    </div>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>