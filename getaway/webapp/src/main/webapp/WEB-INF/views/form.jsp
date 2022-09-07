<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8" %>
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
        <form:form modelAttribute="experienceForm" action="${postPath}" id="createExperienceForm" method="post">
          <div class="container-inputs">
            <div class="p-0 m-0 d-flex">
              <div class="col m-2"> <!--Nombre de la experiencia-->
                <form:label path="activityName" class="form-label"><spring:message code="experienceForm.activityName"/></form:label>
                <form:input path="activityName" type="text" class="form-control"/>
                <form:errors path="activityName" element="p" cssClass="form-error-label"/>
              </div>
              <div class="col m-2"> <!--Categoria-->
                <form:label path="activityCategory" class="form-label"><spring:message code="experienceForm.activityCategory"/></form:label>
                <form:input path="activityCategory" list="categoryOptions" class="form-control" placeholder="${placeholder}"/>
                <datalist id="categoryOptions">
                  <c:forEach var="category" items="${categories}">
                  <option value="${category}">
                    </c:forEach>
                </datalist>
                <form:errors path="activityCategory" element="p" cssClass="form-error-label"/>
              </div>
              <div class="col m-2"> <!--Precio-->
                <form:label path="activityPrice" class="form-label"><spring:message code="experienceForm.activityPrice"/></form:label>
                <form:input path="activityPrice" type="long" class="form-control" placeholder="0"/>
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
              <form:label path="activityInfo" class="form-label"><spring:message code="experienceForm.activityInfo"/></form:label>
              <form:textarea path="activityInfo" class="form-control" rows="4"/>
              <form:errors path="activityInfo" element="p" cssClass="form-error-label"/>
            </div>

            <div class="p-0 m-0 d-flex">
              <div class="col m-2"> <!--Email de contacto-->
                <form:label path="activityMail" class="form-label"><spring:message code="experienceForm.activityMail"/></form:label>
                <form:input path="activityMail" type="email" class="form-control" placeholder="${mailExample}"/>
                <form:errors path="activityMail" element="p" cssClass="form-error-label"/>
              </div>
              <div class="col m-2"> <!--Url-->
                <form:label path="activityUrl" class="form-label"><spring:message code="experienceForm.activityUrl"/></form:label>
                <form:input path="activityUrl" type="text" class="form-control"/>
                <form:errors path="activityUrl" element="p" cssClass="form-error-label"/>
              </div>
            </div>

            <div class="p-0 m-0 d-flex">
              <div class="col m-2"> <!--Pais-->
                <form:label path="activityCountry" class="form-label"><spring:message code="experienceForm.activityCountry"/></form:label>
                <form:input path="activityCountry" list="countriesOptions" class="form-control" id="experienceFormCountryInput" placeholder="${placeholder}"/>
                <datalist id="countriesOptions">
                  <c:forEach var="country" items="${countries}">
                    <option value="${country.name}">
                  </c:forEach>
                </datalist>
                <form:errors path="activityCountry" element="p" cssClass="form-error-label"/>
              </div>
              <div class="col m-2"> <!--Ciudad-->
                <form:label path="activityCity" class="form-label"><spring:message code="experienceForm.activityCity"/></form:label>
                <form:input path="activityCity" list="citiesOptions" class="form-control" id="experienceFormCityInput" placeholder="${placeholder}" disabled="true"/>
                <datalist id="citiesOptions">
                  <c:forEach var="city" items="${cities}">
                  <option value="${city.name}">
                    </c:forEach>
                </datalist>
                <form:errors path="activityCity" element="p" cssClass="form-error-label"/>
              </div>
              <div class="col m-2"> <!--Direccion-->
                <form:label path="activityAddress" class="form-label"><spring:message code="experienceForm.activityAddress"/></form:label>
                <form:input path="activityAddress" type="text" class="form-control"/>
                <form:errors path="activityAddress" element="p" cssClass="form-error-label"/>
              </div>
            </div>

<%--            <div class="p-0 m-2 d-flex flex-column"> <!--Imagenes-->--%>
<%--              <form:label path="activityImg" class="form-label"><spring:message code="experienceForm.activityImg"/></form:label>--%>
<%--              <form:input path="activityImg" type="file" class="form-control" disabled="true"/>--%>
<%--              <form:errors path="activityImg" element="p" cssClass="form-error-label"/>--%>
<%--            </div>--%>
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

    <%@ include file="../components/includes/bottomScripts.jsp"%>
    <script src='<c:url value="/resources/js/createExperience.js"/>'></script>
  </body>
</html>
