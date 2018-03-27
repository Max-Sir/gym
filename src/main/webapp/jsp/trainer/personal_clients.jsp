<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 26.03.2018
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Personal clients</title>
</head>
<body>
<table>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Training program id</th>
    </tr>
    <c:forEach var="findClients" items="${list}">
        <tr>
            <td><c:out value="${findClients.key.id}" default="lol" /></td>
            <td><c:out value="${findClients.key.firstName}" default="lol" /></td>
            <td><c:out value="${findClients.key.lastName}" default="lol" /></td>
            <td><c:out value="${findClients.value}" default="lol" />
                <a href="controller?command=common_describe_training_program&programId=${findClients.value}">Describe</a>
            </td>
        </tr>
    </c:forEach>
</table>
${result}
</body>
</html>
