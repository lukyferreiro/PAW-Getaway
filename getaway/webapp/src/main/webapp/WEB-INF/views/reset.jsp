<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="reset.newPassword.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href='<c:url value="/resources/css/resetRequest.css"/>' rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/simpleNavbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <div class="container-lg h-100 smallContentContainer d-flex align-items-center justify-content-center">
            <div class="row w-75 m-0 align-items-center justify-content-center">
               <div class="col-12">
                  <h1 class="text-center title">
                     <spring:message code="reset.newPassword.description"/>
                  </h1>
               </div>
               <div class="col-12">
                  <div class="container-fluid">
                     <div class="row">
                        <div class="col-12 mt-4">
                           <c:url var="postUrl" value='/user/resetPassword'/>
                           <form:form id="passReset" modelAttribute="resetPasswordForm" action="${postUrl}" method="POST" acceptCharset="UTF-8">
                              <form:input type="hidden" path="token" name="token" value="${token}"/>
                              <div class="form-group mb-5">
                                 <form:label path="password" for="password" class="form-label">
                                    <spring:message code="reset.newPassword.input"/>
                                    <span class="required-field">*</span>
                                 </form:label>
                                 <div class="input-group d-flex justify-content-start align-items-center">
                                    <spring:message code="resetPasswordForm.password.placeholder" var="placeholder"/>
                                    <form:input type="password" path="password" cssClass="form-control input" placeholder="${placeholder}"
                                                id="password1" name="password" cssErrorClass="form-control is-invalid"/>
                                    <div class="input-group-append">
                                       <button id="passwordEye1" type="button" tabindex="-1"
                                               class="btn btn-lg form-control btn-eye input-group-text">
                                          <i id="eye1" class="far fa-eye-slash"></i>
                                       </button>
                                    </div>
                                 </div>
                                 <form:errors path="password" cssClass="form-error-label" element="p"/>
                              </div>
                              <div class="form-group">
                                 <form:label path="confirmPassword" class="form-label" for="confirmPassword">
                                    <spring:message code="reset.newPassword.input2"/>
                                    <span class="required-field">*</span>
                                 </form:label>
                                 <div class="input-group d-flex justify-content-start align-items-center">
                                    <form:input type="text" path="confirmPassword" cssClass="form-control input"
                                                id="password2" name="confirmPassword"
                                                cssErrorClass="form-control is-invalid"/>
                                    <div class="input-group-append">
                                       <button id="passwordEye2" type="button" tabindex="-1"
                                               class="btn btn-lg form-control btn-eye input-group-text">
                                          <i id="eye2" class="far fa-eye-slash"></i>
                                       </button>
                                    </div>
                                 </div>
                              </div>
                              <form:errors path="" cssClass="form-error-label" element="p"/>
                           </form:form>
                        </div>
                        <div class="col-12 mt-2 d-flex align-items-center justify-content-center">
                           <button form="passReset" id="sumbitBtn" type="submit" class="btn btn-continue">
                              <spring:message code="reset.newPassword.applyBtn"/>
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
