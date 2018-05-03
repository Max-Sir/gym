<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="training_program.description_title" var="title"/>
    <fmt:message key="training_program.head" var="head"/>
    <fmt:message key="training_program.day" var="day_message"/>
    <fmt:message key="training_program.sets_count" var="sets_count"/>
    <fmt:message key="training_program.repeats_count" var="repeats_count"/>
    <fmt:message key="training_program.exercise_title" var="exercise_title"/>
    <fmt:message key="training_program.start_date" var="start_date"/>
    <fmt:message key="training_program.end_date" var="end_date"/>
    <fnt:message key="training_program.author" var="author"/>
    <fmt:message key="training_program.edit_training_program" var="edit"/>
    <fmt:message key="training_program.refuse" var="refuse"/>
    <fmt:message key="training_program.diet" var="diet"/>
    <fmt:message key="exercise.description" var="exercise_description"/>
    <fmt:message key="exercise.level_message" var="level"/>
    <fmt:message key="exercise.beginner" var="beginner"/>
    <fmt:message key="exercise.expert" var="expert"/>
    <fmt:message key="exercise.pro" var="pro"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="training_program_description">
    <div class="training_program_description_top">
        <h1>${pageScope.head}</h1>
    </div>
    <ul>
        <c:forEach var="day" items="${sessionScope.daysAndExercises}">
            <li class="training_program_description_li">${pageScope.day_message} ${day.key}
                <ol>
                    <c:forEach var="exercise" items="${day.value}">
                        <li>
                            <a class="collapsible">${exercise.name} </a> ${pageScope.sets_count} ${exercise.setsCount} ${pageScope.repeats_count} ${exercise.repeatsCount}
                            <div class="content">
                                <p>${pageScope.level} <c:choose>
                                    <c:when test="${exercise.level == 'BEGINNER'}">
                                        ${pageScope.beginner}
                                    </c:when>
                                    <c:when test="${exercise.level == 'EXPERT'}">
                                        ${pageScope.expert}
                                    </c:when>
                                    <c:otherwise>
                                        ${pageScope.pro}
                                    </c:otherwise>
                                </c:choose>
                                </p>
                                <p>${pageScope.exercise_description} ${exercise.description}</p>
                            </div>
                        </li>
                    </c:forEach>
                </ol>
            </li>
        </c:forEach>
        <li class="training_program_description_li">${pageScope.diet} <p>${sessionScope.trainingProgram.diet}</p></li>
        <li>${pageScope.start_date} ${sessionScope.trainingProgram.startDate}</li>
        <li>${pageScope.end_date} ${sessionScope.trainingProgram.endDate}</li>
        <c:if test="${sessionScope.user.userRole == 'CLIENT'}">
            <p>${pageScope.author} ${sessionScope.name}</p>
        </c:if>
    </ul>
</div>
<div class="training_program_client_buttons">
    <ul>
        <li>
            <form method="GET" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="special_edit_training_program"/>
                <button type="submit">${pageScope.edit} <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                </button>
            </form>
        </li>
        <c:if test="${sessionScope.user.userRole == 'CLIENT'}">
            <li>
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="client_refuse_training_program"/>
                    <input type="hidden" name="training_program_id" value="${sessionScope.trainingProgram.id}"/>
                    <button type="submit">${pageScope.refuse} <i class="fa fa-trash-o" aria-hidden="true"></i></button>
                </form>
            </li>
        </c:if>
    </ul>
</div>
<script>
    <jsp:directive.include file="/js/collapsible.js"/>
</script>
</body>
</html>
