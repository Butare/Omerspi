
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/header/includes.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>


        <form:form modelAttribute="viewItenerary" method="GET">

            <table >

                <tbody style="background:#ccc">
                    <c:forEach items="${iteneraryListByCarRequisition}" var="itenerary">
                        <tr> 
                            <td>${itenerary.iteneraryDetail}</td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>

        </form:form>
        <c:if test="${empty iteneraryListByCarRequisition}">
            <p align="center"> Has no Itenerary </p> 
        </c:if>
    </body>
</html>