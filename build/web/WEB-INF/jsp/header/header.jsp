<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/header/includes.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OMERSPI</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/omerspi-favicon.ico" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/omerspi.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/reset.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/topnav.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jquery-ui-1.10.3.custom.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jquery.dataTables.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jquery-ui-timepicker-addon.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/bootstrap.css"/>


        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui-1.10.3.custom.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.dataTables.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/chosen.jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui-timepicker-addon.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui-sliderAccess.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.js"></script>

        <script type="text/javascript">
            $(document).ready(function(){
                $(".datatable").dataTable({
                    "aLengthMenu": [[5, 10,20, 50, -1], [5, 10,20, 50, "All"]]
                });
            });
        </script>
    </head>
    <body body style="background:#fafafa;">
        <!--------top section starts----------------------->
        <div id="top_section">
            <div id="top-bana-wrapa" class="" style="background:#fff; height:90px; padding-top:20px; padding-bottom:10px;">
                <div id="bana"class="" style="width:1000px; margin:auto;">
                    <div class="">
                        <div style="float:left;"><img src="${pageContext.request.contextPath}/images/logo.png" height="90" width="100"></div>
                        <div style="float:right;"><img src="${pageContext.request.contextPath}/images/CoatOfArms.png" height="90" width="90"></div>
                    </div>

                    <br/> <br/>  
                    <h5 align="center"> Welcome ${ sessionScope["omerspi-user"] } || <a href="${pageContext.request.contextPath}/logout">Logout</a> </h5> <br/>

                </div>
            </div>
            <div style="margin-left:0px; margin-right:0px;background:#226CA8; height:35px; margin-bottom:0px;">
                <div class="hmenu" style="width:900px; margin:auto;">

                    <ul class="sf-menu" style="">
                        <li class="leaf first">
                            <a href="${pageContext.request.contextPath}/Menu" title>home</a>
                        </li>
                        <omerspi:require privilege="ADD REQUISITION HOD">
                            <li class="">
                                <a href="#">Make Requisition</a>
                                <ul class="dropdown-menu">

                                    <li><a href="${pageContext.request.contextPath}/car/carRequisition/form">Car Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/addItemForm">Stationary Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/officeAsset/addOfficecAssetForm">Office Asset Requisition</a></li>
                                </ul>
                            </li>
                        </omerspi:require>

                        <omerspi:require privilege="ADD REQUISITION PROFESSIONAL">
                            <li class="">
                                <a href="#">Make Requisition</a>
                                <ul class="dropdown-menu">


                                    <li><a href="${pageContext.request.contextPath}/car/carRequisition/form">Car Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/addItemForm">Stationary Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/officeAsset/addOfficecAssetForm">Office Asset Requisition</a></li>
                                </ul>
                            </li>
                        </omerspi:require>

                        <omerspi:require privilege="EDIT REQUISITION">
                            <li class="">
                                <a href="#">My Requisition</a>
                                <ul class="dropdown-menu">

                                    <li><a href="${pageContext.request.contextPath}/car/carRequisition/list">Car Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/stationaryRequisition/byEmployee">Stationary Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/officeAsset/byEmployee">Office Asset Requisition</a></li>
                                </ul>
                            </li>
                        </omerspi:require>
                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL" >
                            <li class="">
                                <a href="#">Respond Requisition</a>
                                <ul class="dropdown-menu">


                                    <li><a href="${pageContext.request.contextPath}/car/carRequisition/notServed/ByDepartment/list">Car Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/stationaryRequisition/ByDepartment/list">Stationary Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/officeAsset/ByDepartment/list">Office Asset Requisition</a></li>
                                </ul>
                            </li>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM PROFESSIONAL" >
                            <li class="">
                                <a href="#">Responded Requisition</a>
                                <ul class="dropdown-menu">
                                    <li class="">
                                        <a href="#">Car Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/Accepted/HOD/list">ACCEPTED</a></li>
                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/Rejected/HOD/list">REJECTED</a></li>
                                        </ul>

                                    </li>

                                    <li class="">
                                        <a href="#">Stationary Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/Accepted/HOD/list">ACCEPTED </a></li>
                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/Rejected/HOD/list">REJECTED</a></li>
                                        </ul>

                                    </li>

                                    <li class="">
                                        <a href="#">Office Asset Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/officeAsset/Accepted/HOD/list">ACCEPTED</a></li>
                                            <li><a href="${pageContext.request.contextPath}/officeAsset/Rejected/HOD/list">REJECTED</a></li>
                                        </ul>

                                    </li>

                                </ul>
                            </li>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD" >
                            <li class="">
                                <a href="#">Respond Requisition</a>
                                <ul class="dropdown-menu">


                                    <li class="">
                                        <a href="#">Stationary Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/accepted/AllDepartments/list">ACCEPTED BY HOD</a></li>
                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/requests/HoD/list">REQUESTED BY HOD</a></li>
                                        </ul>

                                    </li>

                                    <li class="">
                                        <a href="#">Office Asset Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/officeAsset/accepted/AllDepartments/list">ACCEPTED BY HOD</a></li>
                                            <li><a href="${pageContext.request.contextPath}/officeAsset/requests/HoD/list">REQUESTED BY HOD</a></li>
                                        </ul>

                                    </li>

                                </ul>
                            </li>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM HOD" >
                            <li class="">
                                <a href="#">Requisition Response</a>
                                <ul class="dropdown-menu">
                                    <li class="">
                                        <a href="#">Car Requisition</a>
                                        <ul class="dropdown-menu">
                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/Accepted/DAF/list">ACCEPTED</a></li>
                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/Rejected/DAF/list">REJECTED</a></li>
                                        </ul>

                                    </li>

                                    <li class="">
                                        <a href="#">Stationary Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/Accepted/DAF/list">ACCEPTED </a></li>
                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/Rejected/DAF/list">REJECTED</a></li>
                                        </ul>

                                    </li>

                                    <li class="">
                                        <a href="#">Office Asset Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/officeAsset/Accepted/DAF/list">ACCEPTED</a></li>
                                            <li><a href="${pageContext.request.contextPath}/officeAsset/Rejected/DAF/list">REJECTED</a></li>
                                        </ul>

                                    </li>

                                </ul>
                            </li>
                        </omerspi:require>

                        <omerspi:require privilege="RESPOND REQUISITION FROM DAF" >
                            <li class="">
                                <a href="#">Respond Requisition</a>
                                <ul class="dropdown-menu">

                                    <li class="">
                                        <a href="#">Car Requisition</a>
                                        <ul class="dropdown-menu">
                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/accepted/AllDepartments/list">ACCEPTED BY HOD</a></li>
                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/requests/HoD/list">REQUESTED BY HOD</a></li>
                                        </ul>

                                    </li>                                  
                                    <li><a href="${pageContext.request.contextPath}/stationaryRequisition/Accepted/DAF/list">Stationary Requisition</a></li>
                                    <li><a href="${pageContext.request.contextPath}/officeAsset/Accepted/DAF/list">Office Asset Requisition</a></li>
                                </ul>
                            </li>

                            <li class="">
                                <a href="#">Requisition Response</a>
                                <ul class="dropdown-menu">
                                    <li class="">
                                        <a href="#">Car Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/served/list">SERVED</a></li>
                                            <li><a href="${pageContext.request.contextPath}/car/carRequisition/commented/list">COMMENTED</a></li>
                                        </ul>

                                    </li>

                                    <li class="">
                                        <a href="#">Stationary Requisition</a>
                                        <ul class="dropdown-menu">

                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/served/list">SERVED </a></li>
                                            <li><a href="${pageContext.request.contextPath}/stationaryRequisition/commented/list">COMMENTED</a></li>
                                        </ul>

                                    </li>

                                    <li class="">
                                        <a href="#">Office Asset requisition</a>
                                        <ul class="dropdown-menu">
                                            <li><a href="${pageContext.request.contextPath}/officeAsset/served/list">SERVED </a></li>
                                            <li><a href="${pageContext.request.contextPath}/officeAsset/commented/list">COMMENTED</a></li>
                                        </ul>
                                    </li>

                                </ul>
                            </li>

                        </omerspi:require>    


                        <li class=""><omerspi:require privilege="EDIT RECORDS">
                                <a href="#">RECORDS</a>
                            </omerspi:require>
                            <ul class="dropdown-menu">
                                <omerspi:require privilege="VIEW CAR">
                                    <li><a href="${pageContext.request.contextPath}/car/carRegistration/list">Car Registration</a></li>
                                    <li><a href="${pageContext.request.contextPath}/carType/list">Car Type</a></li>
                                </omerspi:require>
                                <omerspi:require privilege="VIEW VENDOR">
                                    <li><a href="${pageContext.request.contextPath}/vendor/list">Contractor Company</a></li>
                                </omerspi:require>
                                <omerspi:require privilege="VIEW DEPARTMENT">  
                                    <li><a href="${pageContext.request.contextPath}/department/list">Department</a></li>
                                </omerspi:require>  
                                <omerspi:require privilege="VIEW EMPLOYEE">
                                    <li><a href="${pageContext.request.contextPath}/employee/list">Employee</a></li>
                                </omerspi:require>

                                <omerspi:require privilege="VIEW CATEGORY">
                                    <li><a href="${pageContext.request.contextPath}/category/list">Item Category</a></li>
                                    <li><a href="${pageContext.request.contextPath}/categoryType/list">Item Category Type</a></li>
                                </omerspi:require>

                                <omerspi:require privilege="VIEW ITEM">
                                    <li><a href="${pageContext.request.contextPath}/item/list">Item Names</a></li>
                                </omerspi:require>

                                <li><a href="${pageContext.request.contextPath}/itenerary/list">Itenerary</a></li>


                                <li><a href="${pageContext.request.contextPath}/privilege/list">Privilege</a></li>

                                <omerspi:require privilege="VIEW STATIONARY">
                                    <li><a href="${pageContext.request.contextPath}/stationary/list">Stock</a></li> 
                                </omerspi:require>

                                <li><a href="${pageContext.request.contextPath}/stationary/summary/stock">Summary</a></li>

                                <omerspi:require privilege="VIEW USER ROLE">
                                    <li><a href="${pageContext.request.contextPath}/user/list">Users</a></li>
                                </omerspi:require>

                            </ul>

                        </li>
                        <omerspi:require privilege="VIEW REPORT">

                            <li class="">
                                <a href="#">REPORT</a>
                                <ul class="dropdown-menu">


                                    <li><a href="${pageContext.request.contextPath}/getTotalByDriverForm.htm">BY Driver</a></li>
                                    <li><a href="${pageContext.request.contextPath}/getStaffByDriverForm.htm">Staff By Driver</a></li>
                                    <li><a href="${pageContext.request.contextPath}/getTotalByDepartment.htm">Total By Department</a></li>
                                    <li><a href="${pageContext.request.contextPath}/getTotalByCompany.htm">Total By Company</a></li>
                                    <li><a href="${pageContext.request.contextPath}/allStockSummary.form">Stock Summary Report</a></li>
                                    <li class="">
                                        <a href="#">Served Items Summary</a>
                                        <ul class="dropdown-menu">
                                            <li><a href="${pageContext.request.contextPath}/servedItem.pdf">Pdf Format</a></li>
                                            <li><a href="${pageContext.request.contextPath}/servedItem.excel">Excel Format</a></li> 
                                        </ul>
                                    </li>

                                    <li class="">
                                        <a href="${pageContext.request.contextPath}/purchasedStationaryItems.form">Purchased Stock</a> 
                                    </li>
                                </ul>
                            </li>

                        </omerspi:require>

                    </ul>

                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/jsp/header/headerMessage.jsp" %>
        <omerspi:require privilege="RESPOND REQUISITION FROM DAF">
            <div class="form-alert"> 
                <a href="${pageContext.request.contextPath}/stationary/outOfStock.htm" > ${ sessionScope["omerspi-alert"] }</a>
            </div>
        </omerspi:require>
        <hr />
