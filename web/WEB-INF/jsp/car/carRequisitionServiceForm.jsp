<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<script type="text/javascript">
    
    $(document).ready(function() {

        $('#exactReturnTime').datetimepicker({
            minDate:-2,maxDate:0,
            timeFormat: "HH:mm"
        });
      
        
    });
 
    function show(){
        var y=document.getElementById("yes");
        if(y.checked){
            document.getElementById("exactTimeOfReturn").style.visibility="visible";
           
        }
        else{
            document.getElementById("exactReturnTime").value="";
            document.getElementById("exactTimeOfReturn").style.visibility="hidden";
           
        }
    }
    
    function selectCar(){
       
        var a= document.getElementById("carSelect")
        var val=a.options[a.selectedIndex].value;
        if(val==""){
            document.getElementById("carText").style.visibility="visible";
           
        }else{
            document.getElementById("carText").value="";
            document.getElementById("carText").style.visibility="hidden";
       
            
        }
        
    }
    
</script>
<c:if test="${carRequisition!=null}">
    <h3 align="center" >Car Requisition</h3>
    <div class="centered">  <table style="border: 1px solid; width: 70%">
            <thead style="background:darkseagreen">
                <tr>
                    <th>Employee Code</th>
                    <th>Reasons</th>
                    <th>Destination</th>
                    <th>Car Type</th>
                    <th>Departure Time</th>
                    <th>Expected Return Time</th>

                </tr>
            </thead>  
            <tbody>
                <tr>
                    <td>${carRequisition.employee.employeeCode}</td>
                    <td>${carRequisition.reason}</td>
                    <td>${carRequisition.destination}</td>
                    <td>${carRequisition.cartype.typeName}</td>
                    <td>${carRequisition.departureTime}</td>
                    <td>${carRequisition.expectedTimeReturn}</td>
                </tr>
            </tbody>
        </table></div>
    </c:if>
<br/>
<h4 align="center">${carRequisitionService.carServiceId != null ? "Edit " : "New " }Service Form</h4>
<br/>
<form:form commandName="carRequisitionService" action="${pageContext.request.contextPath}/car/carRequisitionService/saveOrUpdateCarRequisitionService" method="POST">
    <form:hidden path="carServiceId"/>
    <form:hidden path="carrequisition" />
    <c:if test="${carRequisitionService.carServiceId!=null}">
        <form:hidden path="servedOn" />
        <form:hidden path="serviceNumber"/>
    </c:if>
    <div class="centered"> <table>
            <tr>
                <td>Car Number Plate</td>
                <c:if test="${carRequisitionService.carServiceId==null}">
                    <c:if test="${!empty carList}">
                        <td>  <form:select path="carregistration" onchange="selectCar()" id="carSelect" >
                                <c:if test="${carRequisitionService.carServiceId ==null}">
                                    <form:option  value="" label="--Select--"/> 
                                </c:if>
                                <form:options items="${carList}" itemLabel="plateNo" itemValue="carRegistrationId"/>
                            </form:select></td>
                        </c:if>
                    <td><form:input path="numberPlate" id="carText"/></td>
                    <form:errors path="numberPlate" cssClass="error-validator"/>
                </c:if>
                <c:if test="${carRequisitionService.carServiceId!=null}">
                    <c:if test="${!empty carList}">
                        <td>  <form:select path="carregistration" onchange="selectCar()" id="carSelect" >

                                <form:option  value="" label="--Select--"/> 

                                <form:options items="${carList}" itemLabel="plateNo" itemValue="carRegistrationId"/>
                            </form:select></td>
                        </c:if>
                    <td>
                        <form:input path="numberPlate" id="carText"/>
                        <form:errors path="numberPlate" cssClass="error-validator"/>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>Driver</td>
                <c:if test="${!empty driverList}">
                    <td>  <form:select path="employee">
                            <c:if test="${carRequisitionService.carServiceId ==null}">
                                <form:option  value="" label="--Select--"/> 
                            </c:if>
                            <form:options items="${driverList}" itemLabel="employeeCode" itemValue="employeeRegistrationId" />
                        </form:select></td>
                    </c:if>
                <td>
                    <form:input path="driverNames"/>
                    <form:errors path="driverNames" cssClass="error-validator"/>
                </td>

            </tr>
            <tr>
                <td>Note</td>
                <td><form:textarea path="note" rows="1" cols="30" /></td>
            </tr>
            <tr>
                <td>Contractor Company</td>
                <td>  <form:select path="vendor">
                        <c:if test="${carRequisitionService.carServiceId ==null}">
                            <form:option  value="" label="--Select--"/> 
                        </c:if>
                        <form:options items="${vendorList}" itemLabel="vendorName" itemValue="vendorRegistrationId" />
                    </form:select>
                    <form:errors path="vendor" cssClass="error-validator"/>
                </td> 
            </tr>
            <c:if test="${carRequisitionService.carServiceId !=null}">
                <tr>
                    <td>Returned ?</td>
                    <td> Yes<input type="radio" name="exactTime" id="yes" value="returned" onclick="show()" ></input> 
                        No<input type="radio" name="exactTime" id=no" value="not" onclick="show()" ></input> 
                    </td>
                </tr>
                <tr id="exactTimeOfReturn">
                    <td> Exact time of return</td>
                    <td><form:input path="exactTimeOfReturn" id="exactReturnTime" readonly="true" /></td>

                </tr>
            </c:if>   
            <tr colspan="2">
                <td><input type="submit" style="width: 100px" value="${carRequisitionService.carServiceId != null ? "Save Changes" : "Save" }"/></td> 
            </tr>
        </table>  </div>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
