<%-- 
    Document   : stationaryRequisitionEditQuantity
    Created on : May 23, 2013, 9:40:16 AM
    Author     : JIMMY  
--%>
<%@include file="/WEB-INF/jsp/header/header.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:url var="saveUrl" value="addItem"/>
<h3 align="center">Edit Item Quantity </h3>
<form action="${saveUrl}" method="POST">
    
    <input type="hidden" name="itemId" value="${item.itemId}"/>
    
    <div class="centered"> <table colspan="2">

            <tr>

                <td>Item Name :</td>
                <td>
                    <c:out value="${item.itemName}"/>
                </td>
            </tr> 

            <tr>
                <td>Quantity :</td>
                <td> 
                    <input name="quantity" value="${quantity}"/>
                    Set Quantity to 0 item will be removed from the List
                </td>
                                  
            </tr> 
            <tr><td><input type="submit" value="Add Item"/></td></tr> 
        </table>
    </div>
</form>
<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>