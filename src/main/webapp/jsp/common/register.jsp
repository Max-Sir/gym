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
<form id="reg" name="RegisterForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="register"/>
         <br/>Login:<br/>
             <input id="login" type="text" name="login" value="" onkeyup=checkLoginForPattern(); /> ${loginError}
        <br/>Password:<br/>
            <input id="password" type="password" name="password" value="" onkeyup=checkPass(); /> ${passError}
        <br/>Repeat password<br/>
            <input id="confirm_password" type="password" value="" onkeyup=checkPass(); />
        <br/>First name:<br/>
             <input id="first_name" type="text" name="first_name" value="" onkeyup=checkName(); /> ${firstNameError}
        <br/>Last name:<br/>
             <input id="last_name" type="text" name="last_name" value="" onkeyup=checkName(); /> ${lastNameError}
        <br/>
            <input id="submit" type="submit" value="Register" disabled/>
        <br/>
    ${result}
        <br/>
</form>
    <br/>
        <a href="/jsp/common/login.jsp">Login</a>
    <br/>
</body>
</html>
