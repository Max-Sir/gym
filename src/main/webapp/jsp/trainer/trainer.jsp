<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 18.03.2018
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trainer page.</title>
</head>
<body>
<h1>Trainer page</h1>
<h3>Welcome</h3>
<hr/>
<p>Name: ${user.lastName}</p>
<p>Role: ${user.userRole}</p>

<br/>
${result}
<br/>
<a href="/jsp/trainer/create_exercise.jsp">Create exercise.</a>
<br/>
<a href="controller?command=trainer_prepare_training_program_creation">Create training program.</a>
<br/>
<a href="controller?command=trainer_show_personal_clients">Show personal clients</a>
<br/>
<a href="controller?command=common_logout">Logout</a>
</body>
</html>
