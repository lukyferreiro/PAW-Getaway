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
            <c:when test="${activities.size() == 0}">
               <h2 class="title"><spring:message code="experience.notExist"/></h2>
            </c:when>
            <c:otherwise>
               <h2 class="title"><spring:message code="experience.title"/></h2>
               <div class="cards container-experiences container-fluid">
                  <c:forEach var="activity" varStatus="myIndex" items="${activities}">
                     <div class="card card-experience mx-3 my-2 p-0">
                        <a class="card-link"
                           href="<c:url value="/experiences/${activity.categoryName}/${activity.id}"/>">
                           <jsp:include page="/WEB-INF/views/card_experience.jsp">
                              <jsp:param name="hasImage" value="${activity.hasImage}"/>
                              <jsp:param name="categoryName" value="${activity.categoryName}"/>
                              <jsp:param name="id" value="${activity.id}"/>
                              <jsp:param name="name" value="${activity.name}"/>
                              <jsp:param name="description" value="${activity.description}"/>
                              <jsp:param name="address" value="${activity.address}"/>
                              <jsp:param name="price" value="${activity.price}"/>
                              <jsp:param name="myIndex" value="${myIndex.index}"/>
                           </jsp:include>
                           <div class="card-body container-fluid p-2">
                              <jsp:include page="/WEB-INF/views/star_avg.jsp">
                                 <jsp:param name="avgReview" value="${avgReviews[myIndex.index]}"/>
                              </jsp:include>
                           </div>
                        </a>
                        <div class="btn-group" role="group">
                           <a href="<c:url value="/user/experiences/edit/${activity.id}"/>" class="btn-exp">
                              <button type="button" class="btn btn-circle">
                                 <i class="bi bi-pencil"></i>
                              </button>
                           </a>
                           <a href="<c:url value="/user/experiences/delete/${activity.id}"/>" class="btn-exp">
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
