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
    <script src="/js/validation.js"></script>
</head>
<body>
<form id="reg" name="RegisterForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="common_register"/>
    <br/>Login:<br/>
    <input id="login" type="text" name="login" value="" onkeyup=checkLogin(); /> ${loginError}
    <br/>Password:<br/>
    <input id="password" type="password" name="password" value="" onkeyup=checkPassword(); />
    <br/>Repeat password<br/>
    <input id="confirm_password" type="password" value="" onkeyup=checkPassword(); />
    <br/>First name:<br/>
    <input id="first_name" type="text" name="first_name" value="" onkeyup=checkName(); />
    <br/>Last name:<br/>
    <input id="last_name" type="text" name="last_name" value="" onkeyup=checkName(); />
    <br/>
    <input id="submit" type="submit" value="Register" disabled/>
    <br/>
    ${result}
    <br/>
</form>
</body>
</html>
