<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <title><spring:message code="pageName"/> - ${activity.name}</title>
        <%@ include file="../components/includes/headers.jsp" %>
    </head>

    <body>
        <div class="container-main">
            <%@ include file="../components/navbar.jsp" %>

            <div class="card mx-5 my-3 p-4">
                <h1 class="card-title d-flex justify-content-center">
                    ${activity.name}
                </h1>
<%--                <div>--%>
<%--                    <div class="d-inline-block">--%>
<%--                        <p>--%>
<%--                            experience.ranking <img src="<c:url value = "/resources/images/ic_star.svg" />" alt="Icono estrella">--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                    <div class="d-inline-block">--%>
<%--                        <a href="#">${activity.siteUrl}</a>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="">--%>
<%--                    <button type="button" class="btn btn-bookmark">--%>
<%--                        <img src="<c:url value="/resources/images/ic_bookmark_white.svg"/>" alt="Guardar"/>--%>
<%--                    </button>--%>
<%--                </div>--%>
                <div class="row">
                    <div class="col-auto">
                        <div class="card-body">
                            <img class="rounded" src="<c:url value="/resources/images/adventure_image.jpg" />" alt="experience.image">
                        </div>
                    </div>
                    <div class="col">
                        <div>
                            <div class="card-body">
                                <h5 class="card-title">
                                    <spring:message code="experienceDetail.address"/>
                                </h5>
                                <p>${activity.address}</p>
                                <h5 class="card-title">
                                    <spring:message code="experienceDetail.price"/>
                                    <c:if test="${activity.price > 0}">
                                        <p>
                                            <spring:message code="experienceDetail.price.simbol"/>
                                                ${activity.price}
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
                                <p class="card-text">${activity.description}</p>
                                <h5 class="card-title"> <spring:message code="experienceDetail.url"/> </h5>
                                <a class="card-text" href="<c:url value="${activity.siteUrl}"/>">${activity.siteUrl}</a>
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
    </body>
</html>
