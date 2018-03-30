<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="now" value="<%=new java.util.Date()%>" />
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 29.03.2018
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders information.</title>
</head>
<body>
<table>
    <tr>
        <th>№</th>
        <th>Purchase date</th>
        <th>End date</th>
        <th>Type of season ticket</th>
        <th>Is personal trainer need?</th>
        <th>Price</th>
        <th>Status</th>
        <th>Feedback</th>
    </tr>
        <c:forEach var="order" items="${list}">
            <tr>
                <td>${order.id}</td>
                <td>${order.purchaseDate}</td>
                <td>${order.endDate}</td>
                <td>${order.duration}</td>
                <td><c:choose>
                    <c:when test="${order.isPersonalTrainerNeed == 1}">
                        yes
                    </c:when>
                    <c:when test="${order.isPersonalTrainerNeed == 0}">
                        no
                    </c:when>
                </c:choose></td>
                <td>${order.price}</td>
                <td><c:choose>
                        <c:when test="${order.isPayed == 1}">
                            payed
                        </c:when>
                        <c:when test="${order.isPayed == 0}">
                            not payed
                        </c:when>
                    </c:choose>
                </td>
                <td><c:choose>
                        <c:when test="${order.feedback == null && order.endDate < now}">
                            <c:set var="orderId" scope="session" value="${order.id}" />
                            <a href="/jsp/client/add_feedback.jsp">add feedback</a>
                        </c:when>
                        <c:when test="${order.feedback != null}">
                            ${order.feedback}
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>

</table>
</body>
</html>
