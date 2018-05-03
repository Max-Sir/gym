<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="add_feedback.title" var="title"/>
    <fmt:message key="add_feedback.input_message" var="input"/>
    <fmt:message key="add_feedback.back" var="back"/>
    <fmt:message key="add_feedback.confirm" var="confirm"/>
    <fmt:message key="title.add_feedback" var="add_feedback_title"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="add_feedback">
    <form method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="client_add_feedback"/>
        <p>${pageScope.input}</p>
        <p><textarea id="feedback_id" title="${pageScope.add_feedback_title}" name="feedback" onkeyup="checkFeedback()"></textarea></p>
        <p><button id="feedback_submit" class="add_feedback_confirm" type="submit" disabled>${pageScope.confirm} <i class="fa fa-check-circle-o" aria-hidden="true"></i></button></p>
    </form>
    <label><a href="controller?command=special_show_client_orders&client_id=${sessionScope.user.id}">${pageScope.back}</a></label>
</div>
<script>
    <jsp:directive.include file="/js/feedbackValidation.js"/>
</script>
</body>
</html>
