<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="createExperience.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="d-flex flex-column justify-content-center mx-5 my-2 p-0">
            <h2 class="text-center font-weight-bold">
               <spring:message code="createExperience.description"/>
            </h2>

            <c:url value="/create_experience" var="postPath"/>
            <spring:message code="experienceForm.inputs.placeholder" var="placeholder"/>
            <spring:message code="experienceForm.activityMail.example" var="mailExample"/>
            <spring:message code="experienceForm.activityUrl.example" var="urlExample"/>
            <form:form modelAttribute="experienceForm" action="${postPath}" id="createExperienceForm" method="post" acceptCharset="UTF-8" enctype="multipart/form-data">
               <div class="container-inputs">
                  <div class="p-0 m-0 d-flex">
                     <div class="col m-2"> <!--Nombre de la experiencia-->
                        <form:label path="activityName" class="form-label">
                           <spring:message code="experienceForm.activityName"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:input path="activityName" type="text" class="form-control"/>
                        <form:errors path="activityName" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Categoria-->
                        <form:label path="activityCategory" class="form-label">
                           <spring:message code="experienceForm.activityCategory"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:select path="activityCategory" class="form-select">
                           <option disabled selected value><c:out value="${placeholder}"/></option>
                           <c:forEach var="category" items="${categories}">
                              <option><c:out value="${category}"/></option>
                           </c:forEach>
                        </form:select>
                        <form:errors path="activityCategory" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Precio-->
                        <form:label path="activityPrice" class="form-label">
                           <spring:message code="experienceForm.activityPrice"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.optional"/></span>
                        </form:label>
                        <form:input path="activityPrice" type="text" class="form-control" id="experienceFormPriceInput" placeholder="0"/>
                        <form:errors path="activityPrice" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>

                     <%--            <div class="p-0 m-2 d-flex flex-column"> <!--Tags-->--%>
                     <%--              <form:label path="activityTags" class="form-label"><spring:message code="experienceForm.activityTags"/></form:label>--%>
                     <%--              <form:input list="tagOptions" class="form-control" path="activityTags" placeholder="${placeholder}"/>--%>
                     <%--              <datalist id="tagOptions">--%>
                     <%--                <c:forEach var="tag" items="${tags}">--%>
                     <%--                <option value="${tag.name}">--%>
                     <%--                  </c:forEach>--%>
                     <%--              </datalist>--%>
                     <%--              <form:errors path="activityTags" element="p" cssClass="form-error-label"/>--%>
                     <%--            </div>--%>

                  <div class="p-0 m-2 d-flex flex-column"> <!--Descripcion-->
                     <form:label path="activityInfo" class="form-label">
                        <spring:message code="experienceForm.activityInfo"/>
                        <span class="required-optional-text"><spring:message code="experienceForm.optional"/></span>
                     </form:label>
                     <form:textarea path="activityInfo" class="form-control" rows="4"/>
                     <form:errors path="activityInfo" element="p" cssClass="form-error-label"/>
                  </div>

                  <div class="p-0 m-0 d-flex">
                     <div class="col m-2"> <!--Email de contacto-->
                        <form:label path="activityMail" class="form-label">
                           <spring:message code="experienceForm.activityMail"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:input path="activityMail" type="email" class="form-control" placeholder="${mailExample}"/>
                        <form:errors path="activityMail" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Url-->
                        <form:label path="activityUrl" class="form-label">
                           <spring:message code="experienceForm.activityUrl"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.optional"/></span>
                        </form:label>
                        <form:input path="activityUrl" id="experienceFormUrlInput" type="text" class="form-control" placeholder="${urlExample}"/>
                        <form:errors path="activityUrl" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>

                  <div class="p-0 m-0 d-flex">
                     <div class="col m-2"> <!--Pais-->
                        <form:label path="activityCountry" class="form-label">
                           <spring:message code="experienceForm.activityCountry"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:select path="activityCountry" id="experienceFormCountryInput" class="form-select">
                           <option disabled selected value><c:out value="${placeholder}"/></option>
                           <c:forEach var="country" items="${countries}">
                              <option><c:out value="${country.name}"/></option>
                           </c:forEach>
                        </form:select>
                        <form:errors path="activityCountry" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Ciudad-->
                        <form:label path="activityCity" class="form-label">
                           <spring:message code="experienceForm.activityCity"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:select path="activityCity" id="experienceFormCityInput" class="form-select" disabled="true">
                           <option disabled selected value><c:out value="${placeholder}"/></option>
                           <c:forEach var="city" items="${cities}">
                              <option><c:out value="${city.name}"/></option>
                           </c:forEach>
                        </form:select>
                        <form:errors path="activityCity" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Direccion-->
                        <form:label path="activityAddress" class="form-label">
                           <spring:message code="experienceForm.activityAddress"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:input path="activityAddress" type="text" class="form-control"/>
                        <form:errors path="activityAddress" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>

                  <div class="p-0 m-2 d-flex flex-column"> <!--Imagenes-->
                     <form:label path="activityImg" class="form-label">
                        <spring:message code="experienceForm.activityImg"/>
                        <span class="required-optional-text"><spring:message code="experienceForm.optional"/></span>
                     </form:label>
                     <form:input path="activityImg" type="file" class="form-control"/>
                     <form:errors path="activityImg" element="p" cssClass="form-error-label"/>
                  </div>
               </div>

               <div class="p-0 mt-3 mb-0 d-flex justify-content-around">
                  <a href="<c:url value = "/"/>">
                     <button class="btn btn-cancel-form px-3 py-2" id="cancelFormButton">
                        <spring:message code="experienceForm.cancel"/>
                     </button>
                  </a>
                  <button type="submit" class="btn btn-submit-form px-3 py-2" id="createExperienceFormButton" form="createExperienceForm">
                     <spring:message code="experienceForm.submit"/>
                  </button>
               </div>
            </form:form>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/createExperience.js"/>'></script>
   </body>
</html>
