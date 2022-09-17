<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> | Recuperar contraseña</title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href='<c:url value="/resources/css/resetRequest.css"/>' rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>

         <div class="container-lg p-5 mt-5 smallContentContainer">
            <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
               <div class="col-12">
                  <h1 class="text-center title">
                     Ingresa tu nueva contraseña
                  </h1>
               </div>
               <div class="col-12">
                  <div class="container-fluid">
                     <div class="row">
                        <div class="col-12 mt-4">
                           <c:url var="postUrl" value='/user/resetPassword'/>
                           <form:form id="passReset" modelAttribute="resetPasswordForm"
                                      action="${postUrl}"
                                      method="POST">
                              <form:input type="hidden" path="token" name="token" value="${token}"/>
                              <div class="form-group">
                                 <form:label path="password" for="password">
                                    Nueva contraseña
                                 </form:label>
                                 <div class="input-group d-flex justify-content-start align-items-center">
                                    <form:input type="password" path="password" cssClass="form-control input"
                                                id="password1"
                                                name="password" cssErrorClass="form-control is-invalid"/>
                                    <div class="input-group-append">
                                       <button id="passwordEye1" type="button" tabindex="-1"
                                               class="btn btn-lg form-control inputBtn input-group-text">
                                          <i id="eye1" class="far fa-eye-slash"></i>
                                       </button>
                                    </div>
                                 </div>
                                 <form:errors path="password" cssClass="formError" element="p"/>
                              </div>
                              <div class="form-group">
                                 <form:label path="confirmPassword" cssClass="mt-2" for="confirmPassword">
                                    Confirmar contrase\u00F1a
                                 </form:label>
                                 <div class="input-group d-flex justify-content-start align-items-center">
                                    <form:input type="text" path="confirmPassword" cssClass="form-control input"
                                                id="password2" name="confirmPassword"
                                                cssErrorClass="form-control is-invalid"/>
                                    <form:errors path="" cssClass="formError" element="p"/>
                                    <div class="input-group-append">
                                       <button id="passwordEye2" type="button" tabindex="-1"
                                               class="btn btn-lg form-control inputBtn input-group-text">
                                          <i id="eye2" class="far fa-eye-slash"></i>
                                       </button>
                                    </div>
                                 </div>
                              </div>
                              <form:errors path="" cssClass="formError" element="p"/>
                           </form:form>
                        </div>
                        <div class="col-12 mt-2 d-flex align-items-center justify-content-center">
                           <button form="passReset" id="sumbitBtn" type="submit" class="w-100 submitBtn my-2">
                              Aplicar
                           </button>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src="<c:url value='/resources/js/resetPassword.js'/>"></script>
   </body>
</html>
