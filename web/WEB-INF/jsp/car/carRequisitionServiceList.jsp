
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/car/carRequisitionService/edit"/>
<c:url var="deleteUrl" value="/car/carRequisitionService/delete"/>
<c:url var="searchResponseUrl" value="/car/carRequisitionService/searchResponse"/>
<c:url var="viewIteneraryUrl" value="/car/carRequisition/iteneraryByCarRequisition"/>
<c:url var="searchUrl" value="/car/carRequisition/searchMemo"/>

<form:form modelAttribute="carRequisition" method="GET">

    <script>
        function goBack()
        {
            window.history.back()
        }
    </script>

    <c:if test="${carRequisition!=null}">
        <h3 align="center">Vehicle Requisition</h3>
        <div class="centered">  <table style="border: 1px solid; width: 70%">
                <thead style="background:#d3dce3">
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

    <h3><p align="center"><input type="button" value="Back" onclick="goBack()"/></p></h3>
    <h3 align="center">Vehicle Requisition Service</h3>
    <table class="datatable" >

        <thead>
            <tr>

                <th>Car Requisition ID</th>  
                <th>Plate Number</th> 
                <th>Driver</th> 
                <th>Company</th> 
                <th>Note</th> 
                <th>Served On</th>
                <th>Exact time of return</th>
                <th></th>
                <th></th>

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${carRequisitionServiceList}" var="carRequisitionService">
                <tr>
                    <td>${carRequisitionService.carrequisition.carRequisitionId}</td>
                    <td>
                        <c:if test="${carRequisitionService.carregistration.carRegistrationId!=null}">
                            ${carRequisitionService.carregistration.plateNo}
                        </c:if>       
                        <c:if test="${carRequisitionService.carregistration.carRegistrationId==null}">
                            ${carRequisitionService.numberPlate}
                        </c:if>
                    </td> 

                    <td>
                        <c:if test="${carRequisitionService.employee.employeeRegistrationId!=null}">
                            ${carRequisitionService.employee.firstName} ${carRequisitionService.employee.lastName}
                        </c:if> 
                        <c:if test="${carRequisitionService.employee.employeeRegistrationId==null}">
                            ${carRequisitionService.driverNames}
                        </c:if>

                    </td> 
                    <td>${carRequisitionService.vendor.vendorName}</td>
                    <td>${carRequisitionService.note}</td>
                    <td>${carRequisitionService.servedOn}</td>
                    <td>${carRequisitionService.exactTimeOfReturn} </td>

                    <omerspi:require privilege="RESPOND REQUISITION FROM DAF">

                        <td><a title="Edit" href="${editUrl}?carServiceId=${carRequisitionService.carServiceId}"><img src="${editImgUrl}"></img></a></td>
                        <td><a title="Delete" href="${deleteUrl}?carServiceId=${carRequisitionService.carServiceId}"><img src="${deleteImgUrl}"></img></a></td>

                    </omerspi:require>
                    <omerspi:require privilege="ADD REQUISITION HOD">
                        <td><a title="Form" href="${pageContext.request.contextPath}/printCarRequisitionForm.htm?output=pdf&carServiceId=${carRequisitionService.carServiceId}"/>Print Form</td>
                        <td></td>
                    </omerspi:require>
                    <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                        <td><a title="Form" href="${pageContext.request.contextPath}/printCarRequisitionForm.htm?output=pdf&carServiceId=${carRequisitionService.carServiceId}"/>Print Form</td>
                        <td></td>
                    </omerspi:require>
                </tr>

            </c:forEach>
        </tbody>
    </table>

</form:form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>