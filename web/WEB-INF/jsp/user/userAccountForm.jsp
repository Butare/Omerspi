<%-- 
    Document   : createAccount
    Created on : May 8, 2013, 10:10:03 AM
    Author     : JOHN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/header/loginHeader.jsp" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Account</title>
</head>

<body>
    <br/>
    <h3 align="center">${user.userRegistrationId != null ? "Edit" : "Create" } Account</h3>
    <br/>
    <form:form modelAttribute="user" action="form">
        <form:hidden path="userRegistrationId"/>

        <div class="centered"> <table>
                <tr>
                    <td>Username</td>
                    <td><form:input path="userName"  cssClass="login-input" cssStyle="width:200px;" autocomplete="off" /></td>
                    <td><form:errors path="userName" cssClass="error-validator" /></td>
                </tr>
                <c:if test="${empty user.userRegistrationId }">
                    <tr>
                        <td>Password</td>
                        <td><form:password path="password" cssClass="login-input" cssStyle="width:200px;" /></td>
                        <td><form:errors path="password" cssClass="error-validator" /></td>
                    </tr>
                    <tr>
                        <td>Confirm Password</td>
                        <td><input name="confirmPassword" type="password" class="login-input" style="width: 200px" /></td>
                        <td class="error-validator"><c:if test="${confirmPassword!=null}">
                                ${confirmPassword}
                            </c:if></td>
                    </tr>
                </c:if>
                <tr>
                    <td>Employee Code</td>
                    <td> <form:select path="employee" cssClass="login-input" cssStyle="width:210px;" >
                            <form:option value="" label="--Select--"/>
                            <form:options items="${employeeList}" itemLabel="employeeCode" itemValue="employeeRegistrationId" />
                        </form:select></td>
                    <td><form:errors path="employee" cssClass="error-validator" /></td>
                </tr>
                <c:if test="${empty user.userRegistrationId}">
                    <tr>
                        <td>Identification Word</td>
                        <td><input name="identificationWord"  autocomplete="off" class="login-input" style="width: 200px"  /></td>
                        <td class="error-validator"><c:if test="${identificationWord!=null}">
                                ${identificationWord}
                            </c:if></td>
                    </tr>
                </c:if>
                <tr colspan="2">
                    <td><input type="submit" value="Submit"/></td> 
                </tr>
            </table>  </div>
        </form:form>

</body>
</html>
<%@include file="/WEB-INF/jsp/footer/loginFooter.jsp" %>