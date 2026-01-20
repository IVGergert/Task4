<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>404</title>
</head>
<body>
    <h2>404 - Not Found path</h2>

    <form action = "controller" name="command">

        <p>Request URI: ${requestScope['jakarta.servlet.error.request_uri']}</p>

        <p>
            <a href="${pageContext.request.contextPath}/controller?command=GO_TO_LOGIN">Go to login</a>
        </p>

    </form>
</body>
</html>
