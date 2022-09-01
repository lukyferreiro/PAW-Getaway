<%--
  Created by IntelliJ IDEA.
  User: Agustina
  Date: 8/31/2022
  Time: 8:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="g"  uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <link href="<g:url value = "/resources/css/styles.css" />" rel="stylesheet" >
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Activity Form</title>
</head>
<body>
    <div class="navbar">
        <div class="container-header">
            <a class="logo">
                <img class="logo-img" src="<g:url value = "/resources/images/getaway-icon.png" />"  alt="Logo">
                <!--                    <img class="logo-img" src="./resources/GETAWAY.svg" alt="Logo">-->
                GETAWAY
            </a>
            <div class="container-header-btn">
                <button type="button" class="btn btn-header">Crear experiencia</button>
                <!--                        <button type="button" class="btn btn-header">Iniciar sesion</button>-->
            </div>
        </div>
    </div>
    <div class="form">
        <div class="buttons">
            <button type="button" class="btn btn-circle btn-xl"><img src="<g:url value = "/resources/images/go_back.png" />"/></button>
        </div>

        <div class="card">
            <div>
                <label for="activityName" class="form-label">Nombre</label>
                <input type="email" class="form-control" id="activityName">
            </div>
            <div>
                <label for="activityAddress" class="form-label">Direccion</label>
                <input type="email" class="form-control" id="activityAddress">
            </div>
            <div>
                <label for="exampleFormControlInput1" class="form-label">Email</label>
                <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com">
            </div>
            <div>
                <label for="formFile" class="form-label">Agregar imagen</label>
                <input class="form-control" type="file" id="formFile">
            </div>
            <div>
                <label for="exampleFormControlTextarea1" class="form-label">Descripcion</label>
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
            </div>
            <div>
<%--                <select class="form-select" aria-label="Default select example">--%>
<%--                    --%>
<%--                </select>--%>
                <label for="activityTags" class="form-label">Tags</label>
                <input class="form-control" list="datalistOptions" id="activityTags" placeholder="Escribe para buscar...">
                <datalist id="datalistOptions">
                    <option value="Paracaidismo">
                    <option value="Hoteleria">
                    <option value="Spa">
                    <option value="Comida">
                    <option value="Cerveza">
                </datalist>
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
