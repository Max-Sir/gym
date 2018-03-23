<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 23.03.2018
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<jsp:include page="find_client.jsp" />
 <c:if test="${pageIndex != 1}">
    <td><a href="controller?command=show_all_clients&page=${pageIndex - 1}">Previous</a></td>
 </c:if>
<table border="1" cellpadding="10" cellspacing="10">
    <tr>
        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <c:choose>
                <c:when test="${pageIndex eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="controller?command=show_all_clients&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>
<c:if test="${currentPage lt numberOfPages}">
    <td><a href="controller?command=user_list&page=${currentPage + 1}">Next</a></td>
</c:if>
${result}
</body>
</html>
