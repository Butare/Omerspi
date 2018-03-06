
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<script type="text/javascript">

    $(document).ready(function() {

        var startDateTextBox=$('#start');
        var endDateTextBox = $('#end');
        
        startDateTextBox.datepicker({
            dateFormat:'dd-M-yy',
            maxDate:0,
            onClose: function(dateText,inst){
            
                if(endDateTextBox.val()!=''){
                    var testStartDate=startDateTextBox.datepicker('getDate');
                    var testEndDate = endDateTextBox.datetimepicker('getDate');
                    if(testStartDate>testEndDate)
                        endDateTextBox.datetimepicker('setDate', testStartDate);
                }
                else {
                    endDateTextBox.val(dateText);
                }
            
            },
            onSelect: function(selectedDateTime){
                endDateTextBox.datepicker('option', 'minDate', startDateTextBox.datepicker('getDate'));
                
            }
        })
        
 
        endDateTextBox.datepicker({ 
            dateFormat: 'dd-M-yy',
            maxDate:0,
            onClose: function(dateText, inst) {
               
                if (startDateTextBox.val() != '') {
                    var testStartDate = startDateTextBox.datepicker('getDate');
                    var testEndDate = endDateTextBox.datepicker('getDate');
                    if (testStartDate > testEndDate)
                        startDateTextBox.datepicker('setDate', testEndDate);
                }
                else {
                    startDateTextBox.val(dateText);
                }
            },
            onSelect: function (selectedDateTime){
                startDateTextBox.datepicker('option', 'maxDate', endDateTextBox.datepicker('getDate') );
            }
        });
    }); 

   
</script>


<h3 align="center">Purchased Office Asset</h3>

<c:url var="printPurchasedOfficeUrl" value="stationaryRequisition/printPurchasedOfficeAsset.pdf"/>

<form:form method="POST" action="${printPurchasedOfficeUrl}?output=pdf">

    <div class="centered"> <table>
            <tr>
                <td>Start Date</td>  
                <td> <input type="text" name="startDate" readonly="true" id="start"/></td>
                <td>End Date</td>  
                <td> <input type="text" name="endDate" readonly="true" id="end" /></td>
            </tr>

            <tr>
                <td><input type="submit" value="Print" /></td>
                <td>
                    <%
                        String m = request.getParameter("mes");
                        if (m != null) {%>
                    <h5 style="color: red;"><%=m%></h5>  
                    <%}

                    %>
                </td>

            </tr>
        </table></div> 

</form:form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
