
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<script type="text/javascript">
    $(document).ready(function(){
        $("#phone").keydown(function (e) {
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
        $("#phone").bind({
            paste:function(e){
                e.preventDefault();
            }
        })

        var opts = {
           
            changeMonth: true,
            dateFormat:"dd-M-yy",
            altFormat: "yy-mm-dd",
            onSelect: function (selectedDateTime){
                $('#startingPeriodDisplay').datepicker('option', 'maxDate', $('#endingPeriodDisplay').datepicker('getDate') );
                $('#endingPeriodDisplay').datepicker('option', 'minDate', $('#startingPeriodDisplay').datepicker('getDate'));
            }
        };
        
        $('#startingPeriodDisplay').datepicker(
        $.extend({
            altField: '#startingPeriodHidden'
        }, opts)
    );
        $('#endingPeriodDisplay').datepicker(
        $.extend({
            altField: '#endingPeriodHidden'
        }, opts)
         
    );

    });  
    
</script>

<h3 align="center">${vendor.vendorRegistrationId !=null ?" Edit ":" New " }Company Form</h3>
<c:url var="formUrl" value="form"/>
<form:form commandName="vendor" method="POST" action="${formUrl}">
    <form:hidden path="vendorRegistrationId"/>
    <div class="centered"> <table>
            <tr>
                <td>Vendor Name </td>
                <td><form:input path="vendorName"/></td>  
                <td><form:errors path="vendorName" cssClass="error-validator"/></td>
            </tr> 
            <tr>
                <td>Address </td>
                <td><form:input path="address"/></td>  
                <td><form:errors path="address" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Email </td>
                <td><form:input path="email" title="Email"/></td>
                <td><label for="">eg: omerspi@gmail.com </label>&nbsp;&nbsp;
                    <form:errors path="email" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Phone Number </td>
                <td><form:input path="telephone" id="phone"/></td>  
                <td><form:errors path="telephone" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Tender Number </td>
                <td><form:input path="tenderNumber"/></td>  
                <td><form:errors path="tenderNumber" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Tender Purpose</td>
                <td>
                    <form:select path="tenderPurpose">
                        <form:option value="" label="--Select--"/>
                        <form:options items="${purposeList}"/>
                    </form:select>
                </td>
                <td><form:errors path="tenderPurpose" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Starting Period</td>
                <td>
                    <c:if test="${vendor.vendorRegistrationId !=null}">
                        <input id="startingPeriodDisplay" type="text" value="${vendor.startingPeriod}" />
                    </c:if>
                    <c:if test="${vendor.vendorRegistrationId ==null}">
                        <input id="startingPeriodDisplay" type="text" />
                    </c:if>
                    <form:hidden path="startingPeriod" id="startingPeriodHidden"/>
                </td>
                <td><form:errors path="startingPeriod" cssClass="error-validator"/></td>

            </tr>
            <tr>
                <td>Ending Period</td>
                <td>
                    <c:if test="${vendor.vendorRegistrationId !=null}">
                        <input id="endingPeriodDisplay" type="text" value="${vendor.endingPeriod}" /> 
                    </c:if>
                    <c:if test="${vendor.vendorRegistrationId ==null}">       
                        <input id="endingPeriodDisplay" type="text"/>
                    </c:if>     
                    <form:hidden path="endingPeriod" id="endingPeriodHidden"/>
                </td>
                <td> <form:errors path="endingPeriod" cssClass="error-validator"/></td>

            </tr>

            <tr><td><input type="submit" value=${vendor.vendorRegistrationId !=null ?" Save Changes ":" Save " }/></td></tr>
        </table>  </div> 


</form:form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>