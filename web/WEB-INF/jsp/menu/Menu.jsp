<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<script>
  
    $(function(){
        $('.notification-dashboard-panel-first,.notification-dashboard-panel-second').each(function(){
            var count=parseInt($(this).text());
            if(count>0){
                $(this).addClass('notification-dashboard-active');
            }
            else{
                $(this).addClass('notification-dashboard-innactive');
            }
        });
    });
    
</script>

<center>

    <omerspi:require privilege="EDIT REQUISITION">
        <div id="user-login-form-dashboard">  

            <div class="panel-col-first-dashboard">
                <div >
                    <p align="justify">
                        <font color="black" face="times" size="3">
                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            <br/>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            Car Pending HoD :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/hodPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodPendingCarRequitionNumber}</a>         
                        </omerspi:require>
                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            Incoming Car Requisition:
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/notServed/ByDepartment/list">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${departmentPendingCaRequisitionNumber}</a>
                        </omerspi:require>
                        <br/><br/>
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            Car Rejected HoD:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/hodRejectedList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodRejectedCarRequisitionNumber}</a> 
                        </omerspi:require>
                        </font>
                    </p>

                </div>

            </div>
            <div class="panel-col-dashboard">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  
                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            <br/>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            Stationary Pending HoD :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/hodPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodPendingStationaryRequisitionNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            Incoming Stationary Requisition:
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/ByDepartment/list">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${departmentPendingStationaryRequisitionNumber}</a>
                        </omerspi:require>
                        <br/><br/>
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            Stationary Rejected HoD:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/hodRejectedList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodRejectedStationaryRequisitionNumber}</a>
                        </omerspi:require>

                        </font>
                    </p>
                </div>              

            </div>

            <div class="panel-col-third-dashboard">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            Office Asset Pending HoD :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/hodPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodPendingOfficeAssetRequisitionNumber}</a>
                            <br/><br/>
                            Office Asset Rejected HoD:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/hodRejectedList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodRejectedOfficeAssetRequisitionNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL">
                            <br/>
                            Incoming Office Asset Requisition:
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/ByDepartment/list">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${departmentPendingOfficeAssetRequisitionNumber}</a>
                        </omerspi:require>
                        </font>
                    </p>
                </div>              

            </div>

        </div>

        <div id="user-login-form-dashboard">  

            <div class="panel-col-first-dashboard-daf">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3">
                        Office Asset Pending DAF :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/officeAsset/profDafPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${profDafPendingOfficeAssetRequisitionNumber}</a> 
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp; <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/officeAsset/hodDafPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodDafPendingOfficeAssetRequisitionNumber}</a> 
                        </omerspi:require>
                        <br/><br/>
                        Office Asset Rejected DAF:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/officeAsset/dafRejectedList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${DafRejectedOfficeAssetRequisitionNumber}</a> 
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/officeAsset/dafRejectedList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${DafRejectedOfficeAssetRequisitionNumber}</a> 
                        </omerspi:require>
                        </font>
                    </p>

                </div>

            </div>
            <div class="panel-col-dashboard">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  

                        Stationery Pending DAF:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/profDafPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${profDafPendingStationaryRequisitionNumber}</a> 
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/hodDafPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hodDafPendingStationaryRequisitionNumber}</a> 
                        </omerspi:require>    
                        <br/><br/>
                        Stationery Rejected DAF:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/dafRejectedList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dafRejectedStationaryRequisitionNumber}</a> 
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/dafRejectedList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dafRejectedStationaryRequisitionNumber}</a> 
                        </omerspi:require>
                        </font>
                    </p>
                </div>              

            </div>

        </div>

        <div id="user-login-form-dashboard">  

            <div class="panel-col-first-dashboard-logistics">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3">
                        Car Pending Logistics:
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/logisticsPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allLogisticsPendingCarRequisitionNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp; <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/logisticsPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allLogisticsPendingCarRequisitionNumber}</a>
                        </omerspi:require>
                        <br/><br/>
                        Car Pending Served  &nbsp;:&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/servedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allServedCarRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/servedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allServedCarRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <br/><br/>
                        Car Pending Commtd:
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/commentedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allCommentedCarRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/commentedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allCommentedCarRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        </font>
                    </p>

                </div>
            </div>
            <div class="panel-col-dashboard-logistics">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  
                        Stationary Pending Logistics:
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/logisticsPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allLogisticsPendingStationaryRequisitionNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp; <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/logisticsPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allLogisticsPendingStationaryRequisitionNumber}</a>
                        </omerspi:require>
                        <br/><br/>
                        Stationary Pending Served :&nbsp;&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/servedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allServedStationaryRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/servedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allServedStationaryRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <br/><br/>
                        Stationary Pending Commtd:&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/commentedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allCommentedStationaryRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/commentedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allCommentedStationaryRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        </font>
                    </p>
                </div>              

            </div>
            <div class="panel-col-third-dashboard-logistics">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  
                        Office asset Pending Logistics:
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/logisticsPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allLogisticsPendingOfficeAssetRequisitionNumber}</a>
                        </omerspi:require>                      
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/logisticsPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allLogisticsPendingOfficeAssetRequisitionNumber}</a>
                        </omerspi:require>                           
                        <br/><br/>
                        Office asset Pending Served :&nbsp;&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/servedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allServedOfficeAssetRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/servedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allServedOfficeAssetRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <br/><br/>
                        Office asset Pending Commtd:&nbsp;
                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/commentedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allCommentedOfficeAssetRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/commentedListByEmployee.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allCommentedOfficeAssetRequisitionByEmployeeNumber}</a>
                        </omerspi:require>
                        </font>
                    </p>
                </div>              

            </div>

        </div>
    </omerspi:require>

    <!-- DAF NOTIFICATIONS   -->

    <omerspi:require privilege="RESPOND REQUISITION FROM HOD">

        <div id="user-login-form-dashboard">  

            <div class="panel-col-first-dashboard-daf">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3">
                        <br/>
                        Incoming Office Asset Requisition :
                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/officeAsset/allDafPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allDafPendingOfficeAssetRequisitionNumber}</a>
                        </omerspi:require>
                        </font>
                    </p>

                </div>

            </div>
            <div class="panel-col-dashboard">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  
                        <br/>
                        Incoming Stationery Requisition:
                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD">
                            <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/allDafPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allDafPendingStationaryRequisitionNumber}</a>
                        </omerspi:require>
                        </font>
                    </p>
                </div>              

            </div>

        </div>


    </omerspi:require>

    <!-- LOGISTICS NOTIFICATIONS   -->

    <omerspi:require privilege="RESPOND REQUISITION FROM DAF">

        <div id="user-login-form-dashboard">  


            <div class="panel-col-first-dashboard">               
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3">
                        <br/>
                        Incoming Car Requisition :&nbsp;&nbsp;
                        <a class="notification-dashboard-panel-first" href="${pageContext.request.contextPath}/car/carRequisition/allDafPendingList.dash">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allDafPendingCarRequisitionNumber}</a>

                        </font>
                    </p>

                </div>

            </div>
            <div class="panel-col-dashboard">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  
                        <br/>
                        Incoming Stationery Requisition:

                        <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/stationaryRequisition/Accepted/DAF/list">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allDafAcceptedStationaryRequisitionNumber}</a>
                        </font>
                    </p>
                </div>              

            </div>
            <!--                        -->
            <div class="panel-col-third-dashboard">
                <div class="inside">
                    <p align="justify">
                        <font color="black" face="times" size="3" >  
                        <br/>
                        Incoming Office Asset Requisition:

                        <a class="notification-dashboard-panel-second" href="${pageContext.request.contextPath}/officeAsset/Accepted/DAF/list">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${allDafAcceptedOfficeAssetRequisitionNumber}</a>
                        </font>
                    </p>
                </div>              

            </div>

        </div>


    </omerspi:require>

</center>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>
