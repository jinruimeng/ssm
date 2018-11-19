<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>Success Page</h4>
	
	time: ${requestScope.time}
	<br><br>
	
	names: ${requestScope.names}
	<br><br>
	
	request user: ${requestScope.user}
	<br><br>
	
	session user: ${sessionScope.user}
	<br><br>
	
	<fmt:message key="i18n.username"></fmt:message>
	<br><br>
	
	<fmt:message key="i18n.password"></fmt:message>
	<br><br>
</body>
</html>