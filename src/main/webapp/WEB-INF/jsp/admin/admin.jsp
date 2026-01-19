<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/controller?command=LOGOUT">Logout</a>

    <c:if test="${empty users}">
        <p>No users found in the database</p>
    </c:if>

    <c:if test="${not empty users}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Role</th>
                <th>Status</th>
            </tr>
            </thead>

            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.role}"/></td>
                        <td><c:out value="${user.status}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
