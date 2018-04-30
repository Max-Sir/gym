<%@ page import="by.epam.gym.entities.user.UserRole" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<c:set var="now" value="<%=new java.util.Date()%>"/>
<c:set var="client" value="<%=UserRole.CLIENT%>"/>
<c:set var="currentRole" value="${sessionScope.user.userRole}"/>
<%@ page contentType="text/html;charset=UTF-8"%>

<fmt:bundle basename="page_content">
    <fmt:message key="order.history_title" var="title"/>
    <fmt:message key="order.purchase_date" var="purchase_date"/>
    <fmt:message key="order.end_date" var="end_date"/>
    <fmt:message key="order.duration" var="duration"/>
    <fmt:message key="order.personal_trainer" var="personal_trainer"/>
    <fmt:message key="order.price" var="price"/>
    <fmt:message key="order.status" var="status"/>
    <fmt:message key="order.yes_message" var="yes"/>
    <fmt:message key="order.no_message" var="no"/>
    <fmt:message key="order.not_payed_message" var="not_payed"/>
    <fmt:message key="order.payed_message" var="payed"/>
    <fmt:message key="add_feedback.confirm" var="leave_feedback"/>
    <fmt:message key="order.feedback" var="feedback"/>
    <fmt:message key="order.no_feedback_message" var="no_feedback"/>
    <fmt:message key="order.month_duration" var="month"/>
    <fmt:message key="order.half_year_duration" var="half_year"/>
    <fmt:message key="order.year_duration" var="year"/>
    <fmt:message key="order.show_feedback" var="show_feedback"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/css/font-awesome.min.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<div class="table_order">
    <table>
        <tr>
            <th><span>&#8470;</span></th>
            <th>${pageScope.purchase_date}</th>
            <th>${pageScope.end_date}</th>
            <th>${pageScope.duration}</th>
            <th>${pageScope.personal_trainer}</th>
            <th>${pageScope.price}</th>
            <th>${pageScope.status}</th>
            <th>${pageScope.feedback}</th>
        </tr>
        <c:forEach var="order" items="${requestScope.list}">
            <c:set var="count" value="${pageScope.count+1}"/>
            <tr>
                <td>${count}</td>
                <td>${order.purchaseDate}</td>
                <td>${order.endDate}</td>
                <td><c:choose>
                    <c:when test="${order.duration == 'MONTH'}">
                        ${pageScope.month}
                    </c:when>
                    <c:when test="${order.duration == 'HALF_YEAR'}">
                        ${pageScope.half_year}
                    </c:when>
                    <c:otherwise>
                        ${pageScope.year}
                    </c:otherwise>
                </c:choose></td>
                <td><c:choose>
                    <c:when test="${order.isPersonalTrainerNeed == 1}">
                        ${pageScope.yes}
                    </c:when>
                    <c:otherwise>
                        ${pageScope.no}
                    </c:otherwise>
                </c:choose></td>
                <td>${order.price}</td>
                <td><c:choose>
                    <c:when test="${order.isPayed == 1}">
                        ${pageScope.payed}
                    </c:when>
                    <c:otherwise>
                        ${pageScope.not_payed}
                    </c:otherwise>
                </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${currentRole eq client}">
                            <c:choose>
                                <c:when test="${order.feedback == null && order.endDate < now}">
                                    <c:set var="orderId" scope="session" value="${order.id}"/>
                                    <a href="${pageContext.request.contextPath}/jsp/client/add_feedback.jsp">${pageScope.leave_feedback} <i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                                </c:when>
                                <c:when test="${order.feedback != null}">
                                    <div class="popup" id="${count}" onclick="popUp(this.id)"><a>${pageScope.show_feedback} <i class="fa fa-eye" aria-hidden="true"></i></a>
                                        <span>${order.feedback}</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    ${pageScope.no_feedback}
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${order.feedback != null}">
                                    <div class="popup" id="${count}" onclick="popUp(this.id)"><a>${pageScope.show_feedback} <i class="fa fa-eye" aria-hidden="true"></i></a>
                                        <span>${order.feedback}</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    ${pageScope.no_feedback}
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<script>
    <jsp:directive.include file="/js/popUp.js"/>
</script>
</body>
</html>
