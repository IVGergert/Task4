<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>500</title>
</head>
<body>
java.lang.Throwable
Request from: ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode}<br/>
Exception: ${pageContext.exception} <br/>
<br/><br/><br/>
</body>
</html>
