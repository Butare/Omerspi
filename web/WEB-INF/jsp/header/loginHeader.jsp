<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/header/includes.jsp" %>
<%--<%@page import="jim.omerspi.Constants" %>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OMERSPI</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/omerspi-favicon.ico" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/omerspi.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/reset.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/topnav.css" />


        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.9.1.min.js"></script>

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

                </div>

            </div>
            <div style="margin-left:0px; margin-right:0px;background:#226CA8; height:35px; margin-bottom:0px;">
                <div class="hmenu" style="width:900px; margin:auto;">
                    <ul class="sf-menu" style="">
                        <li class="leaf first">
                            <a href="${pageContext.request.contextPath}/Menu" title>home</a>
                        </li>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/jsp/header/headerMessage.jsp" %>
        <hr />
