<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="jim.omerspi.Constants" %>

<%
    pageContext.setAttribute("error", session.getAttribute(Constants.ERROR_SESSION_ATTR));
    session.removeAttribute(Constants.ERROR_SESSION_ATTR);

    pageContext.setAttribute("information", session.getAttribute(Constants.INFO_SESSION_ATTR));
    session.removeAttribute(Constants.INFO_SESSION_ATTR);
%>

<c:if test="${error!=null}">
    <div class="form-required-error">
        <c:out value="${error}"/>
    </div>

</c:if>

<c:if test="${information!=null}">
    <div class="form-informations">
        <c:out value="${information}"/>
    </div>
</c:if>