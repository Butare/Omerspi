<%@include file="/WEB-INF/jsp/header/header.jsp"%>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/notFoundItemRequisition/edit"/>
<c:url var="deleteUrl" value="/notFoundItemRequisition/delete"/>
<c:url var="logisticCommentUrl" value="/stationaryRequisitionResponse/logisticCommentForm.htm"/>

<script>
    function goBack(){
        window.history.back();
    }
        
</script>


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
    <c:if test="${requestedItem!=null}">
    <h3 align="center" >Requested Item</h3>
    <div class="centered">  <table style="border: 1px solid; width: 70%">
            <thead style="background:darkseagreen">
                <tr>
                    <th>Item Name</th>
                    <th>Requested Qty</th>     
                </tr>
            </thead>  
            <tbody>
                <tr>
                    <td>${requestedItem.items.itemName}</td>          
                    <td>${requestedItem.requestedQty}</td>
                </tr>
            </tbody>
        </table></div> 

</c:if>
<br/>
<omerspi:require privilege="RESPOND REQUISITION FROM DAF">
    <p align="center"><input type="button" value="Back" onclick="goBack()"/></p>
    </omerspi:require>
    <form:form modelAttribute="officeAssetDetails" method="POST" action="saveOrUpdateServedOfficeAssetDetails">

    <h3 align="center">Office Asset Details</h3><br/>

    <div class="centered">   <table style="border: 1px solid; width: 70%">

            <thead style="background:blanchedalmond">
                <tr>
                    <th style="text-align:justify">Serial number</th>
                    <th>Asset code</th>  
                    <th>Observation</th>
                </tr>
            </thead>
            <tbody style="background:fixed">             

                <c:forEach items="${serviceForm.requisitionService}" var="service" varStatus="status" >
                    <tr>
                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <td><input type="text" name="requisitionService[${status.index}].serialNumber" value="${service.serialNumber}" /> </td>
                            <td><input type="text" name="requisitionService[${status.index}].officeAssetCode" value="${service.officeAssetCode}" /> </td>
                            <td><input type="text" name="requisitionService[${status.index}].observation" value="${service.officeAssetCode}"/> </td>
                            </omerspi:require>  
                            <omerspi:require privilege="EDIT REQUISITION">     
                            <td><input type="text" name="requisitionService[${status.index}].serialNumber" value="${service.serialNumber}" readonly="true" /> </td>
                            <td><input type="text" name="requisitionService[${status.index}].officeAssetCode" value="${service.officeAssetCode}" readonly="true" /> </td>
                            <td><input type="text" name="requisitionService[${status.index}].observation" value="${service.officeAssetCode}" readonly="true"/> </td>
                            </omerspi:require>
                    </tr>

                </c:forEach>
            </tbody>
        </table></div><br/>
        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
        <p align="center"><input type="submit" value="Save Details"/></p>
        </omerspi:require>
        <omerspi:require privilege="EDIT REQUISITION">
        <p align="center"><input type="button" value="Back" onclick="goBack()"/></p>
        </omerspi:require>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>
