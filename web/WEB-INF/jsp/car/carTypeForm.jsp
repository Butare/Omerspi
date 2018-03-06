
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<script type="text/javascript">
    $(document).ready(function(){
        $("#daily,#hourly").keydown(function (e) {
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
        $("#daily,#hourly").bind({
            paste:function(e){
                e.preventDefault();
            }
        })
        
    });  
    
</script>
<h3 align="center">${carType.carTypeId!=null?"Edit ":"New "} Car Type Form</h3>
<c:url var="saveUrl" value="/carType/form"/>
<form:form commandName="carType" method="POST" action="${saveUrl}">
    <form:hidden path="carTypeId"/>
    <div class="centered"> <table>
            <tr>
                <td>Type Name</td>  
                <td> <form:input path="typeName"/></td>
                <td><form:errors path="typeName" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Daily Cost</td>  
                <td> <form:input path="dailyCost" id="daily"/></td>
                <td><form:errors path="dailyCost" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Hourly Cost</td>  
                <td> <form:input path="hourlyCost" id="hourly"/></td>
                <td><form:errors path="hourlyCost" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td><input type="submit" value=${carType.carTypeId != null ? "Edit Car Type":"Add Car Type"} /></td>
            </tr>
        </table></div> 

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>

