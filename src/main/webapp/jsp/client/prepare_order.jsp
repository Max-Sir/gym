<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order form.</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="client_prepare_order"/>
    <br/>Enter start date:
    <input type="date" name="startDate" value=""/>
    <br/>Enter type:
    <select name="duration">
        <option value="MONTH">Month</option>
        <option value="HALF_YEAR">Half year</option>
        <option value="YEAR">Year</option>
    </select>
    <br/>Do you need personal trainer?
    <input type="radio" name="isPersonalTrainerNeed" value="1" >Yes<br/>
    <input type="radio" name="isPersonalTrainerNeed" value="0" checked>No<br/>
    <br/>
    <input type="submit" value="Confirm">
</form>
</body>
</html>
