<script>
    history.go(1);

</script>

<%@include file="/WEB-INF/jsp/header/loginHeader.jsp" %>

<body>

    <form action="login.htm" method="POST" autocomplete="off" >
        <br/>

        <center>
            <div id="user-login-form">  

                <div class="panel-col-first">

                    <div class="inside">
                        <p align="center" >
                            <font color="black" size="4">
                            <b>LOGIN </b>
                            </font>
                        </p>
                        <br/>
                        <table style="margin-left: auto; margin-right: auto; border: 0" cellpadding="3" cellspacing="0" >
                            <tr>
                                <td style="text-align: left" title="This field is required." >Username :</td>  


                                <td style="text-align: left" title="This field is required."><input type="text" name="userName" class="login-input"/> </td>
                            </tr>  
                            <tr>
                                <td style="text-align: left" title="This field is required.">Password :</td>
                                <td style="text-align: left"><input type="password" name="password" class="login-input" /></td>
                            </tr>
                            <tr>                  
                                <td style="text-align: left" ><input type="submit" value="Login" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td style="text-align: left"><a href="${pageContext.request.contextPath}/user/form">Create Account</a> </td>

                            </tr>
                            <tr>
                                <td></td>
                                <td style="text-align: left; vertical-align: top"><a style="font-size: 12px" href="${pageContext.request.contextPath}/forgotPassword.form">Forgot password?</a></td>
                            </tr>
                        </table>
                    </div> 
                </div>
                <div class="panel-col">
                    <div class="inside">
                        <p align="justify">
                            <font color="white" face="times" size="3" >  
                            Online Management of Equipments Requisition System in Public Institutions(OMERSPI). Is an application that facilitates the public institutions to manage all the information concerns the institution's equipment,asset and their requisitions as well.
                            <br/><br/>
                            &laquo;Well managed public belongings leads to sustainable development. &raquo;
                            </font>
                        </p>
                    </div>              

                </div>

            </div>
        </center>
    </form> 
    <%@include file="/WEB-INF/jsp/footer/loginFooter.jsp" %>