<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 18.03.2018
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" language="java" %>

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
<a href="controller?command=common_logout">Logout</a>
<br/>
<form id="find" name="FindForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="admin_find_user_by_name"/>
    <br/>Enter client name:<br/>
    <br/>First name:
    <input type="text" id="firstName" name="firstName" value=""/>
    <br/>Last name:
    <input type="text" id="lastName" name="lastName" value=""/>
    <br/>
    <input id="submit" type="submit" value="Find" />
</form>
<br/>
<form id="show" name="ShowAllClients" method="GET" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="admin_show_all_clients"/>
    <input type="submit" value="Show all clients" onClick='location.href="/jsp/admin/admin_find_client.jsp"'>
</form>
<br/>
</body>
</html>
