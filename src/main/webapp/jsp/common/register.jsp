<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 18.03.2018
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registration</title>
</head>
<body>
<script type="text/javascript">
    <jsp:include page="/js/reg.js"/>
</script>
<br/>Login:<br/>
<form id="logCheck" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="check_login"/>
    <input id="login" type="text" name="login" value="" onkeyup=checkLoginForPattern(); onchange="this.form.submit()"/>
    ${result}
</form>
<form id="reg" name="RegisterForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="register"/>
        <br/>Password:<br/>
            <input id="password" type="password" name="password" value="" onkeyup=checkPass(); />
        <br/>Repeat password<br/>
            <input id="confirm_password" type="password" name="confirm_password"  value="" onkeyup=checkPass(); />
        <br/>First name:<br/>
             <input id="first_name" type="text" name="first name" value="" onkeyup=checkName(); />
        <br/>Last name:<br/>
             <input id="last_name" type="text" name="last name" value="" onkeyup=checkName(); />
    ${errorRegisterMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input id="submit" type="submit" value="Register" disabled/>
</form>
</body>
</html>
