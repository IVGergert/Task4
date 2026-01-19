<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>500</title>
</head>
<body>
<h2>500 - Internal Server Error</h2>

java.lang.Throwable
Request from: ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode}<br/>
Exception: ${pageContext.exception} <br/>
<br/><br/><br/>

<p>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_login">Go to login</a>
</p>
</body>
</html>
