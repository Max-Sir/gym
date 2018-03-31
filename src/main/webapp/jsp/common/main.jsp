<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<html>
<head><title>The best gym ever.</title></head>
<body>
<h1>Welcome to gym.</h1>
<form>
    <input type="button" value="Login" onClick='location.href="/jsp/common/login.jsp"'>
    <input type="button" value="Register" onClick='location.href="/jsp/common/register.jsp"'>
</form>
<ctg:info-time/>
</body>
</html>
