<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tagSql" uri="customtags" %>

<fmt:bundle basename="page_content">
    <fmt:message key="order.pay_title" var="title"/>
    <fmt:message key="order.purchase_date" var="purchase_date"/>
    <fmt:message key="order.end_date" var="end_date"/>
    <fmt:message key="order.duration" var="duration"/>
    <fmt:message key="order.personal_trainer" var="personal_trainer"/>
    <fmt:message key="order.status" var="status"/>
    <fmt:message key="order.yes_message" var="yes"/>
    <fmt:message key="order.no_message" var="no"/>
    <fmt:message key="order.not_payed_message" var="not_payed"/>
    <fmt:message key="order.pay_button" var="pay_button"/>
    <fmt:message key="order.edit_button" var="edit_button"/>
    <fmt:message key="order.month_duration" var="month"/>
    <fmt:message key="order.half_year_duration" var="half_year"/>
    <fmt:message key="order.year_duration" var="year"/>
    <fmt:message key="order.discount" var="discount"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<div class="table_order">
    <table>
        <tr>
            <th>${pageScope.purchase_date}</th>
            <th>${pageScope.end_date}</th>
            <th>${pageScope.duration}</th>
            <th>${pageScope.personal_trainer}</th>
            <th>${pageScope.discount} <tagSql:client_discount id="${sessionScope.user.id}"/></th>
            <th>${pageScope.status}</th>
        </tr>
        <tr>
            <td>${sessionScope.order.purchaseDate}</td>
            <td>${sessionScope.order.endDate}</td>
            <td>
                <c:choose>
                    <c:when test="${sessionScope.order.duration == 'MONTH'}">
                        ${pageScope.month}
                    </c:when>
                    <c:when test="${sessionScope.order.duration == 'HALF_YEAR'}">
                        ${pageScope.half_year}
                    </c:when>
                    <c:otherwise>
                        ${pageScope.year}
                    </c:otherwise>
                </c:choose>
            </td>
            <td><c:choose>
                <c:when test="${sessionScope.order.isPersonalTrainerNeed == 1}">${pageScope.yes}</c:when>
                <c:otherwise>${pageScope.no}</c:otherwise>
            </c:choose>
            </td>
            <td>${sessionScope.order.price}</td>
            <td>${pageScope.not_payed}</td>
        </tr>
    </table>
</div>
<div class="pay_button">
    <ul>
        <li>
            <form action="${pageContext.request.contextPath}/jsp/client/prepare_order.jsp">
                <button type="submit">${pageScope.edit_button} <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                </button>
            </form>
        </li>
        <li>
            <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="client_pay_order"/>
                <button type="submit">${pageScope.pay_button} <i class="fa fa-credit-card" aria-hidden="true"></i>
                </button>
            </form>
        </li>
    </ul>
</div>
</body>
</html>
