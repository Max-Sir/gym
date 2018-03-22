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
    <form id="find" name="FindForm" method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="find_user_by_name"/>
            <br/>Enter client name:<br/>
                <input type="text" id="find_user_by_name" name="find_user_by_name" value=""/>
                    <input id="submit" type="submit" value="Find" />
    </form>
</body>
</html>
