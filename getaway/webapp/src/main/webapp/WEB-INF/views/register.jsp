<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>

         <div class="container-mainpage container-fluid p-0 d-flex justify-content-center align-items-center">
            <div class="main-text container-fluid p-0 text-center font-weight-bold">
               <spring:message code="mainPage.description"/>
            </div>
         </div>

         <div class="container-fluid py-4 px-0">
            <div class="container-lg w-100 p-5 smallContentContainer">
               <div class="row w-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">Crea tu cuenta</h1>
                     <p class="subtitle text-center mb-4">
                        <span class="font-weight-bold">Ingresa tus datos</span>
                        y comienza a ofrecer tus experiencias</p>
                  </div>
                  <div class="col-12">
                     <div class="container-lg">
                        <div class="row">
                           <div class="col-12 d-flex align-items-center justify-content-center">
                              <%@ include file="../components/forms/registerForm.jsp" %>
                           </div>
                        </div>
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
