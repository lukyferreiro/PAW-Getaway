<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <title>GetAway</title>
    <%--Bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="<c:url value = "/resources/css/styles.css" />" rel="stylesheet">
</head>
<body>
    <div class="navbar">
        <div class="container-header">
            <a href="<c:url value = "/" />" class="logo">
                <img class="logo-img" src="<c:url value = "/resources/images/getaway-icon.png" />"  alt="Logo">
                <!--                    <img class="logo-img" src="./resources/GETAWAY.svg" alt="Logo">-->
                GETAWAY
            </a>
            <div class="container-header-btn">
                <button type="button" class="btn btn-header">Crear experiencia</button>
                <!--                        <button type="button" class="btn btn-header">Iniciar sesion</button>-->
            </div>
        </div>

        <div class="container-types">
            <a href="<c:url value = "/adventures"/>" class="link-btn-filter">
                <button type="button" class="btn btn-filter">
                    <img src="<c:url value = "/resources/images/ic_adventure.svg" />" alt="Logo aventura"/>Aventura
                </button>
            </a>
            <a href="<c:url value = "/adventures"/>" class="link-btn-filter">
                <button type="button" class="btn btn-filter">
                    <img src="<c:url value = "/resources/images/ic_food.svg" />" alt="Logo aventura"/>Gastronomia
                </button>
            </a>
            <a href="<c:url value = "/adventures"/>" class="link-btn-filter">
                <button type="button" class="btn btn-filter">
                    <img src="<c:url value = "/resources/images/ic_hotel.svg" />" alt="Logo aventura"/>Hoteleria
                </button>
            </a>
            <a href="<c:url value = "/adventures"/>" class="link-btn-filter">
                <button type="button" class="btn btn-filter">
                    <img src="<c:url value = "/resources/images/ic_relax.svg" />" alt="Logo aventura"/>Relax
                </button>
            </a>
            <a href="<c:url value = "/adventures"/>" class="link-btn-filter">
                <button type="button" class="btn btn-filter">
                    <img src="<c:url value = "/resources/images/ic_nightlife.svg" />" alt="Logo aventura"/>Vida nocturna
                </button>
            </a>
            <a href="<c:url value = "/adventures"/>" class="link-btn-filter">
                <button type="button" class="btn btn-filter">
                    <img src="<c:url value = "/resources/images/ic_museum.svg" />" alt="Logo aventura"/>Historico
                </button>
            </a>
        </div>
    </div>

    <hr class="separator"/>

    <div class="container-body-mainpage">
        <div>
            Haz click en algunos de los filtros para buscar por dicho tipo de experiencia
        </div>
    </div>

    <div class="footer">
        Copyright 2022 - Getaway
    </div>


    <!-- Bootstrap y Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
</body>
</html>
