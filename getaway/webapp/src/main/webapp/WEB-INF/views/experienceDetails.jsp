<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <title><spring:message code="pageName"/> - <c:out value="${activity.name}"/></title>
        <%@ include file="../components/includes/headers.jsp" %>
    </head>

    <body>
        <div class="container-main">
            <%@ include file="../components/navbar.jsp" %>

            <div class="card mx-5 my-3 p-4">
                <button id="goBackButton" class="btn btn-leave-experience-details d-flex">
                    <img class="go-back-arrow align-self-center" src="<c:url value = "/resources/images/go_back.png"/>" alt="Flecha">
                    <span><spring:message code="experienceDetail.goBack"/></span>
                </button>
                <h1 class="card-title d-flex justify-content-center">
                    <c:out value="${activity.name}"/>
                </h1>
<%--                <div>--%>
<%--                    <div class="d-inline-block">--%>
<%--                        <p>--%>
<%--                            experience.ranking <img src="<c:url value = "/resources/images/ic_star.svg" />" alt="Icono estrella">--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                    <div class="d-inline-block">--%>
<%--                        <a href="#"><c:out value="${activity.siteUrl}"/></a>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="">--%>
<%--                    <button type="button" class="btn btn-bookmark">--%>
<%--                        <img src="<c:url value="/resources/images/ic_bookmark_white.svg"/>" alt="Guardar"/>--%>
<%--                    </button>--%>
<%--                </div>--%>
                <div class="row">
                    <div class="col-auto">
                        <div class="card-body p-2 mw-25">
                            <c:if test="${dbCategoryName == 'adventures'}">
                                <img class="rounded" style="max-width: 400px; max-height: 400px;" src="<c:url value="/resources/images/adventure_image.jpg" />" alt="Imagen Aventura">
                            </c:if>
                            <c:if test="${dbCategoryName == 'gastronomy'}">
                                <img class="rounded" style="max-width: 400px; max-height: 400px;" src="<c:url value="/resources/images/gastronomy_image.jpg" />" alt="Imagen Gastronomia">
                            </c:if>
                            <c:if test="${dbCategoryName == 'hotels'}">
                                <img class="rounded" style="max-width: 400px; max-height: 400px;" src="<c:url value="/resources/images/hotels_image.jpeg" />" alt="Imagen Hoteles">
                            </c:if>
                            <c:if test="${dbCategoryName == 'relax'}">
                                <img class="rounded" style="max-width: 400px; max-height: 400px;" src="<c:url value="/resources/images/relax_image.jpg" />" alt="Imagen Relax">
                            </c:if>
                            <c:if test="${dbCategoryName == 'night'}">
                                <img class="rounded" style="max-width: 400px; max-height: 400px;" src="<c:url value="/resources/images/night_image.jpg" />" alt="Imagen Vida Nocturna">
                            </c:if>
                            <c:if test="${dbCategoryName == 'historic'}">
                                <img class="rounded" style="max-width: 400px; max-height: 400px;" src="<c:url value="/resources/images/historic_image.jpg" />" alt="Imagen Historico">
                            </c:if>
                        </div>
                    </div>
                    <div class="col">
                        <div>
                            <div class="card-body">
                                <h5 class="card-title">
                                    <spring:message code="experienceDetail.address"/>
                                </h5>
                                <p><c:out value="${activity.address}"/></p>
                                <h5 class="card-title">
                                    <spring:message code="experienceDetail.price"/>
                                    <c:if test="${activity.price > 0}">
                                        <p>
                                            <spring:message code="experienceDetail.price.simbol"/>
                                                <c:out value="${activity.price}"/>
                                            <spring:message code="experienceDetail.price.perPerson"/>
                                        </p>
                                    </c:if>
                                    <c:if test="${activity.price == 0}">
                                        <p>
                                            <spring:message code="experienceDetail.price.free"/>
                                        </p>
                                    </c:if>
                                </h5>
                                <h5 class="card-title">
                                    <spring:message code="experienceDetail.description"/>
                                </h5>
                                <p class="card-text">
                                    <c:out value="${activity.description}"/>
                                </p>
                                <h5 class="card-title"> <spring:message code="experienceDetail.url"/> </h5>
                                <a class="card-text" href="<c:url value="${activity.siteUrl}"/>">
                                    <c:out value="${activity.siteUrl}"/>
                                </a>
<%--                                <h5 class="card-title"> <spring:message code="experienceDetail.tags"/> </h5>--%>
<%--                                <p><a href="#">#tag1</a>   <a href="#">#tag2</a></p>--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="../components/footer.jsp" %>
        </div>

        <%@ include file="../components/includes/bottomScripts.jsp" %>
        <script src='<c:url value="/resources/js/experienceDetailsPage.js"/>'></script>
    </body>
</html>
