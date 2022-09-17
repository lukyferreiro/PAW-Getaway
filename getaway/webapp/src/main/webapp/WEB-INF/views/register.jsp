<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> | <spring:message code="register.title"/> </title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>
         <div class="container-fluid py-4 px-0">
            <div class="container-lg w-100 p-5 smallContentContainer">
               <div class="row w-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center register-title"><spring:message code="registerForm.title"/></h1>
                     <p class="register-subtitle text-center mb-4">
                        <span class="font-weight-bold"><spring:message code="registerForm.description.bold"/></span>
                        <spring:message code="registerForm.description"/>
                     </p>
                  </div>
                  <div class="col-12">
                     <div class="container-lg">
                        <div class="row">
                           <div class="col-12 d-flex align-items-center justify-content-center">
                              <c:url value="/register" var="postPath"/>
                              <form:form modelAttribute="registerForm" action="${postPath}" id="registerForm" method="post" acceptCharset="UTF-8">
                                 <div class="form-group"> <!--Nombre-->
                                    <spring:message code="registerForm.name.placeholder" var="namePlaceholder"/>
                                    <form:label path="name">
                                       <spring:message code="registerForm.name.title"/><span class="required-field">*</span>
                                    </form:label>
                                    <form:input type="text" path="name" id="name" cssErrorClass="form-control is-invalid"
                                                class="form-control" placeholder="${namePlaceholder}"/>
                                    <form:errors path="name" cssClass="form-error-label" element="p"/>
                                 </div>

                                 <div class="form-group"> <!--Apellido-->
                                    <spring:message code="registerForm.surname.placeholder" var="surnamePlaceholder"/>
                                    <form:label path="surname">
                                       <spring:message code="registerForm.surname.title"/><span class="required-field">*</span>
                                    </form:label>
                                    <form:input type="text" path="surname" cssErrorClass="form-control is-invalid" id="surname"
                                                class="form-control" placeholder="${surnamePlaceholder}"/>
                                    <form:errors path="surname" cssClass="form-error-label" element="p"/>
                                 </div>

                                 <div class="form-group"> <!--Email-->
                                    <spring:message code="registerForm.email.placeholder" var="emailPlaceholder"/>
                                    <form:label path="email">
                                       <spring:message code="registerForm.email.title"/><span class="required-field">*</span>
                                    </form:label>
                                    <form:input type="text" path="email" id="email" cssErrorClass="form-control is-invalid"
                                                class="form-control" placeholder="${emailPlaceholder}"/>
                                    <form:errors path="email" cssClass="form-error-label" element="p"/>
                                 </div>

                                 <div class="form-group"> <!--Contraseña-->
                                    <form:label path="password">
                                       <spring:message code="registerForm.password.title"/> <span class="required-field">*</span>
                                    </form:label>
                                    <div class="input-group d-flex justify-content-start align-items-center">
                                       <spring:message code="registerForm.password.placeholder" var="passwordPlaceholder"/>
                                       <form:input type="password" path="password" cssClass="form-control"
                                                   id="password1" name="password" cssErrorClass="form-control is-invalid"
                                                   aria-describedby="password input" placeholder="${passwordPlaceholder}"/>
                                       <div class="input-group-append">
                                          <button id="passwordEye1" type="button" tabindex="-1"
                                                  class="btn btn-lg form-control inputBtn input-group-text">
                                             <i id="eye1" class="far fa-eye-slash"></i>
                                          </button>
                                       </div>
                                    </div>
                                    <form:errors path="password" cssClass="form-error-label" element="p"/>
                                 </div>

                                 <div class="form-group"> <!--Confirmar contraseña-->
                                    <form:label path="confirmPassword">
                                       <spring:message code="registerForm.confirmPassword.title"/> <span class="required-field">*</span>
                                    </form:label>
                                    <div class="input-group d-flex justify-content-start align-items-center">
                                       <form:input type="password" path="confirmPassword" cssClass="form-control"
                                                   id="password2" name="confirmPassword"
                                                   aria-describedby="password input" cssErrorClass="form-control is-invalid"/>
                                       <div class="input-group-append">
                                          <button id="passwordEye2" type="button" tabindex="-1"
                                                  class="btn btn-lg form-control inputBtn input-group-text">
                                             <i id="eye2" class="far fa-eye-slash"></i>
                                          </button>
                                       </div>
                                    </div>
                                    <form:errors path="confirmPassword" cssClass="form-error-label" element="p"/>
                                 </div>

                                 <div class="form-group">
                                    <spring:hasBindErrors name="registerForm">
                                       <c:if test="${errors.globalErrorCount > 0}">
                                          <div class="alert alert-danger"><form:errors/></div>
                                       </c:if>
                                    </spring:hasBindErrors>
                                 </div>

                                 <div class="col-12 px-0 d-flex align-items-center justify-content-center">
                                    <button type="button" id="registerFormButton" form="registerForm" class="w-100 btn-create-account my-2 ">
                                       <spring:message code="registerForm.btn.create"/>
                                    </button>
                                 </div>
                              </form:form>
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
      <script src='<c:url value="/resources/js/register.js"/>'></script>
   </body>
</html>
