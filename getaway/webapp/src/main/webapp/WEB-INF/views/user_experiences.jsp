<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="experience.title"/></title>
      <link href='<c:url value="/resources/css/user_experiences.css"/>' rel="stylesheet">
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <c:choose>
            <c:when test="${experiences.size() == 0}">
                  <h2 class="title"><spring:message code="experience.notExist"/></h2>
            </c:when>
            <c:otherwise>
               <div>
                  <h2 class="title"><spring:message code="experience.title"/></h2>
                  <jsp:include page="/WEB-INF/views/order_dropdown.jsp">
                     <jsp:param name="path1" value="/user/experiences?orderBy=rankingAsc"/>
                     <jsp:param name="path2" value="/user/experiences?orderBy=rankingDesc"/>
                     <jsp:param name="path3" value="/user/experiences?orderBy=A-Z"/>
                     <jsp:param name="path4" value="/user/experiences?orderBy=Z-A"/>
                     <jsp:param name="path5" value="/user/experiences?orderBy=priceDesc"/>
                     <jsp:param name="path6" value="/user/experiences?orderBy=priceAsc"/>
                  </jsp:include>
<%--                  <div class="dropdown">--%>
<%--                     <button class="btn btn-header dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">--%>
<%--                        <spring:message code="order.title"/>--%>
<%--                     </button>--%>
<%--                     <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">--%>
<%--                        <a  class="dropdown-item" href="<c:url value = "/user/experiences?orderBy=rankingAsc"/>">--%>
<%--                           <spring:message code="order.rankingAsc"/>--%>
<%--                        </a>--%>
<%--                        <a  class="dropdown-item" href="<c:url value = "/user/experiences?orderBy=rankingDesc"/>">--%>
<%--                           <spring:message code="order.rankingDesc"/>--%>
<%--                        </a>--%>
<%--                        <a  class="dropdown-item" href="<c:url value = "/user/experiences?orderBy=A-Z"/>">--%>
<%--                           <spring:message code="order.A_Z"/>--%>
<%--                        </a>--%>
<%--                        <a  class="dropdown-item" href="<c:url value = "/user/experiences?orderBy=Z-A"/>">--%>
<%--                           <spring:message code="order.Z_A"/>--%>
<%--                        </a>--%>
<%--                        <a  class="dropdown-item" href="<c:url value = "/user/experiences?orderBy=priceDesc"/>">--%>
<%--                           <spring:message code="order.low_price"/>--%>
<%--                        </a>--%>
<%--                        <a  class="dropdown-item" href="<c:url value = "/user/experiences?orderBy=priceAsc"/>">--%>
<%--                           <spring:message code="order.high_price"/>--%>
<%--                        </a>--%>
<%--                     </ul>--%>
<%--                  </div>--%>

               </div>
               <div class="cards container-experiences container-fluid">
                  <c:forEach var="experience" varStatus="myIndex" items="${experiences}">
                     <div class="card card-experience mx-3 my-2 p-0">
                        <div>
                           <c:set var = "fav" value = "${false}"/>
                           <c:forEach var="favExperience" items="${favExperienceModels}">
                              <c:if test="${favExperience == experience.id}">
                                 <c:set var = "fav"  value = "${true}"/>
                              </c:if>
                           </c:forEach>

                           <c:choose>
                              <c:when test="${fav}">
                                 <a href="<c:url value = "/user/experiences?experience=${experience.id}&set=${false}"/>">
                                    <button type="button" class="btn" id="setFalse">
                                       <i class="fas fa-heart heart-color"></i>
                                    </button>
                                 </a>
                              </c:when>
                              <c:otherwise>
                                 <a href="<c:url value = "/user/experiences?experience=${experience.id}&set=${true}"/>">
                                    <button type="button" class="btn" id="setTrue">
                                       <i class="fas fa-heart"></i>
                                    </button>
                                 </a>
                              </c:otherwise>
                           </c:choose>

                        </div>
                        <a class="card-link"
                           href="<c:url value="/experiences/${experience.categoryName}/${experience.id}"/>">
                           <jsp:include page="/WEB-INF/views/card_experience.jsp">
                              <jsp:param name="hasImage" value="${experience.hasImage}"/>
                              <jsp:param name="categoryName" value="${experience.categoryName}"/>
                              <jsp:param name="id" value="${experience.id}"/>
                              <jsp:param name="name" value="${experience.name}"/>
                              <jsp:param name="description" value="${experience.description}"/>
                              <jsp:param name="address" value="${experience.address}"/>
                              <jsp:param name="price" value="${experience.price}"/>
                              <jsp:param name="myIndex" value="${myIndex.index}"/>
                           </jsp:include>
                           <div class="card-body container-fluid p-2">
                              <jsp:include page="/WEB-INF/views/star_avg.jsp">
                                 <jsp:param name="avgReview" value="${avgReviews[myIndex.index]}"/>
                              </jsp:include>
                           </div>
                        </a>
                        <div class="btn-group" role="group">
                           <a href="<c:url value="/user/experiences/edit/${experience.id}"/>" class="btn-exp">
                              <button type="button" class="btn btn-circle">
                                 <i class="bi bi-pencil"></i>
                              </button>
                           </a>
                           <a href="<c:url value="/user/experiences/delete/${experience.id}"/>" class="btn-exp">
                              <button type="button" class="btn btn-circle">
                                 <i class="bi bi-trash"></i>
                              </button>
                           </a>
                        </div>
                     </div>
                  </c:forEach>
               </div>
            </c:otherwise>
         </c:choose>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
