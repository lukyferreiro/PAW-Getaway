<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="experience.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            <c:choose>
               <c:when test="${experiences.size() == 0}">
               <div class="d-flex justify-content-around align-content-center">
                  <h2 class="title"><spring:message code="experience.notExist"/></h2>
               </div>
               </c:when>
               <c:otherwise>
                  <div class="d-flex justify-content-around align-content-center">
                     <h3 class="title"><spring:message code="experience.title"/></h3>
                     <jsp:include page="/WEB-INF/components/order_dropdown.jsp">
                        <jsp:param name="orderByModels" value="${orderByModels}"/>
                        <jsp:param name="path" value="/user/experiences"/>
                     </jsp:include>
                  </div>

                  <div class="container-fluid mt-3 d-flex flex-wrap justify-content-center align-content-center">
                     <c:forEach var="experience" varStatus="myIndex" items="${experiences}">
                        <div class="d-flex flex-column justify-content-center align-content-center">
                           <jsp:include page="/WEB-INF/components/card_experience.jsp">
                              <jsp:param name="hasImage" value="${experience.hasImage}"/>
                              <jsp:param name="categoryName" value="${experience.categoryName}"/>
                              <jsp:param name="id" value="${experience.experienceId}"/>
                              <jsp:param name="name" value="${experience.experienceName}"/>
                              <jsp:param name="description" value="${experience.description}"/>
                              <jsp:param name="address" value="${experience.address}"/>
                              <jsp:param name="price" value="${experience.price}"/>
                              <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                              <jsp:param name="favUrlFalse" value="/user/experiences?experience=${experience.experienceId}&set=${false}"/>
                              <jsp:param name="favUrlTrue" value="/user/experiences?experience=${experience.experienceId}&set=${true}"/>
                              <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                           </jsp:include>
                           <div class="btn-group" role="group">
                              <a href="<c:url value="/user/experiences/edit/${experience.experienceId}"/>" class="btn-exp">
                                 <button type="button" class="btn btn-circle">
                                    <i class="bi bi-pencil"></i>
                                 </button>
                              </a>
                              <a href="<c:url value="/user/experiences/delete/${experience.experienceId}"/>" class="btn-exp">
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
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
