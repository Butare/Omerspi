
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<script type="text/javascript">
    $(document).ready(function(){
        $("#cost,#year").keydown(function (e) {
            if (e.shiftKey || e.ctrlKey || e.altKey) { // if shift, ctrl or alt keys held down
                e.preventDefault();         // Prevent character input
            } else {
                var n = e.keyCode;
                if (!((n == 8)              // backspace
                    || (n == 46)                // delete
                    || (n >= 35 && n <= 40)     // arrow keys/home/end
                    || (n >= 48 && n <= 57)     // numbers on keyboard
                    || (n >= 96 && n <= 105))   // number on keypad
            ) {
                    e.preventDefault();
                    // alert("in if");
                    // Prevent character input
                }
            }
        });
        $("#cost,#year").bind({
            paste:function(e){
                e.preventDefault(e);
            },
            cut:function(e){
                e.preventDefault(e);
            }
        })
        $("#serviceStatus").chosen();
    });
</script>
<h3>align="center">Car Registration Form</h3>
<c:url var="saveUrl" value="form"/>

<form:form commandName="carRegistration" method="POST" action="${saveUrl}">
    <form:hidden path="carRegistrationId"/>
    <div class="centered">  <table>

            <tr>
                <td>Number Plate</td>
                <td><form:input path="plateNo"/>
                    <form:errors path="plateNo" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Car Model</td>
                <td><form:input path="model"/>
                    <form:errors path="model" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Cost</td>
                <td><form:input path="cost" id="cost"/>
                    <form:errors path="cost" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Manufacturing Year</td>
                <td><form:input path="manufacturedYear" id="year"/> 
                    <form:errors path="manufacturedYear" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Country</td>
                <td>
                    <form:input path="country"/>
                    <form:errors path="country" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Car Status</td>
                <td><form:input path="status"/>
                    <form:errors path="status" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Vendor Name</td>
                <td><form:input path="vendorName"/>
                    <form:errors path="vendorName" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Car Condition</td>
                <td><form:input path="carCondition"/>
                    <form:errors path="carCondition" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Service Status</td>
                <td><form:select path="carServiceStatus" id="serviceStatus" cssStyle="width:165px">
                        <form:option value="" label="--Select--"/> 
                        <form:options items="${serviceList}"/>
                    </form:select>
                    <form:errors path="carServiceStatus" cssClass="error-validator"/>
                </td>
            </tr>
            <tr colspan="2">
                <td><input type="submit" value="Save"/></td> 

            </tr>

        </table></div> 
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
