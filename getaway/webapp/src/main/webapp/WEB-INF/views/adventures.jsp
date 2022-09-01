<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
  <meta charset="UTF-8">
  <title>Aventuras</title>
  <!-- Bootstrap -->
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

  <div class="container-body">
<%--      <div class="container-filters border-end">--%>
<%--        <p> Filtros </p>--%>
<%--        <form class="d-flex" role="search">--%>
<%--          <input class="form-control me-2" type="search" placeholder="¿A donde?" aria-label="Search">--%>
<%--          <button class="btn btn-outline-success btn-search" type="submit">Search</button>--%>
<%--        </form>--%>
<%--      </div>--%>

      <div class="container-experiences">
        <div>
          <a href="<c:url value = "/adventures/id" />" class="card-container">
            <div class="card">
              <button type="button" class="btn btn-outline-dark btn-bookmark rounded-circle">
                <img src="<c:url value = "/resources/images/ic_bookmark_white.svg"/>" alt="Bookmark" class="btn-bookmark-img">
              </button>
              <img src="<c:url value = "/resources/images/ic_museum.svg"/>" class="card-img-top img-experience" alt="Imagen experiencia">
              <div class="card-body">
                <h5 class="card-title">Aventura 1</h5>
  <!--              <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>-->
                <div class="rate">
                  <img src="<c:url value = "/resources/images/ic_star.svg"/>" alt="Star" class="rate-ic">
                  <p class="rate-value">4.5</p>
                </div>
                <p>Desde $15000 por persona</p>
              </div>
            </div>
          </a>
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
