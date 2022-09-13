<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>Login</title>
    <%@ include file="../components/includes/headers.jsp" %>
</head>
<body>
    <c:url value="/login" var="loginUrl" />
<%--    <form action="<c:url value="${loginUrl}"/>" method="post" enctype="application/x-www-form-urlencoded">--%>
    <form action="<c:url value="/login"/>" method="post" enctype="application/x-www-form-urlencoded">
        <div>
            <label for="username">Username: </label>
            <input id="username" name="email" type="text"/>
        </div>
        <div>
            <label for="password">Password: </label>
            <input id="password" name="password" type="password"/>
        </div>
        <div>
            <label><input name="rememberme" type="checkbox"/> Recordarme</label>
        </div>
        <div>
            <button type="submit" value="Login!"></button>
        </div>
    </form>
</body>
</html>