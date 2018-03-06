
<%@include file="/WEB-INF/jsp/header/header.jsp"%>


<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/editItem"/>
<c:url var="deleteUrl" value="/notFoundItemRequisition/delete"/>
<c:url var="logisticCommentUrl" value="/stationaryRequisitionResponse/logisticCommentForm.htm"/>

<script>
  function goBack(){
      
      window.history.back()   
  }  
    
</script>

 <c:if test="${officeAssetRequisition!=null}">
         <h3 align="center" >Office Asset Requisition</h3>
        <div class="centered">  <table style="border: 1px solid; width: 70%">
                <thead style="background:darkseagreen">
                    <tr>

                        <th>Requester Code</th>
                        <th>Requester Names</th>
                        <th>Beneficiary Name</th>
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
<h3> <p align="center"><input type="button" value="Back" onclick="goBack()"/></p>  </h3>
<br/>
<form:form method="GET">

    <h3 align="center">Requested Item List</h3><br/>

    <div class="centered">   <table style="border: 1px solid; width: 70%">

            <thead style="background:#1c94c4">
                <tr>
                    <th>Requisition ID</th>
                    <th>Item Name</th>  
                    <th>Requested Quantity</th> 
                    <th>Served Quantity</th>
                    <th>Observation</th>
                </tr>
            </thead>
            <tbody style="background:#ccc">
                <c:forEach items="${requestedItemList}" var="requestedItem">
                    <tr>
                        <td>${requestedItem.officeassetrequisition.officeAssetRequisitionId}</td>
                        <td>${requestedItem.items.itemName}</td>
                        <td>${requestedItem.requestedQty}</td>
                        <c:if test="${requestedItem.officeassetrequisition.status=='SERVED'}" >
                            <td>${requestedItem.servedQty}</td>
                            <td>${requestedItem.observation}</td>
                        </c:if> 
                            
                         <c:if test="${requestedItem.officeassetrequisition.status!='SERVED'}" >
                            <td style="color: red;">---</td>
                            <td style="color: red;">---</td>
                        </c:if> 
                           
                    </tr>

                </c:forEach>
            </tbody>
        </table></div>  

</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>