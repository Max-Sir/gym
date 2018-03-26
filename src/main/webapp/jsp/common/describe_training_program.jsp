<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 27.03.2018
  Time: 0:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Training program description.</title>
</head>
<body>
<br>Training program
<br>
<c:forEach var="day" items="${days}">
    Day${day.key}
    <ol>
        <c:forEach var="exercise" items="${day.value}">
            <li>
                ${exercise.name}
            </li>
        </c:forEach>
    </ol>
</c:forEach>
</body>
</html>
