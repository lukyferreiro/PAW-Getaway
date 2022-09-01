<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Activity Init View</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="<c:url value = "/resources/css/styles.css" />" rel="stylesheet">
</head>
<body>
    <div class="card activity-view">
        <img class="card-img-top" src="<c:url value = "/resources/images/adventure_image.jpg" />" alt="Card image cap">
        <div class="card-img-overlay buttons save">
            <button type="button" class="btn btn-circle btn-xl ">
                <img src="<c:url value = "/resources/images/ic_bookmark_white.svg" />"  alt="Guardar"/>
            </button>
        </div>
        <div class="card-body">
            <h2 class="card-title">Experience Name</h2>
            <p>experience.address</p>
            <p>experience.ranking <img src="<c:url value = "/resources/images/ic_star.svg" />"  alt="Icono estrella"></p>
        </div>
    </div>
</body>
</html>
