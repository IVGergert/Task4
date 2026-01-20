<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>500</title>
</head>
<body>
    <h2>500 - Internal Server Error</h2>

    <form action = "controller" name="command">

    <p>Internal Server Error</p>

    <p>
        <a href="${pageContext.request.contextPath}/controller?command=GO_TO_LOGIN">Go to login</a>
    </p>

    </form>
</body>
</html>
