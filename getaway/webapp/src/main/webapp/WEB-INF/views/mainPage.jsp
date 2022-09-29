<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>




<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/carousel.css"/>" rel="stylesheet" type="text/css"/>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

<%--         <div class="container-fluid overflow-auto h-100 p-0 my-3 d-flex">--%>
<%--             <c:forEach varStatus="categoryIndex" var="categoryList" items="${listByCategory}">--%>
<%--                 <div id="carouselExampleIndicators<c:out value="${categoryIndex.index}"/> " class="carousel slide" data-bs-ride="carousel">--%>
<%--                     <div class="carousel-indicators">--%>
<%--                         <c:forEach var = "i" begin = "0" end = "${categoryList.size() }">--%>
<%--                             <button type="button" data-bs-target="#carouselExampleIndicators<c:out value="${categoryIndex.index}"/>" data-bs-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>  aria-current="true"></button>--%>
<%--                         </c:forEach>--%>
<%--                     </div>--%>
<%--                     <div class="carousel-inner">--%>
<%--                         <c:forEach var="experience" varStatus="myIndex" items="${categoryList}">--%>
<%--                             <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">--%>
<%--                                 <jsp:include page="/WEB-INF/components/card_experience.jsp">--%>
<%--                                     <jsp:param name="hasImage" value="${experience.hasImage}"/>--%>
<%--                                     <jsp:param name="categoryName" value="${experience.categoryName}"/>--%>
<%--                                     <jsp:param name="id" value="${experience.experienceId}"/>--%>
<%--                                     <jsp:param name="name" value="${experience.experienceName}"/>--%>
<%--                                     <jsp:param name="description" value="${experience.description}"/>--%>
<%--                                     <jsp:param name="address" value="${experience.address}"/>--%>
<%--                                     <jsp:param name="price" value="${experience.price}"/>--%>
<%--                                     <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>--%>
<%--                                     <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>--%>
<%--                                     <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>--%>
<%--                                     <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>--%>
<%--                                 </jsp:include>--%>
<%--                             </div>--%>
<%--                         </c:forEach>--%>

<%--                         <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators<c:out value="${categoryIndex.index}"/>" data-bs-slide="prev">--%>
<%--                             <span class="carousel-control-prev-icon" aria-hidden="true"></span>--%>
<%--                             <span class="visually-hidden">Previous</span>--%>
<%--                         </button>--%>
<%--                         <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators<c:out value="${categoryIndex.index}"/>" data-bs-slide="next">--%>
<%--                             <span class="carousel-control-next-icon" aria-hidden="true"></span>--%>
<%--                             <span class="visually-hidden">Next</span>--%>
<%--                         </button>--%>
<%--                     </div>--%>

