<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="exercise.show_title" var="title"/>
    <fmt:message key="exercise.beginner" var="beginner"/>
    <fmt:message key="exercise.expert" var="expert"/>
    <fmt:message key="exercise.pro" var="pro"/>
    <fmt:message key="exercise.description" var="description"/>
    <fmt:message key="exercise.name" var="name"/>
    <fmt:message key="exercise.level" var="level"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<div class="show_exercise">
    <h3>${pageScope.name}</h3>
    <p>${requestScope.exercise.name}</p>
    <h3>${pageScope.level}</h3>
    <p><c:choose>
        <c:when test="${requestScope.exercise.level == 'BEGINNER'}">
            ${pageScope.beginner}
        </c:when>
        <c:when test="${requestScope.exercise.level == 'EXPERT'}">
            ${pageScope.expert}
        </c:when>
        <c:otherwise>
            ${pageScope.pro}
        </c:otherwise>
    </c:choose>
    </p>
    <h3>${pageScope.description}</h3>
    <p>${requestScope.exercise.description}</p>
</div>
</body>
</html>
