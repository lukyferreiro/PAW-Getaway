<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>GetAway</title>
    <%--Bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="<c:url value = "/resources/css/styles.css" />" rel="stylesheet">
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
                <button type="button" class="btn btn-header" data-bs-toggle="modal" data-bs-target="#createExperience">Crear experiencia</button>
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


<%-- MODAL CREATE EXPERIENCE FORM --%>
    <div class="modal fade" id="createExperience" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="createExperienceLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Crea tu experiencia</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <c:url value="/create" var="postUrl"/>
                    <form:form modelAttribute="activityForm" action="${postUrl}" method="post">
                        <div>
                            <form:errors path="activityName" element="p" cssStyle="color: red"/>
                            <from:label path="activityName" class="form-label">Nombre</from:label>
                            <from:input type="email" class="form-control" path="activityName"/>
                        </div>
                        <div>
                            <form:errors path="activityCategory" element="p" cssStyle="color: red"/>
                            <from:label path="activityCategory" class="form-label">Tags</from:label>
                            <from:input class="form-control" list="datalistOptions" path="activityCategory" placeholder="Escribe para buscar..."/>
                            <datalist id="categoryOptions">
                                <option value="Aventura">
                                <option value="Gastronomia">
                                <option value="Hoteleria">
                                <option value="Relax">
                                <option value="Vida nocturna">
                                <option value="Historico">
                            </datalist>
                        </div>
                        <div>
                            <form:errors path="activityAddress" element="p" cssStyle="color: red"/>
                            <from:label path="activityAddress" class="form-label">Direccion</from:label>
                            <from:input type="email" class="form-control" path="activityAddress"/>
                        </div>
                        <div>
                            <form:errors path="activityMail" element="p" cssStyle="color: red"/>
                            <from:label path="activityMail" class="form-label">Email</from:label>
                            <from:input type="email" class="form-control" path="activityMail" placeholder="name@example.com"/>
                        </div>
                        <div>
                            <form:errors path="activityImg" element="p" cssStyle="color: red"/>
                            <from:label path="activityImg" class="form-label">Agregar imagen</from:label>
                            <from:input class="form-control" type="file" path="activityImg"/>
                        </div>
                        <div>
                            <form:errors path="activityInfo" element="p" cssStyle="color: red"/>
                            <from:label path="activityInfo" class="form-label">Descripcion</from:label>
                            <from:textarea class="form-control" path="activityInfo" rows="3"></from:textarea>
                        </div>
                        <div>
                            <form:errors path="activityTags" element="p" cssStyle="color: red"/>
                            <from:label path="activityTags" class="form-label">Tags</from:label>
                            <from:input class="form-control" list="datalistOptions" path="activityTags" placeholder="Escribe para buscar..."/>
                            <datalist id="datalistOptions">
                                <option value="Paracaidismo">
                                <option value="Hoteleria">
                                <option value="Spa">
                                <option value="Comida">
                                <option value="Cerveza">
                            </datalist>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-header" type="submit" value="Guardar"></input>
                        </div>
                        </form:form>
                    </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
//                    <button type="button" class="btn btn-header">Guardar</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap y Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
</body>
</html>
