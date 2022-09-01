
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Activity</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="<c:url value = "/resources/css/activity.css" />" rel="stylesheet">
    <link href="<c:url value = "/resources/css/navbar.css" />" rel="stylesheet">

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

        <div class="card">
            <h1 class="card-title">Experience Name</h1>
            <div>
                <div class="activity-info">
                    <p>experience.ranking <img src="<c:url value = "/resources/images/ic_star.svg" />" alt="Icono estrella"></p>
                </div>
                <div class="activity-info">
                    <a href="#">experience.link</a>
                </div>
            </div>

            <div class="buttons save">
                <button type="button" class="btn btn-circle btn-xl"><img src="<c:url value = "/resources/images/ic_bookmark_white.svg" />"/></button>
            </div>
            <div class="row">
                <div class="col-auto">
                    <div class="card-body">
                        <img class="rounded" src="<c:url value = "/resources/images/adventure_image.jpg" />" alt="experience.image">
                    </div>
                </div>
                <div class="col">
                    <div>
                        <div class="card-body">
                            <h5 class="card-title"> Direccion </h5>
                            <p>experience.address</p>
                            <h5 class="card-title"> Descripcion </h5>
                            <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                            <h5 class="card-title"> Tags </h5>
                            <p><a href="#">#tag1</a>   <a href="#">#tag2</a></p>
                        </div>
                    </div>
                </div>
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
