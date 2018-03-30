<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 30.03.2018
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order form.</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="client_make_order"/>

</form>
</body>
</html>
