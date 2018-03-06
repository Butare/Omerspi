
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<c:url var="viewImgUrl" value="/images/view_memo.png"/>
<c:url var="commentImgUrl" value="/images/comment.png"/>

<c:url var="serveUrl" value="/car/carRequisitionService.htm"/>
<c:url var="hodResponseUrl" value="/car/carRequisitionResponse/hodResponseForm.htm"/>
<c:url var="dafResponseUrl" value="/car/carRequisitionResponse/dafResponseForm.htm"/>
<c:url var="dafSearchResponseUrl" value="/car/carRequisition/searchResponse"/>
<c:url var="logisticCommentUrl" value="/car/carRequisitionResponse/logisticCommentForm.htm"/>
<c:url var="viewIteneraryUrl" value="/car/carRequisition/iteneraryByCarRequisition"/>
<c:url var="searchUrl" value="/car/carRequisition/searchMemo"/>
<c:url var="serviceUrl" value="/car/carRequisitionService/list"/>

<script>
    $(function () {    
      
        $('.itenerery-link').each(function() {
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

<c:if test="${!empty carRequisitionListSuper}">
    <form:form modelAttribute="carRequisition" method="GET">

        <h3 align="center">Vehicle Requisitions List</h3><br/>
        <p align="center"><a href="${pageContext.request.contextPath}/car/carRequisition/carRequisitionCsv.html">Csv File</a></p>

        <div class="centered">  <table class="datatable">

                <thead>
                    <tr>
                        <th>Employee Name</th>  
                        <th>Reason</th> 
                        <th>Destination</th> 
                        <th>Departure Time</th> 
                        <th>Return Time</th> 
                        <th>Car Type</th>
                        <th>Baggage Weight</th>
                        <th>Amount</th>
                        <th>Requested On</th> 
                        <th>Status</th> 
                        <th>Itenerary</th>
                        <th>Memo</th>
                        <th>Respond</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${carRequisitionListSuper}" var="carRequisition">
                        <tr>

                            <td>${carRequisition.employee.firstName}&nbsp;${carRequisition.employee.lastName}</td>
                            <td>${carRequisition.reason}</td>
                            <td>${carRequisition.destination}</td>
                            <td>${carRequisition.departureTime}</td>
                            <td>${carRequisition.expectedTimeReturn}</td>
                            <td>${carRequisition.cartype.typeName}</td>
                            <td>${carRequisition.baggageWeight}</td>
                            <td>${carRequisition.totalAmount}</td>
                            <td>${carRequisition.requestedOn}</td>
                            <td>${carRequisition.carRequisitionStatus}</td>

                            <c:if test="${carRequisition.carRequisitionStatus=='SENT BY HOD'||carRequisition.carRequisitionStatus=='APPROVED BY HOD' ||carRequisition.carRequisitionStatus=='REJECTED BY HOD'||carRequisition.carRequisitionStatus=='LOGISTICS COMMENTED' }">

                                <td><a class="itenerery-link" title="Iteneraries" href="${viewIteneraryUrl}?carRequisitionId=${carRequisition.carRequisitionId}"> View </a></td>
                                <td><a title="View Memo" href="${searchUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td><a title="View Response" href="${dafSearchResponseUrl}?requisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>  
                                <td></td>
                            </c:if>

                            <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
                                <c:if test="${carRequisition.carRequisitionStatus=='SERVED'}">
                                    <td>  <a class="itenerery-link" title="Iteneraries" href="${viewIteneraryUrl}?carRequisitionId=${carRequisition.carRequisitionId}"> View </a></td>
                                    <td><a title="View Memo" href="${searchUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                    <td><a title="View Service" href="${serviceUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                    <td></td>
                                </c:if>
                            </omerspi:require>



                            <c:if test="${carRequisition.carRequisitionStatus=='SENT BY PROFESSIONAL'}">
                                <td>  <a class="itenerery-link" title="Iteneraries" href="${viewIteneraryUrl}?carRequisitionId=${carRequisition.carRequisitionId}"> View </a></td>
                                <td><a title="View Memo" href="${searchUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td>  <a title="Click to Respond" href="${hodResponseUrl}?carRequisitionId=${carRequisition.carRequisitionId}"><img src="${viewImgUrl}"></img></a></td>
                                <td></td>               
                            </c:if>


                        </tr>

                    </c:forEach>
                </tbody>
            </table></div> 

    </form:form>

</c:if>

</div>
<c:if test="${empty carRequisitionListSuper}">
    <p align="center"> No Related requisition Found.</p>
</c:if>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>