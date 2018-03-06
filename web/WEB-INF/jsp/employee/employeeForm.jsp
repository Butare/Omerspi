
<%@include file="/WEB-INF/jsp/header/header.jsp"%>
<script type="text/javascript"> 
    $(document).ready(function(){
        $("#phone,#nationalId,#licence").keydown(function (e) {
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
        //        $("#department").chosen();
    });

    function show(){
        var y=document.getElementById("yes");
        if(y.checked){
            document.getElementById("hideableLicence").style.visibility="visible";
            document.getElementById("hideableWord").style.visibility="hidden";
        }
        else{
            document.getElementById("hideableWord").style.visibility="visible";
            document.getElementById("hideableLicence").style.visibility="hidden";
           
        }
    }


</script>
<form:form modelAttribute="employee" method="POST" action="form" >
    <form:hidden path="employeeRegistrationId"/>     
    <h3 align="center">${employee.employeeRegistrationId != null ? "Edit" : "New" } Employee Form</h3>
    <div class="centered">
        <table>

            <tr>
                <td>Employee Code</td>
                <td><form:input path="employeeCode"/></td>
                <td><form:errors path="employeeCode"  cssClass="error-validator" /></td>
            </tr>
            <tr>
                <td>First Name</td>
                <td><form:input path="firstName"/></td>
                <td><form:errors path="firstName" cssClass="error-validator"/></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><form:input path="lastName"/></td>
                <td><form:errors path="lastName" cssClass="error-validator" /></td>
            </tr>
            <tr>
                <td>Gender</td>
                <td>

                    <form:select path="gender" id="gender" cssStyle="width:165px">
                        <c:if test="${employee.employeeRegistrationId ==null}">
                            <form:option value="" label="--Select--"/>   
                        </c:if>

                        <form:options items="${sexList}"/>
                    </form:select>


                </td>
                <td><form:errors path="gender" cssClass="error-validator" /></td>

            </tr>
            <tr>
                <td>Department</td>
                <td>

                    <form:select path="department" id="department" cssStyle="width:165px">
                        <c:if test="${employee.employeeRegistrationId ==null}">
                            <form:option  value="" label="--Select--"/> 
                        </c:if>
                        <form:options items="${departmentList}" itemLabel="departmentName" itemValue="departmentRegistrationId" />
                    </form:select>

                </td>
                <td><form:errors path="department" cssClass="error-validator" /></td>
            </tr>
            <tr>
                <td>Job Title</td>
                <td><form:input path="jobPosition"/></td>
                <td><form:errors path="jobPosition" cssClass="error-validator" /></td>
            </tr>
            <tr>
                <td>National ID</td>
                <td><form:input path="nationalId" id="nationalId"/></td>
                <td><form:errors path="nationalId" cssClass="error-validator" /></td>
            </tr>
            <tr>
                <td>Telephone</td>
                <td><form:input path="telephone" id="phone" /></td>
                <td><form:errors path="telephone" cssClass="error-validator" /></td>

            </tr>
            <tr>
                <td>Work Email</td>
                <td><form:input path="workEmail"/></td>
                <td><form:errors path="workEmail" cssClass="error-validator"/></td>
            </tr>

            <tr><td>Driver</td>
                <td><div class="radio">
                   
                    <form:radiobutton path="driver" value="true" id="yes" onclick="show()" />  <label for="true">Yes</label>  
                    
                    
                    <form:radiobutton path="driver" value="false" id="no" onclick="show()" />
                    <label for="false">No</label>
                    </div>
                </td>
                <td><form:errors path="driver" cssClass="error-validator"/> </td>
            </tr> 

            <tr id="hideableWord">
                <td>Identification Word</td>
                <td><form:input path="identificationWord" id="word" /></td>
                <td><form:errors path="identificationWord" cssClass="error-validator" /></td>
            </tr>

            <tr id="hideableLicence">
                <td>Licence Number</td>
                <td><form:input path="licenceNumber" id="licence" /></td>
                <td><form:errors path="licenceNumber" cssClass="error-validator"/></td>
            </tr>

            <tr>
                <td>State</td>
                <td><form:input path="state" id="state" /></td>
                <td><form:errors path="state" cssClass="error-validator" /></td>
            </tr>
            <tr >
                <td><input type="submit" style="width: 100px" value=${employee.employeeRegistrationId != null ? "Save Changes" : " Save Employee" }></td> 

            </tr>

        </table></div>
</form:form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>