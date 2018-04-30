<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:bundle basename="page_content">
    <fmt:message key="main.title" var="title"/>
    <fmt:message key="page.content.info" var="info"/>
    <fmt:message key="page.content.news" var="news"/>
    <fmt:message key="page.content.promotion" var="promotion"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="wrapper_content">
    <div class="wrapper_content-item  clearfix">
        <img src="${pageContext.request.contextPath}/img/info_content.jpg" alt="" width="300px" height="200px">
        <p>
            ${pageScope.info}
        </p>
    </div>
    <div class="wrapper_content-item clearfix">
        <img src="${pageContext.request.contextPath}/img/news_content.jpg" alt="" width="300px" height="200px">
        <p>${pageScope.news}
        </p>
    </div>
    <div class="wrapper_content-item  clearfix">
        <img src="${pageContext.request.contextPath}/img/promotion_content.jpg" alt="" width="300px" height="200px">
        <p>
            ${pageScope.promotion}
        </p>
    </div>
</div>
<footer class="footer">
    <p>
        <span><i class="fa fa-mobile" aria-hidden="true"></i> +375 (25)<strong>1111111</strong></span>
        Web Design By: Eugene Makarenko
    </p>
</footer>
</body>
</html>