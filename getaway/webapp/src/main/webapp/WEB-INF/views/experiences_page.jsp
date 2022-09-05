<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
  <head>
    <title><spring:message code="pageName"/> - <spring:message code="experiencies.title"/></title>
    <%@ include file="../components/includes/headers.jsp" %>
  </head>

  <body>
    <div class="container-main">
      <%@ include file="../components/navbar.jsp" %>

      <div class="container-body container-fluid p-0 d-flex">
        <div class="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
          <p class="font-weight-bold"> Filtros </p>
          <form class="d-flex" role="search">
            <input class="form-control me-2" type="search" placeholder="¿A donde?" aria-label="Search">
            <button class="btn btn-outline-success btn-search" type="submit">Search</button>
          </form>
        </div>

        <div class="container-experiences container-fluid p-0 mx-2 my-0 d-flex flex-wrap justify-content-center">
<%--          <div class="row row-cols-1 row-cols-md-2 g-2">--%>
            <c:forEach var="activity" items="${activities}">
              <div class="card card-experience w-25 m-2 p-0">
                <a class="card-link" href="<c:url value="${activity.categoryName}/${activity.id}"/>">
                  <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/adventure_image.jpg"/>" alt="Imagen de la experiencia">
                  <div class="">
                    <button type="button" class="btn btn-bookmark">
                      <img src="<c:url value="/resources/images/ic_bookmark_white.svg"/>" alt="Guardar"/>
                    </button>
                  </div>
                  <div class="card-body container-fluid p-2">
                    <h2 class="card-title container-fluid p-0">${activity.name}</h2>
                    <div class="card-text container-fluid p-0">
                        <p>${activity.description}</p>
                        <h5>${activity.address}</h5>
                        <c:if test="${activity.price > 0}">
                            <h6>$ ${activity.price} por persona</h6>
                        </c:if>
                        <c:if test="${activity.price == 0}">
                            <h6>Gratis</h6>
                        </c:if>
                    </div>
                  </div>
                </a>
              </div>
            </c:forEach>
<%--          </div>--%>
        </div>
      </div>

      <%@ include file="../components/footer.jsp" %>
    </div>

    <!-- Bootstrap y Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
  </body>
</html>
