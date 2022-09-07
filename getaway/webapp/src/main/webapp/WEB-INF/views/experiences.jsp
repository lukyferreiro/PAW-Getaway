<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="pageName"/> - <c:url value="${categoryName}"/></title>
    <%@ include file="../components/includes/headers.jsp" %>
</head>

<body>
<div class="container-main">
    <%@ include file="../components/navbar.jsp" %>

      <div class="container-body container-fluid p-0 d-flex">
<%--        <div class="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">--%>
<%--          <p class="font-weight-bold">--%>
<%--              <spring:message code="filters.title"/>--%>
<%--          </p>--%>
<%--          <form class="d-flex" role="search">--%>
<%--            <input class="form-control me-2" type="search" placeholder="¿A donde?" aria-label="Search">--%>
<%--            <button class="btn btn-outline-success btn-search" type="submit">--%>
<%--                <spring:message code="filters.place.submit"/>--%>
<%--            </button>--%>
<%--          </form>--%>
<%--        </div>--%>

        <div class="container-experiences container-fluid p-0 mx-2 my-0 d-flex flex-wrap justify-content-center">
            <c:forEach var="activity" items="${activities}">
              <div class="card card-experience w-25 mx-3 my-2 p-0">
                <a class="card-link" href="<c:url value="${activity.categoryName}/${activity.id}"/>">
                  <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/adventure_image.jpg"/>" alt="Imagen de la experiencia">
    <%--                  <div class="">--%>
    <%--                    <button type="button" class="btn btn-bookmark">--%>
    <%--                      <img src="<c:url value="/resources/images/ic_bookmark_white.svg"/>" alt="Guardar"/>--%>
    <%--                    </button>--%>
    <%--                  </div>--%>
                  <div class="card-body container-fluid p-2">
                    <h2 class="card-title container-fluid p-0"><c:out value="${activity.name}"/></h2>
                    <div class="card-text container-fluid p-0">
                        <p><c:out value="${activity.description}"/></p>
                        <h5><c:out value="${activity.address}"/></h5>
                        <c:if test="${activity.price > 0}">
                            <h6>
                                <spring:message code="experience.price.simbol"/>
                                <c:out value="${activity.price}"/>
                                <spring:message code="experience.price.perPerson"/>
                            </h6>
                        </c:if>
                        <c:if test="${activity.price == 0}">
                            <h6>
                                <spring:message code="experience.price.free"/>
                            </h6>
                        </c:if>
                    </div>
                  </div>
                </a>
              </div>
            </c:forEach>
        </div>
      </div>

      <%@ include file="../components/footer.jsp" %>
    </div>

    <%@ include file="../components/includes/bottomScripts.jsp" %>
  </body>
</html>
