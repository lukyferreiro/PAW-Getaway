<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="resetPassword.emailConfirmation.title"/></title>
      <%@ include file="../../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../../components/simpleNavbar.jsp" %>

         <div class="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div class="container-lg w-100 modalContainer">
               <div class="row w-100 m-0 p-4 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="resetPassword.emailConfirmation.description"/>
                     </h1>
                  </div>
                  <div class="col-12">
                     <div class="container-fluid">
                        <div class="row">
                           <c:url var="postUrl" value='/user/resetPasswordRequest'/>
                           <form:form id="passResetRequest" modelAttribute="resetPasswordEmailForm" action="${postUrl}" method="POST" acceptCharset="UTF-8">
                              <form:label path="email" for="email" class="form-label">
                                 <spring:message code="resetPassword.emailConfirmation.email"/>
                                 <span class="required-field">*</span>
                              </form:label>
                              <spring:message code="resetPasswordEmailForm.email.placeholder" var="placeholder"/>
                              <form:input path="email" type="text" class="form-control input" placeholder="${placeholder}"
                                          id="email" name="email" cssErrorClass="form-control is-invalid"/>
                              <form:errors path="email" cssClass="form-error-label" element="p"/>
                              <div class="col-12 mt-3 d-flex align-items-center justify-content-center">
                                 <button form="passResetRequest" type="submit" class="btn btn-continue">
                                    <spring:message code="resetPassword.emailConfirmation.applyBtn"/>
                                 </button>
                              </div>
                           </form:form>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
         <%@ include file="../../components/footer.jsp" %>
      </div>
      <%@ include file="../../components/includes/bottomScripts.jsp" %>
   </body>
</html>
