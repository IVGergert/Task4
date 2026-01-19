<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>

<div class="container" style="text-align: center">

    <h2 style="text-align: center">Login</h2>

    <c:if test="${not empty error_msg}">
        <div style="color:red">${error_msg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/controller" method="post">

        <input type="hidden" name="command" value="login">

        <label>Email:</label>
        <input type="text" name="email" value="${email}" required/><br/>

        <label>Password:</label>
        <input type="password" name="password" required/><br/>

        <button type="submit">Sign in</button>

    </form>

    <br/>

    <a href="${pageContext.request.contextPath}/controller?command=GO_TO_REGISTER">Register</a>
</div>
</body>
</html>
