<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 30.03.2018
  Time: 23:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pay order</title>
</head>
<body>
<table>
    <tr>
        <th>Purchase date</th>
        <th>End date</th>
        <th>Type of season ticket</th>
        <th>Is personal trainer need?</th>
        <th>Price</th>
        <th>Status</th>
    </tr>
    <tr>
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
        <td>not payed</td>
    </tr>
</table>
<br/>
<a href="/jsp/client/prepare_order.jsp">edit</a>
<br/>
<a href="controller?command=client_pay_order">pay</a>
</body>
</html>
