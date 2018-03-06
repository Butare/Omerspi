
<%@include file="/WEB-INF/jsp/header/header.jsp"%>
<form:form modelAttribute="response" method="POST" action="saveOrUpdateRequsitionResponse.htm" >
    <form:hidden path="stationaryrequisition"/>
    <form:hidden path="carrequisition"/>
    <form:hidden path="officeassetrequisition"/>
    <c:if test="${carRequisition!=null}">
        <h3 align="center">Car Requisition</h3>
        <div class="centered">  <table style="border: 3px solid; width: 70%">
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
                        <th>Requester names</th>
                        <th>Beneficiary names</th>
                        <th>Requested On</th>
                        <th>Requisition Status</th>

                    </tr>
                </thead>  
                <tbody>
                    <tr>
                        <td>${officeAssetRequisition.employee.employeeCode}</td>
                        <td>${officeAssetRequisition.employee.firstName} ${officeAssetRequisition.employee.lastName}</td>
                        <td>${officeAssetRequisition.beneficiary}</td>
                        <td>${officeAssetRequisition.requisitionDate}</td>
                        <td>${officeAssetRequisition.status}</td>

                    </tr>
                </tbody>
            </table></div>
        </c:if>
    <br/>
    <h3 align="center">${response.requisitionResponseId != null ? "Edit" : "New " }Requisition Response Form</h3>
    <br/>
    <div class="centered">   <table>

            <tr>
                <td>HoD Response</td>
                <td> <form:select path="hodResponse">
                        <c:if test="${response.requisitionResponseId==null}">
                            <form:option value="" label="--Select--"/>   
                        </c:if>

                        <form:options items="${responseList}"/>
                    </form:select></td>
                <td><form:errors path="hodResponse" cssClass="error" /></td>


            </tr>
            <tr>
                <td>HoD Comment</td>
                <td><form:textarea path="hodComment" cols="30" rows="1"/></td>
                <td><form:errors path="hodComment" cssClass="error" element="div"/></td>
            </tr>

            <tr >
                <td><input type="submit" value=${response.requisitionResponseId  != null ? "Save Changes" : " Respond" }></td> 

            </tr>

        </table></div>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>