<%--                 </c:forEach>--%>



             <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                 <c:if test="${listAdventure.size() != 0}"><h2><spring:message code="navbar.filter.adventure"/> </h2></c:if>
                 <div class="carousel-indicators">
                     <c:forEach var = "i" begin = "0" end = "${listAdventure.size()}">
                        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>  aria-current="true"></button>
                     </c:forEach>
                 </div>
                 <div class="carousel-inner">
                    <c:forEach var="experience" varStatus="myIndex" items="${listAdventure}">
                        <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">
                            <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                <jsp:param name="id" value="${experience.experienceId}"/>
                                <jsp:param name="name" value="${experience.experienceName}"/>
                                <jsp:param name="description" value="${experience.description}"/>
                                <jsp:param name="address" value="${experience.address}"/>
                                <jsp:param name="price" value="${experience.price}"/>
                                <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                                <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                                <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                            </jsp:include>
                        </div>
                    </c:forEach>

                     <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                         <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                         <span class="visually-hidden">Previous</span>
                     </button>
                     <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                         <span class="carousel-control-next-icon" aria-hidden="true"></span>
                         <span class="visually-hidden">Next</span>
                     </button>
                 </div>
             </div>

             <div id="carouselExampleIndicators2" class="carousel slide" data-bs-ride="carousel">
                 <c:if test="${listGastronomy.size() != 0}"><h2><spring:message code="navbar.filter.gastronomy"/> </h2></c:if>
                 <div class="carousel-indicators">
                     <c:forEach var = "i" begin = "0" end = "${listGastronomy.size()}">
                         <button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>  aria-current="true"></button>
                     </c:forEach>
                 </div>
                 <div class="carousel-inner">
                     <c:forEach var="experience" varStatus="myIndex" items="${listGastronomy}">
                         <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">
                             <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                 <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                 <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                 <jsp:param name="id" value="${experience.experienceId}"/>
                                 <jsp:param name="name" value="${experience.experienceName}"/>
                                 <jsp:param name="description" value="${experience.description}"/>
                                 <jsp:param name="address" value="${experience.address}"/>
                                 <jsp:param name="price" value="${experience.price}"/>
                                 <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                 <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                                 <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                                 <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                             </jsp:include>
                         </div>
                     </c:forEach>
                     <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide="prev">
                         <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                         <span class="visually-hidden">Previous</span>
                     </button>
                     <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide="next">
                         <span class="carousel-control-next-icon" aria-hidden="true"></span>
                         <span class="visually-hidden">Next</span>
                     </button>
                 </div>
             </div>

            <div id="carouselExampleIndicators3" class="carousel slide" data-bs-ride="carousel">
                <c:if test="${listHotel.size() != 0}"><h2><spring:message code="navbar.filter.hotels"/> </h2></c:if>

                <div class="carousel-indicators">
                    <c:forEach var = "i" begin = "0" end = "${listHotel.size()}">
                        <button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>  aria-current="true"></button>
                         </c:forEach>
                     </div>
                     <div class="carousel-inner">
                         <c:forEach var="experience" varStatus="myIndex" items="${listHotel}">
                             <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">
                                 <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                     <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                     <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                     <jsp:param name="id" value="${experience.experienceId}"/>
                                     <jsp:param name="name" value="${experience.experienceName}"/>
                                     <jsp:param name="description" value="${experience.description}"/>
                                     <jsp:param name="address" value="${experience.address}"/>
                                     <jsp:param name="price" value="${experience.price}"/>
                                     <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                     <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                                     <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                                     <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                                 </jsp:include>
                             </div>
                         </c:forEach>
                         <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators3" data-bs-slide="prev">
                             <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                             <span class="visually-hidden">Previous</span>
                         </button>
                         <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators3" data-bs-slide="next">
                             <span class="carousel-control-next-icon" aria-hidden="true"></span>
                             <span class="visually-hidden">Next</span>
                         </button>
                     </div>
                 </div>

            <div id="carouselExampleIndicators4" class="carousel slide" data-bs-ride="carousel">
                <c:if test="${listRelax.size() != 0}"><h2><spring:message code="navbar.filter.relax"/> </h2></c:if>

                <div class="carousel-indicators">
                    <c:forEach var = "i" begin = "0" end = "${listRelax.size()}">
                        <button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>  aria-current="true"></button>
                    </c:forEach>
                </div>
                <div class="carousel-inner">
                    <c:forEach var="experience" varStatus="myIndex" items="${listRelax}">
                        <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">
                            <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                <jsp:param name="id" value="${experience.experienceId}"/>
                                <jsp:param name="name" value="${experience.experienceName}"/>
                                <jsp:param name="description" value="${experience.description}"/>
                                <jsp:param name="address" value="${experience.address}"/>
                                <jsp:param name="price" value="${experience.price}"/>
                                <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                                <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                                <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                            </jsp:include>
                        </div>
                    </c:forEach>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators4" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators4" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            <div id="carouselExampleIndicators5" class="carousel slide" data-bs-ride="carousel">
                <c:if test="${listNight.size() != 0}"><h2><spring:message code="navbar.filter.night"/> </h2></c:if>

                <div class="carousel-indicators">
                    <c:forEach var = "i" begin = "0" end = "${listNight.size()}">
                        <button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>  aria-current="true"></button>
                    </c:forEach>
                </div>
                <div class="carousel-inner">
                    <c:forEach var="experience" varStatus="myIndex" items="${listNight}">
                        <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">
                            <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                <jsp:param name="id" value="${experience.experienceId}"/>
                                <jsp:param name="name" value="${experience.experienceName}"/>
                                <jsp:param name="description" value="${experience.description}"/>
                                <jsp:param name="address" value="${experience.address}"/>
                                <jsp:param name="price" value="${experience.price}"/>
                                <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                                <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                                <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                            </jsp:include>
                        </div>
                    </c:forEach>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators5" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators5" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>

            <div id="carouselExampleIndicators6" class="carousel slide" data-bs-ride="carousel">
                <c:if test="${listHistoric.size() != 0}"><h2><spring:message code="navbar.filter.historic"/> </h2></c:if>

                <div class="carousel-indicators">
                    <c:forEach var = "i" begin = "0" end = "${listHistoric.size()}">
                        <button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>  aria-current="true"></button>
                    </c:forEach>
                </div>
                <div class="carousel-inner">
                    <c:forEach var="experience" varStatus="myIndex" items="${listHistoric}">
                        <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">
                            <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                <jsp:param name="id" value="${experience.experienceId}"/>
                                <jsp:param name="name" value="${experience.experienceName}"/>
                                <jsp:param name="description" value="${experience.description}"/>
                                <jsp:param name="address" value="${experience.address}"/>
                                <jsp:param name="price" value="${experience.price}"/>
                                <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                                <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                                <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                            </jsp:include>
                        </div>
                    </c:forEach>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators6" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators6" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
<%--         </div>--%>


    <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>

   </body>
</html>
