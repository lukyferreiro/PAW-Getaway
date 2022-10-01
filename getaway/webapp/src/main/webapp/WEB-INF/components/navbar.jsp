<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="navbar container-fluid p-0 d-flex flex-column">
    <div class="container-fluid px-2 pt-2 d-flex">
        <a href="<c:url value = "/"/>" class="logo d-flex">
            <img class="logo-img" src="<c:url value = "/resources/images/getaway-icon.png"/>" alt="Logo">
            <span class="logo-text align-self-center text-uppercase font-weight-bold">
                <spring:message code="pageName"/>
         </span>
        </a>
        <%--      <a href="<c:url value = "/"/>" class="link-home" >--%>
        <%--         <spring:message code="navbar.home"/>--%>
        <%--      </a>--%>

        <div>
            <c:url value="/search_result" var="postPath"/>
            <form:form modelAttribute="searchForm" action="${postPath}" id="searchExperienceForm" method="post"
                       acceptCharset="UTF-8">
                <form:input path="query" type="text" class="form-control" cssErrorClass="form-control is-invalid"/>
                <form:errors path="query" element="p" cssClass="form-error-label"/>
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Busca</button>
            </form:form>
        </div>


        <div class="container-navbar-buttons d-flex justify-content-between">
            <c:if test="${loggedUser.hasRole('VERIFIED')}">
                <a href="<c:url value = "/create_experience"/>" style="margin-right: 40px;">
                    <button type="button" class="btn button-primary">
                        <spring:message code="navbar.createExperience"/>
                    </button>
                </a>
            </c:if>
            <c:choose>
                <c:when test="${loggedUser == null}">
                    <a href="<c:url value = "/login"/>">
                        <button type="button" class="btn button-primary">
                            <spring:message code="navbar.login"/>
                        </button>
                    </a>
                </c:when>
                <c:otherwise>
                    <div class="dropdown">
                        <button class="btn button-primary dropdown-toggle" type="button" id="dropdownMenuButton1"
                                data-bs-toggle="dropdown" aria-expanded="false">
                                <%--                     <c:choose>--%>
                                <%--                        <c:when test="${loggedUser.hasImage}">--%>
                                <%--                           <img class="container-fluid p-0" style="height: 60px; width: 60px" src="<c:url value='/user/profileImage/${loggedUser.profileImageId}'/>" alt="Imagen"/>--%>
                                <%--                        </c:when>--%>
                                <%--                        <c:otherwise>--%>
                                <%--                           <img class="container-fluid p-0" style="height: 60px; width: 60px" alt="Imagen ${loggedUser.email}"--%>
                                <%--                                src="<c:url value="/resources/images/ic_user.png" />"--%>
                                <%--                        </c:otherwise>--%>
                                <%--                     </c:choose>--%>
                            <spring:message code="navbar.user"/>
                        </button>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <a class="dropdown-item" href="<c:url value="/user/profile"/>">
                                <spring:message code="navbar.profile"/>
                            </a>
                            <a class="dropdown-item" href="<c:url value="/user/experiences"/>">
                                <spring:message code="navbar.experiencies"/>
                            </a>
                            <a class="dropdown-item" href="<c:url value="/user/favourites"/>">
                                <spring:message code="navbar.favourites"/>
                            </a>
                            <a class="dropdown-item" href="<c:url value="/user/reviews"/>">
                                <spring:message code="navbar.reviews"/>
                            </a>
                            <a class="dropdown-item" href="<c:url value="/logout"/>">
                                <spring:message code="navbar.logout"/>
                            </a>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">
        <a href="<c:url value="/experiences/Aventura"/>">
            <button type="button" class="btn btn-category">
                <img src="<c:url value="/resources/images/ic_adventure.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.adventure"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Gastronomia"/>">
            <button type="button" class="btn btn-category">
                <img src="<c:url value="/resources/images/ic_food.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.gastronomy"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Hoteleria"/>">
            <button type="button" class="btn btn-category">
                <img src="<c:url value="/resources/images/ic_hotel.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.hotels"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Relax"/>">
            <button type="button" class="btn btn-category">
                <img src="<c:url value="/resources/images/ic_relax.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.relax"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Vida_nocturna"/>">
            <button type="button" class="btn btn-category">
                <img src="<c:url value="/resources/images/ic_nightlife.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.night"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Historico"/>">
            <button type="button" class="btn btn-category">
                <img src="<c:url value="/resources/images/ic_museum.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.historic"/>
            </button>
        </a>
    </div>
</div>

<hr class="separator"/>

