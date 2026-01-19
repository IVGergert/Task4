<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>User Home</title>
</head>
<body>
    <h1>Welcome, ${sessionScope.user.firstName} ${sessionScope.user.lastName}!</h1>
    <p>You are logged in as: <b>${sessionScope.user.email}</b></p>

    <br>

    <a href="${pageContext.request.contextPath}/controller?command=LOGOUT">Logout</a>

</body>
</html>
