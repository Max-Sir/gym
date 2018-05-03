<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="training_program_edit.title" var="title"/>
    <fmt:message key="training_program.day" var="day_message"/>
    <fmt:message key="training_program.delete_day" var="delete_day"/>
    <fmt:message key="training_program.sets_count" var="sets_count"/>
    <fmt:message key="training_program.repeats_count" var="repeats_count"/>
    <fmt:message key="training_program.edit_exercise" var="edit_exercise"/>
    <fmt:message key="training_program.delete_exercise" var="delete_exercise"/>
    <fmt:message key="training_program.add_exercise" var="add_exercise"/>
    <fmt:message key="training_program.add_day" var="add_day"/>
    <fmt:message key="training_program.diet" var="diet"/>
    <fmt:message key="training_program.save_diet" var="save_diet"/>
    <fmt:message key="training_program.save_training_program" var="save_training_program"/>
    <fmt:message key="training_program.exercise_title" var="exercise_title"/>
    <fmt:message key="exercise.description" var="exercise_description"/>
    <fmt:message key="exercise.level_message" var="level"/>
    <fmt:message key="exercise.beginner" var="beginner"/>
    <fmt:message key="exercise.expert" var="expert"/>
    <fmt:message key="exercise.pro" var="pro"/>
    <fmt:message key="title.training_program_diet" var="title_diet"/>
    <fmt:message key="title.sets_count" var="title_sets"/>
    <fmt:message key="title.repeats_count" var="title_repeats"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="edit_training_program_wrapper">
    <c:forEach var="day" items="${sessionScope.daysAndExercises}">
        <div class="day_wrapper">
                ${pageScope.day_message} ${day.key}
            <ol>
                <c:forEach var="exercise" items="${day.value}">
                    <li>
                        <a class="collapsible">${exercise.name}</a>
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
                        <div class="exercises_button">
                            <ul>
                                <li>
                                    <form method="POST" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command"
                                               value="special_edit_exercise_in_training_program"/>
                                        <input type="hidden" name="day_number" value="${day.key}"/>
                                        <input type="hidden" name="exercise_id" value="${exercise.id}"/>
                                        <label>${pageScope.sets_count} <input title="${pageScope.title_sets}"
                                                                              type="number" name="sets_count"
                                                                              value="${exercise.setsCount}"></label>
                                        <label>${pageScope.repeats_count} <input title="${pageScope.title_repeats}"
                                                                                 type="number" name="repeats_count"
                                                                                 value="${exercise.repeatsCount}"></label>
                                        <button class="edit_training_program_wrapper_button"
                                                type="submit">${pageScope.edit_exercise} <i
                                                class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                    </form>
                                </li>
                                <li>
                                    <form method="POST" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command"
                                               value="special_delete_exercise_from_training_program"/>
                                        <input type="hidden" name="day_number" value="${day.key}"/>
                                        <input type="hidden" name="exercise_id" value="${exercise.id}"/>
                                        <button class="edit_training_program_wrapper_button"
                                                type="submit">${pageScope.delete_exercise} <i class="fa fa-trash"
                                                                                              aria-hidden="true"></i>
                                        </button>
                                    </form>
                                </li>
                            </ul>
                        </div>
                    </li>
                </c:forEach>
            </ol>
            <div class="add_exercise_wrapper">
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="special_add_exercise_to_training_program"/>
                    <input type="hidden" name="day_number" value="${day.key}"/>
                    <label>${pageScope.name} <select name="exercise_id">
                        <c:forEach var="chooseExercise" items="${sessionScope.exercises}">
                            <option value="${chooseExercise.id}"
                                    title=" ${chooseExercise.description}">${chooseExercise.name}</option>
                        </c:forEach>
                    </select></label>
                    <label>${pageScope.sets_count} <input title="${pageScope.title_sets}" type="number"
                                                          name="sets_count" value="1"/></label>
                    <label>${pageScope.repeats_count} <input title="${pageScope.title_repeats}" type="number"
                                                             name="repeats_count" value="1"/></label>
                    <button class="edit_training_program_wrapper_button" type="submit">${pageScope.add_exercise} <i
                            class="fa fa-plus-square" aria-hidden="true"></i>
                    </button>
                </form>
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="special_delete_day_from_training_program"/>
                <input type="hidden" name="day_number" value="${day.key}"/>
                <button class="edit_training_program_wrapper_button" type="submit">${pageScope.delete_day} <i
                        class="fa fa-trash" aria-hidden="true"></i></button>
            </form>
        </div>
    </c:forEach>
    <div class="diet_wrapper">
        <form method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="special_edit_diet_in_training_program"/>
            <p>${pageScope.diet}</p>
            <p><textarea title="${pageScope.title_diet}" id='diet_area' name="diet"
                         onkeyup="checkDiet()">${sessionScope.trainingProgram.diet}</textarea></p>
            <button class="edit_training_program_wrapper_button" type="submit">${pageScope.save_diet} <i
                    class="fa fa-plus-square" aria-hidden="true"></i></button>
        </form>
    </div>
    <div class="add_buttons">
        <ul>
            <li>
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="special_add_day_to_training_program"/>
                    <button class="edit_training_program_wrapper_button" type="submit">${pageScope.add_day} <i
                            class="fa fa-plus-square" aria-hidden="true"></i>
                    </button>
                </form>
            </li>
            <li>
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <c:choose>
                        <c:when test="${sessionScope.trainingProgram.id == 0}">
                            <input type="hidden" name="command" value="trainer_finish_training_program_creation"/>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="command" value="special_save_training_program_edit"/>
                        </c:otherwise>
                    </c:choose>
                    <button id="save_training_program" class="save_button" type="submit"
                            disabled>${pageScope.save_training_program} <i class="fa fa-plus-square"
                                                                           aria-hidden="true"></i></button>
                </form>
            </li>
        </ul>
    </div>
</div>
<script>
    <jsp:directive.include file="/js/collapsible.js"/>
    <jsp:directive.include file="/js/editValidation.js"/>
</script>
</body>
</html>
