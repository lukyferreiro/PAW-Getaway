<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/experiences.css" />" rel="stylesheet">
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
      <link href="<c:url value = "/resources/css/start_rating.css" />" rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <div class="container-experiences container-fluid overflow-auto p-0 mt-0 mb-3 h-100 d-flex flex-wrap justify-content-center">
            <c:forEach var="activity" varStatus="myIndex" items="${activities}">
               <div class="card card-experience mx-3 my-2 p-0">
                  <a class="card-link" href="<c:url value="/experiences/${activity.categoryName}/${activity.id}"/>">
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
<%--                     <div class="card-body container-fluid p-2">--%>
<%--                           <jsp:include page="/WEB-INF/views/star_avg.jsp">--%>
<%--                             <jsp:param name="avgReview" value="${avgReviews[myIndex.index]}"/>--%>
<%--                           </jsp:include>--%>
<%--                     </div>--%>
                  </a>
               </div>
            </c:forEach>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
