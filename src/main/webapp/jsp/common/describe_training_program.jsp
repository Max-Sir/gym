<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="calendar" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                Подходы: ${exercise.setsCount}
                Повторения: ${exercise.repeatsCount}
            </li>
        </c:forEach>
    </ol>
</c:forEach>

<br> Начало программы:
<calendar:formatDate value="${startDate}" pattern="yyyy-MM-dd" />
<br> Конец программы:
<calendar:formatDate value="${endDate}" pattern="yyyy-MM-dd" />
<br> Автор программы: ${name}
<br> Диета: ${diet}
</body>
</html>
