<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="client.description_title" var="title"/>
    <fmt:message key="client.next" var="next"/>
    <fmt:message key="client.previous" var="previous"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<tag:clientDescription clients="${requestScope.list}" role="${sessionScope.user.userRole}"/>
<div class="navigation_buttons">
    <ul>
        <c:if test="${requestScope.pageIndex != 1}">
            <li>
                <a href="controller?command=admin_show_all_clients&page=${requestScope.pageIndex - 1}">${pageScope.previous}</a>
            </li>
        </c:if>
        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
            <li>
                <c:choose>
                    <c:when test="${requestScope.pageIndex eq i}">${i}</c:when>
                    <c:otherwise>
                        <a href="controller?command=admin_show_all_clients&page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
        <c:if test="${requestScope.pageIndex < requestScope.numberOfPages}">
            <li>
                <a href="controller?command=admin_show_all_clients&page=${requestScope.pageIndex + 1}">${pageScope.next}</a>
            </li>
        </c:if>
    </ul>
</div>
</body>
</html>
