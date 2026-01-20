<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>403</title>
</head>
<body>
    <form action = "controller" name="command">
        <p> You do not have access rights to this page.</p>

        <p>
            <a href="${pageContext.request.contextPath}/controller?command=GO_TO_LOGIN">Go to login</a>
        </p>

    </form>
</body>
</html>
