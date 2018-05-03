<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="training_program.create_title" var="title"/>
    <fmt:message key="training_program.select_client" var="select"/>
    <fmt:message key="training_program.name" var="name"/>
    <fmt:message key="training_program.start_date" var="start_date"/>
    <fmt:message key="training_program.end_date" var="end_date"/>
    <fmt:message key="training_program.diet" var="diet"/>
    <fmt:message key="training_program.days_count" var="days_count"/>
    <fmt:message key="training_program.create_training_program" var="create"/>
    <fmt:message key="title.training_program_start_date" var="title_start"/>
    <fmt:message key="title.training_program_end_date" var="title_end"/>
    <fmt:message key="title.training_program_days_count" var="title_count"/>
    <fmt:message key="title.training_program_diet" var="title_diet"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="create_training_program">
    <form id="createTrainingProgram" method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="trainer_create_training_program"/>
        <p><label>${pageScope.select} <select name="client_id">
            <c:forEach var="clients" items="${sessionScope.list}">
                <c:set var="count" value="${pageScope.count+1}"/>
                <option value="${clients.key}"><span>&#8470;</span> ${count} ID: ${clients.key} ${clients.value}
                </option>
            </c:forEach>
        </select></label></p>
        <p><label>${pageScope.start_date} <input title="${pageScope.title_start}" id="purchase_date" type="date"
                                                 name="start_date" pattern="yyyy-MM-dd"
                                                 onchange="checkStartDate()"/></label></p>
        <p><label>${pageScope.end_date} <input title="${pageScope.title_end}" id="end_date" type="date" name="end_date"
                                               pattern="yyyy-MM-dd" onchange="checkEndDate()"/></label></p>
        <p><label>${pageScope.days_count} <input title="${pageScope.title_count}" id="training_days"
                                                 class="create_training_program_number" type="number" name="days_count"
                                                 onchange="checkDaysCount()" value="2"/></label></p>
        <p>${pageScope.diet}</p>
        <p>
            <textarea title="${pageScope.title_diet}" id="diet_area" name="diet" onkeyup="checkDiet()"></textarea>
        </p>
        <button type="submit" id="button_create" disabled>${pageScope.create} <i class="fa fa-plus-circle"
                                                                                 aria-hidden="true"></i></button>
    </form>
</div>
<script>
    <jsp:directive.include file="/js/trainingProgramValidation.js"/>
</script>
</body>
</html>
