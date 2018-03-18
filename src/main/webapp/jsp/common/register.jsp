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
    <jsp:include page="/js/password_validation.js"/>
</script>
<form id="reg" name="RegisterForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="register"/>
    <br/>Login:<br/>
    <input id="login" type="text" name="Login" value="" onkeyup=check(); />
    <br/>Password:<br/>
    <input id="password" type="password" name="password" value="" onkeyup=check(); />
    <br/>Repeat password<br/>
    <input id="confirm_password" type="password" name="confirm_password"  value="" onkeyup=check(); />
    <br/>First name:<br/>
    <input id="first_name" type="text" name="First name" value=""/>
    <br/>Last name:<br/>
    <input id="last_name" type="text" name="Last name" value=""/>
    ${errorRegisterMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input type="submit" value="Register"/>
</form>
</body>
</html>
