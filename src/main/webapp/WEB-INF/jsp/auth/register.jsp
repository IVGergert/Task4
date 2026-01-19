<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Register</title>
</head>
<body>
<div class="container" style="text-align: center">
    <h2 style="text-align: center">Register</h2>

    <c:if test="${not empty error_msg}">
        <div style="color:red">${error_msg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="register">

        <label>Email:</label>
        <input type="email" name="email" value="${email}" required/><br/>

        <label>First Name:</label>
        <input type="text" name="first_name" value="${firstName}" required><br>

        <label>Last Name:</label>
        <input type="text" name="last_name" value="${lastName}" required><br>

        <label>Password:</label>
        <input type="password" name="password" required/><br/>

        <button type="submit">Create account</button>
    </form>

    <p>
        <a href="${pageContext.request.contextPath}/controller?command=GO_TO_LOGIN">Back to login</a>
    </p>

</div>
</body>
</html>
