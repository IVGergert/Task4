<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Register</title>
    <style>
        .container{
            text-align: center;
            font-family: Arial, sans-serif;
            margin-top: 50px;
        }

        .form-group {
            margin-bottom: 10px;
        }

        label {
            display: inline-block;
            width: 100px;
            text-align: right;
            margin-right: 5px;
        }

        input, button {
            width: 200px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<div class="container" align="center">
    <h2 style="text-align: center">Register</h2>

    <c:if test="${not empty error_msg}">
        <div style="color:red">${error_msg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="register">

        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" value="${email}" required/><br/>
        </div>

        <div class="form-group">
            <label>First Name:</label>
            <input type="text" name="first_name" value="${firstName}" required><br>
        </div>

        <div class="form-group">
            <label>Last Name:</label>
            <input type="text" name="last_name" value="${lastName}" required><br>
        </div>

        <div class="form-group">
            <label>Password:</label>
            <input type="password" name="password" required/><br/>
        </div>

        <button type="submit">Create account</button>
    </form>

    <p>
        <a href="${pageContext.request.contextPath}/controller?command=GO_TO_LOGIN">Back to login</a>
    </p>

</div>
</body>
</html>
