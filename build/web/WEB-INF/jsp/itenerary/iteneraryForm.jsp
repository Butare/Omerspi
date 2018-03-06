
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<script type="text/javascript">
    $(document).ready(function(){
        $("#cost").keydown(function (e) {
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
        $("#cost").bind({
            paste:function(e){
                e.preventDefault();
            }
        })
        
    });  
    
</script>


<h3 align="center">${itenerary.iteneraryId!=null?"Edit ":"New "} Itenerary Form</h3>

<c:url var="saveUrl" value="/itenerary/form"/>

<form:form commandName="itenerary" method="POST" action="${saveUrl}">
    <form:hidden path="iteneraryId"/>
    <div class="centered"> <table>
            <tr>
                <td>Itenerary Name</td>  
                <td> <form:input path="iteneraryDetail"/></td>
                <td><form:errors path="iteneraryDetail" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Itenerary Cost</td>  
                <td> <form:input path="cost" id="cost"/></td>
                <td><form:errors path="cost" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td><input type="submit" value=${itenerary.iteneraryId != null ? "Edit Itenerary":"Add Itenerary"} /></td>
            </tr>
        </table></div> 

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
