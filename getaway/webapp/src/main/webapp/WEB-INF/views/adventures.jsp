<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
  <head>
    <title>Aventuras</title>
    <%@ include file="../components/includes/headers.jsp" %>
  </head>

  <body>
    <div class="container-main">
      <%@ include file="../components/navbar.jsp" %>

      <div class="container-body">
  <%--        <div class="container-filters border-end">--%>
  <%--          <p> Filtros </p>--%>
  <%--          <form class="d-flex" role="search">--%>
  <%--            <input class="form-control me-2" type="search" placeholder="¿A donde?" aria-label="Search">--%>
  <%--            <button class="btn btn-outline-success btn-search" type="submit">Search</button>--%>
  <%--          </form>--%>
  <%--        </div>--%>

        <div class="container-experiences">
          <div>
            <a href="<c:url value = "/adventures/id" />" class="card-container">
              <div class="card card-experience">
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

      <%@ include file="../components/footer.jsp" %>
    </div>

    <!-- Bootstrap y Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
  </body>
</html>
