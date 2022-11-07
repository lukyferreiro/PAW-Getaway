<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="${title}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="d-flex flex-column justify-content-center mx-5 my-2 p-0">
            <h2 class="text-center font-weight-bold">
               <spring:message code="${description}"/>
            </h2>

            <c:url value="${endpoint}" var="postPath"/>
            <spring:message code="experienceForm.inputs.placeholder" var="placeholder"/>
            <spring:message code="experienceForm.experienceMail.example" var="mailExample"/>
            <spring:message code="experienceForm.experienceUrl.example" var="urlExample"/>
            <form:form modelAttribute="experienceForm" action="${postPath}" id="createExperienceForm" method="post" acceptCharset="UTF-8" enctype="multipart/form-data">
               <div class="container-inputs">
                  <div class="p-0 m-0 d-flex">
                     <div class="col m-2"> <!--Nombre de la experiencia-->
                        <form:label path="experienceName" class="form-label d-flex justify-content-between">
                           <div>
                              <spring:message code="experienceForm.experienceName"/>
                              <span class="required-field">*</span>
                           </div>
                           <div class="align-self-center">
                              <h6 class="max-input-text"> <spring:message code="experienceForm.maxInput" arguments="50"/> </h6>
                           </div>
                        </form:label>
                        <form:input maxlength="50" path="experienceName" type="text" class="form-control" cssErrorClass="form-control is-invalid"/>
                        <form:errors path="experienceName" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Categoria-->
                        <form:label path="experienceCategory" class="form-label">
                           <spring:message code="experienceForm.experienceCategory"/>
                           <span class="required-field">*</span>
                        </form:label>
                        <form:select path="experienceCategory" class="form-select" cssErrorClass="form-control is-invalid">
                           <c:if test="${formCategory == 0}">
                              <option value="" disabled selected hidden><c:out value="${placeholder}"/></option>
                           </c:if>
                           <c:forEach var="category" items="${categories}">
                              <c:choose>
                                 <c:when test="${formCategory != null && category.ordinal() == formCategory-1}">
                                    <option selected value="${category.ordinal()}">
                                       <spring:message code="experienceForm.${category.toString()}"/></option>
                                 </c:when>
                                 <c:otherwise>
                                    <option value="${category.ordinal()}">
                                       <spring:message code="experienceForm.${category.toString()}"/></option>
                                 </c:otherwise>
                              </c:choose>
                           </c:forEach>
                        </form:select>
                        <form:errors path="experienceCategory" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Precio-->
                        <form:label path="experiencePrice" class="form-label d-flex justify-content-between">
                           <div>
                              <spring:message code="experienceForm.experiencePrice"/>
                              <span class="optional-text"><spring:message code="inputField.optional"/></span>
                           </div>
                           <div class="align-self-center">
                              <h6 class="max-input-text"> <spring:message code="experienceForm.maxInput" arguments="7"/> </h6>
                           </div>
                        </form:label>
                        <form:input path="experiencePrice" type="text" class="form-control" id="experienceFormPriceInput"
                                    cssErrorClass="form-control is-invalid" placeholder="0"/>
                        <form:errors path="experiencePrice" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>

                  <div class="p-0 m-2 d-flex flex-column"> <!--Descripcion-->
                     <form:label path="experienceInfo" class="form-label d-flex justify-content-between">
                        <div>
                           <spring:message code="experienceForm.experienceInfo"/>
                           <span class="optional-text"><spring:message code="inputField.optional"/></span>
                        </div>
                        <div class="align-self-center">
                           <h6 class="max-input-text"> <spring:message code="experienceForm.maxInput" arguments="500"/> </h6>
                        </div>
                     </form:label>
                     <form:textarea maxlength="500" path="experienceInfo" class="form-control" cssErrorClass="form-control is-invalid" rows="4" cssStyle="max-height: 300px;"/>
                     <form:errors path="experienceInfo" element="p" cssClass="form-error-label"/>
                  </div>

                  <div class="p-0 m-0 d-flex">
                     <div class="col m-2"> <!--Email de contacto-->
                        <form:label path="experienceMail" class="form-label d-flex justify-content-between">
                           <div>
                              <spring:message code="experienceForm.experienceMail"/>
                              <span class="required-field">*</span>
                           </div>
                           <div class="align-self-center">
                              <h6 class="max-input-text"> <spring:message code="experienceForm.maxInput" arguments="255"/> </h6>
                           </div>
                        </form:label>
                        <form:input maxlength="255" path="experienceMail" type="email" class="form-control"
                                    cssErrorClass="form-control is-invalid" placeholder="${mailExample}"/>
                        <form:errors path="experienceMail" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Url-->
                        <form:label path="experienceUrl" class="form-label d-flex justify-content-between">
                           <div>
                              <spring:message code="experienceForm.experienceUrl"/>
                              <span class="optional-text"><spring:message code="inputField.optional"/></span>
                           </div>
                           <div class="align-self-center">
                              <h6 class="max-input-text"> <spring:message code="experienceForm.maxInput" arguments="500"/> </h6>
                           </div>
                        </form:label>
                        <form:input maxlength="500" path="experienceUrl" id="experienceFormUrlInput" type="text"
                                    cssErrorClass="form-control is-invalid" class="form-control" placeholder="${urlExample}"/>
                        <form:errors path="experienceUrl" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>

                  <div class="p-0 m-0 d-flex">
                     <div class="col m-2"> <!--Pais-->
                        <form:label path="experienceCountry" class="form-label">
                           <spring:message code="experienceForm.experienceCountry"/>
                           <span class="required-field">*</span>
                        </form:label>
                        <form:select path="experienceCountry" id="experienceFormCountryInput" class="form-select" cssErrorClass="form-control is-invalid">
                           <option disabled selected value><c:out value="${placeholder}"/></option>
                           <option selected><c:out value="${country}"/></option>
                        </form:select>
                        <form:errors path="experienceCountry" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Ciudad-->
                        <form:label path="experienceCity" class="form-label">
                           <spring:message code="experienceForm.experienceCity"/>
                           <span class="required-field">*</span>
                        </form:label>
                        <form:select path="experienceCity" id="experienceFormCityInput" class="form-select"
                                     cssErrorClass="form-control is-invalid" disabled="true">
                           <c:if test="${formCity == null}">
                              <option value="" disabled selected hidden><c:out value="${placeholder}"/></option>
                           </c:if>
                           <c:forEach var="city" items="${cities}">
                              <c:choose>
                                 <c:when test="${formCity != null && city.cityName == formCity}">
                                    <option selected><c:out value="${city.cityName}"/></option>
                                 </c:when>
                                 <c:otherwise>
                                    <option><c:out value="${city.cityName}"/></option>
                                 </c:otherwise>
                              </c:choose>
                           </c:forEach>
                        </form:select>
                        <form:errors path="experienceCity" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Direccion-->
                        <form:label path="experienceAddress" class="form-label d-flex justify-content-between">
                           <div>
                              <spring:message code="experienceForm.experienceAddress"/>
                              <span class="required-field">*</span>
                           </div>
                           <div class="align-self-center">
                              <h6 class="max-input-text"> <spring:message code="experienceForm.maxInput" arguments="100"/> </h6>
                           </div>
                        </form:label>
                        <form:input maxlength="100" path="experienceAddress" type="text" class="form-control" cssErrorClass="form-control is-invalid"/>
                        <form:errors path="experienceAddress" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>

                  <div class="p-0 m-2 d-flex flex-column"> <!--Imagenes-->
                     <form:label path="experienceImg" class="form-label">
                        <spring:message code="experienceForm.experienceImg"/>
                        <span class="optional-text"><spring:message code="inputField.optional"/></span>
                     </form:label>
                     <form:input path="experienceImg" type="file" class="form-control" cssErrorClass="form-control is-invalid"/>
                     <form:errors path="experienceImg" element="p" cssClass="form-error-label"/>
                  </div>
               </div>

               <div class="p-0 mt-3 mb-0 d-flex justify-content-around">
                  <a href="<c:url value="${cancelBtn}"/>">
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
      <script src='<c:url value="/resources/js/cancelButton.js"/>'></script>

   </body>
</html>
