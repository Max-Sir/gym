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
<a href="controller?command=logout">Logout</a>
</body>
</html>
