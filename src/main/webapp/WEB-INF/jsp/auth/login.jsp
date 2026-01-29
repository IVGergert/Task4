<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Login</title>
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

    <div class="container">

        <h2>Login</h2>

        <c:if test="${not empty error_msg}">
            <div style="color:red">${error_msg}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/controller" method="post">

            <input type="hidden" name="command" value="login">

            <div class="form-group">
                <label>Email:</label>
                <input type="text" name="email" value="${email}" required/>
            </div>

            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" required/>
            </div>

            <button type="submit">Sign in</button>
        </form>

        <br/>

        <a href="${pageContext.request.contextPath}/controller?command=GO_TO_REGISTER">Register</a>
    </div>

</body>
</html>
