<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <th>Price</th>
        <th>Feedback</th>
        <th>Status</th>
    </tr>
        <c:forEach var="order" items="${list}">
            <tr>
            <td>${order.id}</td>
            <td>${order.purchaseDate}</td>
            <td>${order.price}</td>
            <td>${order.feedback}</td>
            <td><c:choose>
                <c:when test="${order.isPayed == 1}">
                    payed
                </c:when>
                <c:when test="${order.isPayed == 0}">
                    not payed
                </c:when>
            </c:choose>
            </td>
            </tr>
        </c:forEach>

</table>
</body>
</html>
