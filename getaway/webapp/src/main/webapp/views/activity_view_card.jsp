<%--
  Created by IntelliJ IDEA.
  User: Agustina
  Date: 8/31/2022
  Time: 7:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value = "/css/styles.css" />" rel="stylesheet" >
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Activity Init View</title>
</head>
<body>
    <div class="card activity-view">
        <img class="card-img-top" src="resources/Adventure-Activities-Destination-Deluxe.jpg" alt="Card image cap">
        <div class="card-img-overlay buttons save">
            <button type="button" class="btn btn-circle btn-xl "><img src="resources/ic_bookmark_white.svg"/></button>
        </div>
        <div class="card-body">
            <h2 class="card-title">Experience Name</h2>
            <p>experience.address</p>
            <p>experience.ranking <img src="resources/ic_star.svg"></p>
        </div>
    </div>
</body>
</html>
