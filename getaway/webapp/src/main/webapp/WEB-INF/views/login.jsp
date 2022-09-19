<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> | <spring:message code="login.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/login&register.css"/>" rel="stylesheet" type="text/css"/>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>

         <div class="container-fluid p-0 h-100 w-100 d-flex justify-content-center align-items-center">
            <div class="container-lg w-100 p-2 smallContentContainer">
               <div class="row w-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title"><spring:message code="loginForm.title"/></h1>
                     <p class="subtitle text-center">
                        <spring:message code="loginForm.description"/>
                     </p>
                  </div>
                  <div class="col-12">
                     <div class="container-fluid px-0">
                        <c:url value="/login" var="postPath"/>
                        <form id="loginForm" action="${postPath}" accept-charset="UTF-8" method="POST" enctype="application/x-www-form-urlencoded">
                           <div class="row">
                              <div class="col-12"> <!--Email-->
                                 <label for="email" class="form-label">
                                    <spring:message code="loginForm.email.title"/>
                                 </label>
                                 <input type="text" id="email" name="email" class="form-control mb-2" aria-describedby="email input"/>
                              </div>
                           </div>

                           <div class="col-12 mt-2">
                              <div class="container-fluid">
                                 <div class="row">
                                    <div class="col-6 px-0">
                                       <label class="form-label" for="password">
                                          <spring:message code="loginForm.password.title"/>
                                       </label>
                                    </div>
                                    <div class="col-6 px-0 d-flex justify-content-end form-label">
                                       <a href="<c:url value='/user/resetPasswordRequest'/>" tabindex="-1">
                                           <span class="text-right" style="font-size: medium; ">
                                               <spring:message code="loginForm.forgotPassword"/>
                                           </span>
                                       </a>
                                    </div>
                                    <div class="col-12 px-0 d-flex justify-content-start align-items-center">
                                       <div class="input-group d-flex justify-content-start align-items-center">
                                          <input type="password" class="form-control"
                                                 id="password" name="password"
                                                 aria-describedby="password input"/>
                                          <div class="input-group-append">
                                             <button id="passwordEye" type="button" tabindex="-1"
                                                     class="btn btn-lg form-control btn-eye input-group-text">
                                                <i id="eye" class="far fa-eye-slash"></i>
                                             </button>
                                          </div>
                                       </div>
                                    </div>
                                    <c:if test="${error==true}">
                                       <div class="col-12 mt-2 px-0 d-flex justify-content-start align-items-center">
                                          <p class="mb-0 form-error-label">
                                             <spring:message code="loginForm.error"/>
                                          </p>
                                       </div>
                                    </c:if>
                                 </div>
                              </div>
                           </div>

                           <div class="col-12 mt-3 d-flex justify-content-start align-items-center">
                              <input type="checkbox" id="rememberMe" name="rememberMe"/>
                              <label class="mb-0 mx-2" for="rememberMe" style="font-size: medium">
                                 <spring:message code="loginForm.rememberme"/>
                              </label>
                           </div>
                        </form>
                     </div>
                  </div>
                  <div class="col-12 d-flex align-items-center justify-content-center">
                     <button form="loginForm" type="submit" class="w-100 btn-login my-2">
                        <spring:message code="loginForm.btn"/>
                     </button>
                  </div>
                  <div class="col-12 mt-4">
                     <p class="mb-0 text-center" style="font-size: large;">
                        <spring:message code="loginForm.register1"/>
                        <a href="<c:url value='/register'/>">
                           <spring:message code="loginForm.register2"/>
                        </a>
                     </p>
                  </div>
               </div>
            </div>
         </div>
         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/login.js"/>'></script>
   </body>
</html>
