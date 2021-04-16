<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>coffee management</title>
</head>
<body>

<h1>coffee list</h1>

<table>
    <tr>
        <th>name</th>
        <th>createBy</th>
        <th>price</th>
        <th>remark</th>
        <th>操作</th>
    </tr>
    <c:forEach var="coffee" items="${coffeeList}">
        <tr>
            <td>${coffee.name}</td>
            <td>${coffee.createBy}</td>
            <td>${coffee.price}</td>
            <td>${coffee.remark}</td>
            <td>
                <a href="${BASE}/coffee_edit?id=${coffee.id}">edit</a>
                <a href="${BASE}/coffee_delete?id=${coffee.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>