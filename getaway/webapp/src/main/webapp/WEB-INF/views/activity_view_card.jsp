<%--
  Created by IntelliJ IDEA.
  User: Agustina
  Date: 8/31/2022
  Time: 7:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="e"  uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <link href="<e:url value = "/resources/css/styles.css" />" rel="stylesheet" >
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Activity Init View</title>
</head>
<body>
    <div class="card activity-view">
        <img class="card-img-top" src="<e:url value = "/resources/images/adventure_image.jpg" />" alt="Card image cap">
        <div class="card-img-overlay buttons save">
            <button type="button" class="btn btn-circle btn-xl "><img src="<e:url value = "/resources/images/ic_bookmark_white.svg" />" /></button>
        </div>
        <div class="card-body">
            <h2 class="card-title">Experience Name</h2>
            <p>experience.address</p>
            <p>experience.ranking <img src="<e:url value = "/resources/images/ic_star.svg" />" ></p>
        </div>
    </div>
</body>
</html>
