
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="editUrl" value="/car/carRequisition/edit"/>
<c:url var="deleteUrl" value="/car/carRequisition/delete"/>
<c:url var="searchResponseUrl" value="/car/carRequisition/searchResponse"/>
<c:url var="formUrl" value="/car/carRequisition/form"/>
<c:url var="viewIteneraryUrl" value="/car/carRequisition/iteneraryByCarRequisition"/>
<c:url var="searchUrl" value="/car/carRequisition/searchMemo"/>
<c:url var="serviceUrl" value="/car/carRequisitionService/list"/>

<script>
    $(function () {    
      
        $('.itenerary-link').each(function() {
            // event.preventDefault();
           
            var $link = $(this);
            var $dialog = $('<div></div>')
            .load($link.attr('href'))
            .dialog({
                autoOpen: false,
                modal:true,
                title: $link.attr('title'),
                width: 300,
                height: 300
            });

            $link.click(function() {
                $dialog.dialog('open');

                return false;
            });
        });
    });

</script>
<p align="center">  <a href="${formUrl}">New Car Requisition</a></p>
<form:form modelAttribute="carRequisition" method="GET">
    <c:if test="${!empty carRequisitionList}">
        <h3 align="center">Vehicle Requisitions List</h3>
        <table class="datatable" >

            <thead>
                <tr>

                    <th>Employee Name</th>  
                    <th>Reason</th> 
                    <th>Destination</th> 
                    <th>Departure Time</th> 
                    <th>Expected Time Return</th> 
                    <th>Means of Transport</th>
                    <th>Requested On</th> 
                    <th>Status</th> 
                    <th>Itener.</th>
                    <th>Memo</th>
                    <th>Resp.</th>
                    <th>Serv.</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach items="${carRequisitionList}" var="carRequisition">
                    <tr>
                        <td>${carRequisition.employee.firstName}&nbsp;${carRequisition.employee.lastName}</td>
                        <td>${carRequisition.reason}</td>
                        <td>${carRequisition.destination}</td>
                        <td>${carRequisition.departureTime}</td>
                        <td>${carRequisition.expectedTimeReturn}</td>
                        <td>${carRequisition.cartype.typeName}</td>
                        <td>${carRequisition.requestedOn}</td>
                        <td>${carRequisition.carRequisitionStatus}</td>
                        <c:if test="${carRequisition.carRequisitionStatus =='SENT BY HOD'||carRequisition.carRequisitionStatus =='SENT BY PROFESSIONAL'}">
                            <td><a class="itenerary-link" title="Iteneraries" href="${viewIteneraryUrl}?carRequisitionId=${carRequisition.carRequisitionId}"> View </a></td>
                            <td><a title="View Memo" href="${searchUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="Edit" href="${editUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${editImgUrl}"></img></a></td>
                            <td><a title="Delete" href="${deleteUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${deleteImgUrl}"></img></a></td>
                                </c:if>

                        <c:if test="${carRequisition.carRequisitionStatus=='APPROVED BY HOD'||carRequisition.carRequisitionStatus=='REJECTED BY HOD'||carRequisition.carRequisitionStatus=='APPROVED BY DAF'||carRequisition.carRequisitionStatus=='REJECTED BY DAF'||carRequisition.carRequisitionStatus=='LOGISTICS COMMENTED'}">                    
                            <td><a class="itenerary-link" title="Iteneraries" href="${viewIteneraryUrl}?carRequisitionId=${carRequisition.carRequisitionId}"> View </a></td>
                            <td><a title="View Memo" href="${searchUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Response" href="${searchResponseUrl}?requisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td></td>
                        </c:if>
                        <c:if test="${carRequisition.carRequisitionStatus=='SERVED'}">
                            <td><a class="itenerary-link" title="Iteneraries" href="${viewIteneraryUrl}?carRequisitionId=${carRequisition.carRequisitionId}"> View </a></td>
                            <td><a title="View Memo" href="${searchUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Response" href="${searchResponseUrl}?requisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                            <td><a title="View Service" href="${serviceUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>

                        </c:if>

                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty carRequisitionList}">
        <p align="center">  No Related requisition(s) found</p>
    </c:if>
</form:form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>