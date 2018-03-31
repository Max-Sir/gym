<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 18.03.2018
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Client page</h1>
<h3>Welcome</h3>
<hr/>
<p>Name: ${user.lastName}</p>
<p>Role: ${user.userRole}</p>
<hr/>
<br/>
<a href="${pageContext.request.contextPath}/controller?command=client_show_orders">Orders history.</a>
<br/>
<a href="/jsp/client/prepare_order.jsp">Make order.</a>
<br/>
<a href="${pageContext.request.contextPath}/controller?command=common_logout">Logout</a>
<br/>
<a href="${pageContext.request.contextPath}/controller?command=client_show_training_program">Training</a>
<br/>
${result}
</body>
</html>
