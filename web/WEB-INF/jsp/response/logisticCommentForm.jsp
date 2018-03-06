<%@include file="/WEB-INF/jsp/header/header.jsp"%>

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

<c:if test="${stationaryRequisition!=null}">
    <h3 align="center" >Stationary Requisition</h3>
    <div class="centered">  <table style="border: 1px solid; width: 70%">
            <thead style="background:darkseagreen">
                <tr>

                    <th>Requester Code</th>
                    <th>Requester Names</th>
                    <th>Requested On</th>
                    <th>Requisition Status</th>

                </tr>
            </thead>  
            <tbody>
                <tr>
                    <td>${stationaryRequisition.employee.employeeCode}</td>
                    <td>${stationaryRequisition.employee.firstName} ${stationaryRequisition.employee.lastName}</td>
                    <td>${stationaryRequisition.requisitionDate}</td>
                    <td>${stationaryRequisition.status}</td>

                </tr>
            </tbody>
        </table></div>
    </c:if>   

<c:if test="${officeAssetRequisition!=null}">
    <h3 align="center" >Office Asset Requisition</h3>
    <div class="centered">  <table style="border: 1px solid; width: 70%">
            <thead style="background:darkseagreen">
                <tr>

                    <th>Requester Code</th>
                    <th>Requester Names</th>
                    <th>Beneficiary names</th>
                    <th>Room number</th>
                    <th>Requested On</th>
                    <th>Requisition Status</th>

                </tr>
            </thead>  
            <tbody>
                <tr>
                    <td>${officeAssetRequisition.employee.employeeCode}</td>
                    <td>${officeAssetRequisition.employee.firstName} ${officeAssetRequisition.employee.lastName}</td>
                    <td>${officeAssetRequisition.beneficiary}</td>
                    <td>${officeAssetRequisition.roomNumber}</td>
                    <td>${officeAssetRequisition.requisitionDate}</td>
                    <td>${officeAssetRequisition.status}</td>

                </tr>
            </tbody>
        </table></div>
    </c:if>   
<br/>

<c:if test="${response.carrequisition.carRequisitionId!=null||response.requisitionResponseId==null}">
    <c:url var="saveUrl" value="/car/carRequisitionResponse/saveOrUpdateRequsitionResponse.htm"/>
</c:if>
<c:if test="${response.stationaryrequisition.stationaryRequisitionId!=null ||response.requisitionResponseId==null}">
    <c:url var="saveUrl" value="/stationaryRequisitionResponse/saveOrUpdateRequsitionResponse.htm"/>
</c:if>
<c:if test="${response.officeassetrequisition.officeAssetRequisitionId!=null||response.requisitionResponseId==null}">
    <c:url var="saveUrl" value="/officeAssetRequisitionResponse/saveOrUpdateRequsitionResponse.htm"/>
</c:if>


<form:form modelAttribute="response" method="POST" action="${saveUrl}" >
    <form:hidden path="stationaryrequisition"/>
    <form:hidden path="carrequisition"/>
    <form:hidden path="officeassetrequisition"/>
    <form:hidden path="requisitionResponseId"/>
    <form:hidden path="employeeByHodEmployeeRegistrationId"/>
    <form:hidden path="hodResponse"/>
    <form:hidden path="hodComment"/>

    <c:if test="${response.hodResponse=='APPROVED'}" >
        <form:hidden path="hodResponseDate"/>
    </c:if>   

    <form:hidden path="employeeByDafEmployeeRegistrationId"/>
    <form:hidden path="dafResponse"/>
    <form:hidden path="dafComment"/>
    <form:hidden path="dafResponseDate"/>

    <br/>
    <script>
            
        function goBack(){
                
            window.history.back();
        }
            
    </script>
    <p align="center"><input type="button" value="Back" onclick="goBack()"</p>
    <h3 align="center" >${response.requisitionResponseId != null ? "Edit " : "New " }Requisition Response Form</h3>
    <br/>
    <div class="centered"> <table>

            <tr>
                <td>Logistic Comment</td>
                <td><form:textarea path="logisticComment" cols="30" rows="1"/></td>

            </tr>
            <tr >
                <td><input type="submit" value=${response.requisitionResponseId  != null ? "Save Changes" : " Save Response" }></td> 

            </tr>

        </table></div>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>