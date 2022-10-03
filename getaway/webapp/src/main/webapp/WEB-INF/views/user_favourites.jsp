<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="favourite.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            <c:choose>
               <c:when test="${experiences.size() == 0}">
                  <div class="d-flex justify-content-around align-content-center">
                     <h2 class="title"><spring:message code="favourite.notExist"/></h2>
                  </div>
               </c:when>
               <c:otherwise>
                  <div class="d-flex justify-content-center align-content-center">
                     <div style="margin: 0 auto 0 20px; flex:1;">
<%--                        <jsp:include page="/WEB-INF/components/order_dropdown.jsp">--%>
<%--                           <jsp:param name="orderByModels" value="${orderByModels}"/>--%>
<%--                           <jsp:param name="path" value="/user/favourites"/>--%>
<%--                        </jsp:include>--%>
                     </div>
                     <h3 class="title m-0"><spring:message code="favourite.description"/></h3>
                     <div style="margin: 0 20px 0 auto; flex:1;"></div>
                  </div>

                  <div class="container-fluid my-3 d-flex flex-wrap justify-content-center">
                     <c:forEach var="experience" varStatus="myIndex" items="${experiences}">
                        <jsp:include page="/WEB-INF/components/card_experience.jsp">
                           <jsp:param name="hasImage" value="${experience.hasImage}"/>
                           <jsp:param name="categoryName" value="${experience.categoryName}"/>
                           <jsp:param name="id" value="${experience.experienceId}"/>
                           <jsp:param name="name" value="${experience.experienceName}"/>
                           <jsp:param name="description" value="${experience.description}"/>
                           <jsp:param name="address" value="${experience.address}"/>
                           <jsp:param name="price" value="${experience.price}"/>
                           <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                           <jsp:param name="path" value="/user/favourites"/>
                           <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                           <jsp:param name="reviewCount" value="${listReviewsCount[myIndex.index]}"/>
                           <jsp:param name="isEditing" value="${isEditing}"/>
                        </jsp:include>
                     </c:forEach>
                  </div>
               </c:otherwise>
            </c:choose>
         </div>
         <%@ include file="../components/footer.jsp" %>
      </div>
      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
