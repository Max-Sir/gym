<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="order.form_title" var="title"/>
    <fmt:message key="order.purchase_date" var="purchase_date"/>
    <fmt:message key="order.duration" var="duration"/>
    <fmt:message key="order.yes_message" var="yes"/>
    <fmt:message key="order.no_message" var="no"/>
    <fmt:message key="order.month_duration" var="month"/>
    <fmt:message key="order.half_year_duration" var="half_year"/>
    <fmt:message key="order.year_duration" var="year"/>
    <fmt:message key="order.need_personal_trainer" var="need_trainer"/>
    <fmt:message key="order.confirm" var="confirm"/>
    <fmt:message key="title.order_purchase_date" var="title_purchase_date"/>
    <fmt:message key="title.order_duration" var="title_duration"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="prepare_order">
    <form method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="client_prepare_order"/>
        <p><label>${pageScope.purchase_date}<input class="date_input" title="${pageScope.title_purchase_date}" id="purchase_date" type="date" name="start_date" onchange="checkDate()"/></label></p>
        <p><label>
            ${pageScope.duration}
            <select title="${pageScope.title_duration}" class="duration_select" name="duration">
                <option value="MONTH">${pageScope.month}</option>
                <option value="HALF_YEAR">${pageScope.half_year}</option>
                <option value="YEAR">${pageScope.year}</option>
            </select>
        </label>
        </p>
        <ul>
            <li> ${pageScope.need_trainer}</li>
            <li><input type="radio" name="is_personal_trainer_need" value="1">${pageScope.yes}</li>
            <li><input type="radio" name="is_personal_trainer_need" value="0" checked>${pageScope.no}</li>
        </ul>
        <button class="prepare_order_confirm" id="button_confirm" type="submit" disabled>${pageScope.confirm} <i class="fa fa-check-circle-o" aria-hidden="true"></i></button>
    </form>
</div>
<script>
    <jsp:directive.include file="/js/orderValidation.js"/>
</script>
</body>
</html>
