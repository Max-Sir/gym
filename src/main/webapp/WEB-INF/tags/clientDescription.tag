<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="clients" required="true" type="java.util.List" %>
<%@attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300i,700&amp;subset=cyrillic" rel="stylesheet">

<fmt:bundle basename="page_content">
    <fmt:message key="client.name" var="name"/>
    <fmt:message key="client.order" var="order"/>
    <fmt:message key="client.training_program" var="training_program"/>
    <fmt:message key="client.describe" var="describe"/>
</fmt:bundle>

<div class="table_user">
    <table>
        <tr>
            <th><span>&#8470;</span></th>
            <th>ID</th>
            <th>${pageScope.name}</th>
            <th><c:choose>
                <c:when test="${role.equals('TRAINER')}">
                    ${pageScope.training_program}
                </c:when>
                <c:otherwise>
                    ${pageScope.order}
                </c:otherwise>
            </c:choose></th>
        </tr>
        <c:forEach var="client" items="${clients}">
            <c:set var="count" value="${pageScope.count+1}"/>
            <tr>
                <td>${count}</td>
                <td>${client.id}</td>
                <td>${client.firstName} ${client.lastName}</td>
                <td>
                    <c:choose>
                        <c:when test="${role.equals('TRAINER')}">
                            <a href="controller?command=special_show_client_training_program&client_id=${client.id}">${pageScope.describe} <i class="fa fa-info-circle" aria-hidden="true"></i></a>
                        </c:when>
                        <c:otherwise>
                            <a href="controller?command=special_show_client_orders&client_id=${client.id}">${pageScope.describe} <i class="fa fa-info-circle" aria-hidden="true"></i></a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>