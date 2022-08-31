<%--
  Created by IntelliJ IDEA.
  User: Agustina
  Date: 8/31/2022
  Time: 7:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value = "/css/styles.css" />" rel="stylesheet" >
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Activity</title>
</head>
<body>
    <div class="card">
        <h1 class="card-title">Experience Name</h1>
        <div>
            <div class="activity-info">
                <p>experience.ranking <img src="resources/ic_star.svg"></p>
            </div>
            <div class="activity-info">
                <a href="#">experience.link</a>
            </div>
        </div>

        <div class="buttons save">
            <button type="button" class="btn btn-circle btn-xl"><img src="resources/ic_bookmark_white.svg"/></button>
        </div>
        <div class="row">
            <div class="col-auto">
                <div class="card-body">
                    <img class="rounded" src="resources/Adventure-Activities-Destination-Deluxe.jpg" alt="experience.image">
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

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
</body>
</html>
