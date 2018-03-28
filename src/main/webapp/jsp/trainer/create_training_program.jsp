<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 28.03.2018
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Training program creation.</title>
</head>
<body>
<form id="createTrainingProgram" method="POST" action="${pageContext.request.contextPath}/controller" >
    <input type="hidden" name="command" value="trainer_create_training_program" />
    <br/>Select client:
        <select name="clientId">
            <c:forEach var="clients" items="${list}">
                 <option  value="${clients.key}">ID: ${clients.key} Name: ${clients.value}</option>
             </c:forEach>
        </select>
    <br/>Date start:
        <input type="date" name="startDate" pattern="yyyy-MM-dd" value=""/>
    <br/>Date end:
        <input type="date" name="endDate" pattern="yyyy-MM-dd" value=""/>
    <br/>Number of days:
        <input type="number" name="daysCount" value=""/>
    <br/>Diet:
        <input type="text" name="diet" value=""/>
    <br/>
        <input type="submit" value="Create"/>
</form>
<br/>
</body>
</html>
