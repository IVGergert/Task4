<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body{
            font-family: Arial, sans-serif;
            text-align: center;
        }

        table {
            margin: 20px auto;
            border-collapse: collapse;
            width: 80%;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }

        button {
            padding: 5px 10px;
            margin: 2px;
            cursor: pointer;
        }
    </style>
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
            <th>Action</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>

                <td>
                    <c:choose>
                        <c:when test="${user.id == sessionScope.user.id}">
                            <strong><c:out value="${user.role}"/></strong>
                            <span style="color:gray; font-size:0.8em">(You)</span>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="CHANGE_ROLE"/>
                                <input type="hidden" name="userId" value="${user.id}"/>

                                <select name="newRole" onchange="this.form.submit()">
                                    <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
                                    <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                </select>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td><c:out value="${user.status}"/></td>

                <td>
                    <c:if test="${user.role != 'ADMIN'}">

                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <c:choose>
                                <c:when test="${user.status eq 'ACTIVE'}">
                                    <input type="hidden" name="command" value="BAN_USER" />
                                    <button type="submit" onclick="return confirm('Are you sure you want to block the user?');">
                                        Ban
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="command" value="UNBAN_USER"/>
                                    <button type="submit" onclick="return confirm('Are you sure you want to unblock the user?');">
                                        Unban
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>

                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <input type="hidden" name="command" value="DELETE_USER"/>
                            <button type="submit" onclick="return confirm('Are you sure you want to delete the user?')">
                                Delete
                            </button>
                        </form>

                    </c:if>

                    <c:if test="${user.role == 'ADMIN'}">
                        <span style="color: grey">Admin cannot be deleted or blocked.</span>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>