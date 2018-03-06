
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<script type="text/javascript"> 
    $(document).ready(function(){
        $("#qty,#unit").keydown(function (e) {
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
        $("#item").chosen();
        
        $("#qty,#unit").bind({
            copy : function(e){
                $('span').text('copy behaviour detected!');
            },
            paste : function(e){
                e.preventDefault();
            },
            cut : function(e){
                e.preventDefault();
            }
        });   
    });
    
    function calculate(){
        var  qty=parseInt($("#qty").val());
        var unit=parseInt($("#unit").val());
        var total=qty*unit;
        document.getElementById("total").value=total;
    }
 
</script>

<c:url var="saveUrl" value="form"/>
<form:form commandName="stationaryRegistrations" method="POST" action="${saveUrl}" name="stationary" >
    <h3 align="center">${stationaryRegistrations.stationaryRegistrationId != null ? "Edit " : "New " }Item Registration Form</h3><br/>
    <form:hidden path="stationaryRegistrationId"/>
    <c:if test="${stationaryRegistrations.stationaryRegistrationId!=null}">
        <form:hidden path="acquisitionDate"/>
        <form:hidden path="employee"/>
        <form:hidden path="openingStock"/>
    </c:if>
    <div class="centered">  <table>
            <tr>
                <td>Purchase Order</td>
                <td><form:input path="purchaseOrder"/>
                    <form:errors path="purchaseOrder" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Delivery note No.</td>
                <td>
                    <form:input path="deliveryNoteNumber"/>
                    <form:errors path="deliveryNoteNumber" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Supplier Name</td>
                <td>
                    <form:select path="vendor">
                        <form:option value="" label="--Select--"/>
                        <form:options items="${equipmentVendorList}" itemLabel="vendorName" itemValue="vendorRegistrationId"/>
                    </form:select>
                    <form:errors path="vendor" cssClass="error-validator"/>
                </td>
            </tr>
            <tr><td>Contract No.</td>
                <td>
                    <form:input path="contractNumber"/>
                    <form:errors path="contractNumber" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Item Name</td>
                <td> 
                    <form:select path="items" id="item" cssStyle="width:160px">
                        <form:option value="" label="--Select--"/>
                        <form:options items="${itemList}" itemLabel="itemName" itemValue="itemId" />
                    </form:select>
                    <form:errors path="items" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Specifications</td>
                <td><form:textarea path="specification" rows="1" cols="30"/>
                    <form:errors path="specification" cssClass="error-validator"/>
                </td>
            </tr>

            <tr>
                <td>Purchased Qty</td>
                <td>
                    <form:input path="purchasedQty" id="qty" onchange="calculate()" autocomplete="off" />
                    <input type="hidden" name="oldQuantity" value="${stationaryRegistrations.purchasedQty}" />
                    <form:errors path="purchasedQty" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Price/Unit</td>
                <td><form:input path="unitPrice" id="unit" onchange="calculate()" autocomplete="off" />Rwf
                    <form:errors path="unitPrice" cssClass="error-validator"/>
                </td>
            </tr>

            <tr>
                <td>Total Price</td>
                <td><input type="text" name="totalPrice" value="0" id="total" readonly="true" />Rwf</td>

            </tr>
            <tr colspan="2">
                <td><input type="submit" value="Add Stationary"/></td> 
            </tr>

        </table></div> 
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>