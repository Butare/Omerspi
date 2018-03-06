
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<script type="text/javascript">
    var dailyCost = ${vechicleDailyCostsJson};
    var hourlyCost = ${vechicleHourlyCostsJson};
    var iteneraryCost = ${iteneraryCostsJson};
    
    $(document).ready(function() {

        $('.chzn-select').chosen();

        //$('#returnTime,#departureTime').datetimepicker({
        //    timeFormat: "HH:mm"
        //});
      
        var startDateTextBox = $('#departureTime');
        var endDateTextBox = $('#returnTime');
    
        startDateTextBox.datetimepicker({ 
           
            minDate: 0,
            
            onClose: function(dateText, inst) {
                if (endDateTextBox.val() != '') {
                    var testStartDate = startDateTextBox.datetimepicker('getDate');
                    var testEndDate = endDateTextBox.datetimepicker('getDate');
                    if (testStartDate > testEndDate)
                        endDateTextBox.datetimepicker('setDate', testStartDate);
                    if(document.getElementById("itenerary").checked==false){
                        dateDifference(testStartDate,testEndDate);
                    }
                }
                else {
                    endDateTextBox.val(dateText);
                }
            },
            onSelect: function (selectedDateTime){
                endDateTextBox.datetimepicker('option', 'minDate', startDateTextBox.datetimepicker('getDate') );
            }
        });
        endDateTextBox.datetimepicker({ 
            
            onClose: function(dateText, inst) {
               
                if (startDateTextBox.val() != '') {
                    var testStartDate = startDateTextBox.datetimepicker('getDate');
                    var testEndDate = endDateTextBox.datetimepicker('getDate');
                    if (testStartDate > testEndDate)
                        startDateTextBox.datetimepicker('setDate', testEndDate);
                    if(document.getElementById("itenerary").checked==false){
                        dateDifference(testStartDate,testEndDate);
                    }
                }
                else {
                    startDateTextBox.val(dateText);
                }
            },
            onSelect: function (selectedDateTime){
                startDateTextBox.datetimepicker('option', 'maxDate', endDateTextBox.datetimepicker('getDate') );
            }
        });
    }); 
   
    function dateDifference(d1, d2) {
        var t2 = d2.getTime();
        var t1 = d1.getTime();
         
        var days =parseInt( (t2 - t1) / (24 * 3600 * 1000));
        var hours=parseInt(((t2-t1) / (3600*1000)) % 24);
        
        var carType = $("#carType").val();
       
        if(days!=0){
            $('#amt').val(days*dailyCost[carType]);
        }
        else if(hours!=0){ 
            $('#amt').val(hours*hourlyCost[carType]);
        }
    }
   
  
    function showItenerary(){
        
        var y=document.getElementById("itenerary");
        if(y.checked){
            document.getElementById("hideIteneraries").style.visibility="visible";
        }
        else{
            document.getElementById("hideIteneraries").style.visibility="hidden";
            document.getElementById("itenerary").check=false;
           
        }
    }
    
    function calculateItenerary(){
        if(document.getElementById("itenerary").checked){
            var itenerarys = $('#itenerarySelector').val()||[];
            var tot = 0;
            
            for(var i = 0; i < itenerarys.length; i++) {
                var iteneraryId = parseInt(itenerarys[i]); 
                tot += iteneraryCost[iteneraryId];
            }
            $('#amt').val(tot);     
        }
    }
</script>
<h3 align="center">${carRequisition.carRequisitionId != null ? "Edit" : "New" } Car Requisition Form</h3>
<p class="form-errors">${dateDiff}</p>
<form:form commandName="carRequisition" action="form" method="POST">
    <form:hidden path="carRequisitionId"/>

    <div class="centered">
        <table>

            <tr>
                <td>Reason</td>
                <td>
                    <form:textarea path="reason" cols="30" rows="1"/>
                    <form:errors path="reason" cssClass="error-validator"/>
                </td>

            </tr>
            <tr>
                <td>Destination</td>
                <td>
                    <form:input path="destination"/>
                    <form:errors path="destination" cssClass="error-validator"/>
                </td>

            </tr>

            <tr><td>Destination Type</td>
                <td>
                    <form:radiobutton path="destinationType" id="kigali" value="Kigali"/>Kigali
                    <form:radiobutton path="destinationType" id="countrySide" value="Countryside" />Countryside
                    <form:errors path="destinationType" cssClass="error-validator"/>
                </td>
            </tr>

            <tr><td>Costing Basis</td>
                <td>
                    <form:radiobutton path="costingBasis" value="itenerary" id="itenerary" onclick="showItenerary()" />Itenerary
                    <form:radiobutton path="costingBasis" value="hourlyDaily" id="hourlyDaily" onclick="showItenerary()" />Others(Hourly/Daily)
                    <form:errors path="costingBasis" cssClass="error-validator"/>
                </td>
            </tr>
            <tr id="hideIteneraries">
                <td>Itenerary</td>
                <td>
                    <form:select path="iteneraries" data-placeholder="Select.." class="chzn-select" id="itenerarySelector" onchange="calculateItenerary()" cssStyle="width:400px" >
                        <form:options items="${iteneraryList}" itemValue="iteneraryId" itemLabel="iteneraryDetail" />
                    </form:select>
                    <form:errors path="iteneraries" cssClass="error-validator"/>
                </td>

            </tr>

            <tr>
                <td>Means of Transport</td>
                <td>
                    <form:select path="cartype" data-placeholder="Select.." id="carType" >
                        <form:option value="" label="Select.."/>
                        <form:options items="${carTypeList}" itemValue="carTypeId" itemLabel="typeName"/>
                    </form:select>
                    <form:errors path="cartype" cssClass="error-validator"/>
                </td>

            </tr>
            <tr>
                <td>Baggage Weight</td>
                <td><form:input path="baggageWeight"/> (in Tons)</td>  
            </tr>

            <tr>
                <td>Departure Time</td>
                <td><form:input path="departureTime" id="departureTime" readonly="true" />
                    <form:errors path="departureTime" cssClass="error-validator"/>
                </td>
            </tr>
            <tr>
                <td>Expected Return Time</td>
                <td><form:input path="expectedTimeReturn" id="returnTime" readonly="true" />
                    <form:errors path="expectedTimeReturn" cssClass="error-validator"/>
                </td>
            </tr>

            <tr>
                <td>Total Amount</td>
                <td><form:input path="totalAmount" id="amt" readonly="true" />Rwf</td> 
            </tr>


            <tr colspan="2">
                <td><input type="submit" value="${carRequisition.carRequisitionId != null ? "Save Changes" : "Save" }"/></td> 


            </tr>

        </table></div> 

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>