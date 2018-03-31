<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 31.03.2018
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add exercise to training program.</title>
</head>
<body>
<c:forEach var="day" items="${sessionScope.days}">
    Day${day.key}
    <ol>
        <c:forEach var="exercise" items="${day.value}">
            <li>
                    ${exercise.name}
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="common_edit_exercise"/>
                    <input type="hidden" name="dayNumber" value="${day.key}" />
                    <input type="hidden" name="exerciseId" value="${exercise.id}"/>
                    <br/> Подходы:
                    <input type="number" name="setsCount" value="${exercise.setsCount}">
                    <br/>  Повторения:
                    <input type="number" name="repeatsCount" value="${exercise.repeatsCount}">
                    <br/>
                    <input type="submit" value="edit"/>
                </form>

                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="common_delete_exercise_from_training_program"/>
                    <input type="hidden" name="dayNumber" value="${day.key}" />
                    <input type="hidden" name="exerciseId" value="${exercise.id}"/>
                    <input type="submit" value="Delete exercise"/>
                </form>
            </li>
        </c:forEach>
    </ol>
    <form method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="common_add_exercise_to_training_program"/>
        <input type="hidden" name="dayNumber" value="${day.key}" />
        <br/>
        <select name="exerciseId" title="Exercise name.">
            <c:forEach var="chooseExercise" items="${sessionScope.exercises}">
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
<br/>
${result}
<br/>
<a href="controller?command=common_finish_training_program_creation">Finish</a>
</body>
</html>
