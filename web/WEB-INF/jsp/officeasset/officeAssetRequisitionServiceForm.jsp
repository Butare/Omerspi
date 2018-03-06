
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
<c:url var="assetDetailUrl" value="/officeAssetService/addAssetDetail"/>
<c:url var="dafAcceptedUrl" value="/officeAsset/Accepted/DAF/list"/>
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
<br/>

<form:form modelAttribute="officeAssetRequestedItems" method="POST" action="addAssetDetail">

    <h3 align="center">Office Asset Service Form</h3><br/>

    <div class="centered">   <table style="border: 1px solid; width: 70%">

            <thead style="background:#1c94c4">
                <tr>
                    <th>Requisition ID</th>
                    <th>Item Name</th>  
                    <th>Requested Quantity</th>
                    <th>Served Qty</th>
                    <th>Detail</th>
                </tr>
            </thead>
            <tbody style="background:yellowgreen">
                <c:forEach items="${requestedItems}" var="item" varStatus="status" >             
                    <tr>              
                        <td><c:out value="${item.officeassetrequisition.officeAssetRequisitionId}" /> </td>
                        <td><c:out value="${item.items.itemName}"/></td>
                        <td><c:out value="${item.requestedQty}"  /></td>
                        <td><c:out value="${servedQuantities[item]}"/></td>
                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                            <td><a class="btn" href="addAssetDetail?requestedId=${item.requestedId}">Details</a></td>
                        </omerspi:require>
                        <omerspi:require privilege="EDIT REQUISITION">
                            <td><a class="btn" href="addAssetDetail?requestedId=${item.requestedId}">View Details</a></td>
                        </omerspi:require>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>  <br/>
    <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
        <p align="center"><a class="btn" href="${dafAcceptedUrl}">Back</a></p>
    </omerspi:require>
    <omerspi:require privilege="EDIT REQUISITION">
        <p align="center"><input type="button" value="Back" onclick="goBack()"/></p>
        </omerspi:require>
    </form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp"%>
