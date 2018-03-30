<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 30.03.2018
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add feedback</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="client_add_feedback"/>
    <br/> Write your feedback:
    <input type="text" name="feedback" value="" />
    <br/>
    <input type="submit" value="Confirm">
</form>
<br/>
${result}
</body>
<a href="controller?command=client_show_orders">Back</a>
</html>
