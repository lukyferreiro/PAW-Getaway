<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
    <body>
        <h2>Hello World!</h2>
        <p> Hello <c:out value="${username}" escapeXml="true"/> </p>
    </body>
</html>
