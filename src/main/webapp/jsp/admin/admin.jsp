<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 18.03.2018
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Admin page</h1>
<h3>Welcome</h3>
<hr/>
<p>Name: ${user.lastName}</p>
<p>Role: ${user.userRole}</p>
<hr/>
<br/>
<a href="controller?command=logout">Logout</a>
<br/>
<form id="find" name="FindForm" method="GET" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="find_user_by_name"/>
    <br/>Enter client name:<br/>
    <input type="text" id="firstName" name="firstName" value=""/>
    <input type="text" id="lastName" name="lastName" value=""/>
    <input id="submit" type="submit" value="Find" />
</form>
<br/>
<form id="show" name="ShowAllClients" method="GET" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="show_all_clients"/>
    <input type="submit" value="Show all clients" onClick='location.href="/jsp/admin/find_client.jsp"'>
</form>
<br/>
</body>
</html>
