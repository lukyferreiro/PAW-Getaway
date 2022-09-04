<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
  <meta charset="UTF-8">
  <title>Aventuras</title>
  <!-- Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
  <link href="<c:url value = "/resources/css/styles.css" />" rel="stylesheet">
  <link href="<c:url value = "/resources/css/activity.css" />" rel="stylesheet">

</head>
<body>
<%@ include file="index.jsp" %>

  <div class="container-body">
<%--      <div class="container-filters border-end">--%>
<%--        <p> Filtros </p>--%>
<%--        <form class="d-flex" role="search">--%>
<%--          <input class="form-control me-2" type="search" placeholder="¿A donde?" aria-label="Search">--%>
<%--          <button class="btn btn-outline-success btn-search" type="submit">Search</button>--%>
<%--        </form>--%>
<%--      </div>--%>

      <div class="container-experiences">
        <div class="row row-cols-1 row-cols-md-2 g-2">
          <c:forEach var="activity" items="${activities}">
            <a href="<c:url value = "${activity.categoryName}/${activity.id}"  />" class="card-container">
            <div class="card activity-view">
              <img class="card-img-top" src="<c:url value = "/resources/images/adventure_image.jpg" />" alt="Card image cap">
              <div class="card-img-overlay buttons save">
                <button type="button" class="btn btn-circle btn-xl ">
                  <img src="<c:url value = "/resources/images/ic_bookmark_white.svg" />"  alt="Guardar"/>
                </button>
              </div>
              <div class="card-body">
                <h2 class="card-title">${activity.name}</h2>
                <p>${activity.address}</p>
                <p> $ ${activity.price}</p>
              </div>
            </div>
            </a>
          </c:forEach>

        </div>
      </div>
  </div>

<%--  <div class="footer">--%>
<%--    Copyright 2022 - Getaway--%>
<%--  </div>--%>

  <!-- Bootstrap y Popper -->
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
</body>
</html>
