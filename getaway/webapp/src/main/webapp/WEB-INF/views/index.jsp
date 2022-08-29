<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <%--CSS--%>
    <link rel="stylesheet" href='<c:url value="/css/styles.css"/>'>
    <%--Bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
</head>
    <body>
        <h2>Hello World!</h2>
        <p> Hello <c:out value="${username}" escapeXml="true"/> </p>
    </body>
</html>
