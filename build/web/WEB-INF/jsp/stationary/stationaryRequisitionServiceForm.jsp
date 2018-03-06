
<%@include file="/WEB-INF/jsp/header/header.jsp"%>

<script type="text/javascript">

    function compare(){
        var  qty=parseInt($("#requestedQty").val());
        if(parseInt(document.getElementById("servedQty").value)>qty){
            alert("Served Qty can't be greater than Requested Qty ");
            document.getElementById("servedQty").value="";
            document.getElementById("servedQty").focus();
        }
     
    }
 
</script>

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

<br/>

<p align="center"><input type="button" value="Back" onclick="goBack()"/></p>

<form:form modelAttribute="stationaryRequisitionService" method="POST" action="saveOrUpdateServedStationaryRequisition">

    <h3 align="center">Stationary Service Form</h3><br/>

    <div class="centered">   <table style="border: 1px solid; width: 70%">

            <thead style="background:#1c94c4">
                <tr>
                    <th>Requisition ID</th>
                    <th>Item Name</th>  
                    <th>Requested Quantity</th> 
                    <th>Accepted Quantity</th>
                    <th>Observation</th>
                </tr>
            </thead>
            <tbody style="background:#ccc">
                <c:forEach items="${requestedItemList}" var="requestedItem" varStatus="i" >
                    <tr>
                <input type="hidden" name="requestedId" value="${requestedItem.requestedId}"/>
                <input type="hidden" name="stationaryrequisition" value="${requestedItem.stationaryrequisition.stationaryRequisitionId}"/>
                <input type="hidden" name="stationaryregistration" value="${requestedItem.items.itemId}"/>
                <td><c:out value="${requestedItem.stationaryrequisition.stationaryRequisitionId}" /> </td>
                <td><c:out value="${requestedItem.items.itemName}"/> </td>
                <td><c:out value="${requestedItem.requestedQty}"  /></td>
                <input type="hidden" value="${requestedItem.requestedQty}" id="requestedQty-${requestedItem.requestedId}" />
                <td><input name="serviceQty-${requestedItem.requestedId}" id="servedQty" onchange="compare()" /> </td>
                <td><input name="observation-${requestedItem.requestedId}" id="observation"/> </td>
                </tr>

            </c:forEach>
            </tbody>
        </table></div>  
    <p align="center"><input type="submit" value="Serve Requsition"/></p>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>
