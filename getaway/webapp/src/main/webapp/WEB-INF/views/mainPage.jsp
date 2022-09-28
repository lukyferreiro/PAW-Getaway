<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 overflow-auto my-3 h-100 d-flex flex-wrap justify-content-center">
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
                  <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                  <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                  <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
               </jsp:include>
            </c:forEach>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
