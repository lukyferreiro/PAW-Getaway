<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <title>Activity</title>
        <%@ include file="../components/includes/headers.jsp" %>
    </head>

    <body>
        <div class="container-main">
            <%@ include file="../components/navbar.jsp" %>

            <div class="card card-view">
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

            <%@ include file="../components/footer.jsp" %>
        </div>

        <!-- Bootstrap y Popper -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
    </body>
</html>
