<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> | <spring:message code="experience.deleteQuestion"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href='<c:url value="/resources/css/resetRequest.css"/>' rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/simpleNavbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <div class="container-fluid py-4 px-0 d-flex align-items-center">
            <div class="container-lg p-5 mt-5 smallContentContainer">
               <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="experience.deleteQuestion"/>
                     </h1>
                  </div>
                  <div class="col-12 px-0 d-flex align-items-center justify-content-center">
                     <c:url value="/delete/${experience.id}" var="postPath"/>
                     <form:form modelAttribute="deleteForm" action="${postPath}" id="deleteExperienceForm" method="post" acceptCharset="UTF-8" enctype="multipart/form-data">
                        <div class="p-0 mt-3 mb-0 d-flex justify-content-around">
                           <a href="<c:url value = "/"/>">
                              <button class="btn btn-cancel-form px-3 py-2" id="cancelFormButton">
                                 <spring:message code="experienceForm.cancel"/>
                              </button>
                           </a>
                           <button type="submit" class="btn btn-submit-form px-3 py-2" id="deleteExperienceFormButton" form="deleteExperienceForm">
                              <spring:message code="experience.delete"/>
                           </button>
                        </div>
                     </form:form>
                  </div>
               </div>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/deleteExperience.js"/>'></script>

   </body>
</html>
