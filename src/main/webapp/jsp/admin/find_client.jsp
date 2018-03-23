<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 22.03.2018
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <table>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        <c:forEach var="findUsers" items="${list}">
            <tr>
                 <td>${findUsers.id}</td>
                 <td>${findUsers.firstName}</td>
                 <td>${findUsers.lastName}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
