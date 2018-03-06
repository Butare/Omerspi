
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<h3 align="center" strong="2">${memo.memoId!=null?"Edit ":"New "}Memo Form</h3>

<c:url var="saveUrl" value="/saveOrUpdateMemo"/>

<form:form commandName="memo" method="POST" action="${saveUrl}">
    <form:hidden path="memoId"/>
    <form:hidden path="notfounditemrequisition" />
    <form:hidden path="carrequisition" />
    <div class="centered">  <table>
            <tr>
                <td>Send To:</td>
                <td><form:input path="sendTo"/></td>
            </tr>
            <tr>
                <td>Cc</td>
                <td><form:textarea cols="30" rows="2" path="cc"/></td>
            </tr>
            <tr>
                <td>Subject</td>
                <td><form:textarea rows="2" cols="30" path="subject"/></td>
            </tr>

            <tr colspan="2">
                <td><input type="submit" value=${memo.memoId !=null?"Save Changes":"Save"}></td> 

            </tr>
        </table></div>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>