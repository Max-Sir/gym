<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 25.03.2018
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<form id="addExercise" name="addExercise" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="trainer_add_exercise"/>
    <br/>Name:<br/>
    <input id="name" type="text" name="name" value="" />
    <br/>Select level:<br/>
    <select name="level">
        <option value="BEGINNER">Beginner</option>
        <option value="EXPERT">Expert</option>
        <option value="PRO">Professional</option>
    </select>
    <br/>Description:<br/>
    <input id="description" type="text" name="description" value="" />
    <br/>
    <input id="submit" type="submit" value="Add"/>
    <br/>
    ${result}
    <br/>
</form>
</body>
</html>
