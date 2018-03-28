<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 28.03.2018
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add exercise to training program.</title>
</head>
<body>
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
    <form method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="trainer_add_exercise_to_training_program"/>
        <input type="hidden" name="dayNumber" value="${day.key}" />
        <input type="hidden" name="programId" value="${programId}" />
        <br/>
        <select name="exerciseId" title="Exercise name.">
            <c:forEach var="chooseExercise" items="${exercises}">
                <option value="${chooseExercise.key}">${chooseExercise.value}</option>
            </c:forEach>
        </select>
        <br/>Sets count:
        <input type="number" name="setsCount" value=""/>
        <br/>Repeats count:
        <input type="number" name="repeatsCount" value=""/>
        <br/>
        <input type="submit" value="Add exercise"/>
    </form>
</c:forEach>
</body>
</html>
