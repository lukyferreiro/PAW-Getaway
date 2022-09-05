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
        <form:form modelAttribute="experienceForm" action="${postPath}" method="post">
          <div class="container-inputs">
            <div class="p-0 m-0 d-flex">
              <div class="col m-2"> <!--Nombre de la experiencia-->
                <form:errors path="activityName" element="p" cssStyle="color: red"/>
                <form:label path="activityName" class="form-label"><spring:message code="experienceForm.activityName"/></form:label>
                <form:input type="text" class="form-control" path="activityName"/>
              </div>
              <div class="col m-2"> <!--Categoria-->
                <form:errors path="activityCategory" element="p" cssStyle="color: red"/>
                <form:label path="activityCategory" class="form-label"><spring:message code="experienceForm.activityCategory"/></form:label>
                <form:input list="categoryOptions" class="form-control" path="activityCategory" placeholder="${placeholder}"/>
                <datalist id="categoryOptions">
                  <c:forEach var="category" items="${categories}">
                  <option value="${category}">
                    </c:forEach>
                </datalist>
              </div>
              <div class="col m-2"> <!--Precio-->
                <form:errors path="activityPrice" element="p" cssStyle="color: red"/>
                <form:label path="activityPrice" class="form-label"><spring:message code="experienceForm.activityPrice"/></form:label>
                <form:input type="long" class="form-control" path="activityPrice"/>
              </div>
            </div>

            <div class="p-0 m-2 d-flex flex-column"> <!--Descripcion-->
              <form:errors path="activityInfo" element="p" cssStyle="color: red;"/>
              <form:label path="activityInfo" class="form-label"><spring:message code="experienceForm.activityInfo"/></form:label>
              <form:textarea path="activityInfo" class="form-control" rows="4"/>
            </div>

            <div class="p-0 m-0 d-flex">
              <div class="col m-2"> <!--Email de contacto-->
                <form:errors path="activityMail" element="p" cssStyle="color: red"/>
                <form:label path="activityMail" class="form-label"><spring:message code="experienceForm.activityMail"/></form:label>
                <form:input type="email" class="form-control" path="activityMail" placeholder="${mailExample}"/>
              </div>
              <div class="col m-2"> <!--Url-->
                <form:errors path="activityUrl" element="p" cssStyle="color: red"/>
                <form:label path="activityUrl" class="form-label"><spring:message code="experienceForm.activityUrl"/></form:label>
                <form:input type="text" class="form-control" path="activityUrl"/>
              </div>
            </div>

            <div class="p-0 m-0 d-flex">
              <div class="col m-2"> <!--Pais-->
                <form:errors path="activityCountry" element="p" cssStyle="color: red"/>
                <form:label path="activityCountry" class="form-label"><spring:message code="experienceForm.activityCountry"/></form:label>
                <form:input list="countriesOptions" class="form-control" path="activityCountry" placeholder="${placeholder}"/>
                <datalist id="countriesOptions">
                  <c:forEach var="country" items="${countries}">
                    <option value="${country.name}">
                  </c:forEach>
                </datalist>
              </div>
              <div class="col m-2"> <!--Ciudad-->
                <form:errors path="activityCity" element="p" cssStyle="color: red"/>
                <form:label path="activityCity" class="form-label"><spring:message code="experienceForm.activityCity"/></form:label>
                <form:input list="citiesOptions" class="form-control" path="activityCity" placeholder="${placeholder}"/>
                <datalist id="citiesOptions">
                  <c:forEach var="city" items="${cities}">
                  <option value="${city.name}">
                    </c:forEach>
                </datalist>
              </div>
              <div class="col m-2"> <!--Direccion-->
                <form:errors path="activityAddress" element="p" cssStyle="color: red"/>
                <form:label path="activityAddress" class="form-label"><spring:message code="experienceForm.activityAddress"/></form:label>
                <form:input type="text" class="form-control" path="activityAddress"/>
              </div>
            </div>

            <div class="p-0 m-2 d-flex flex-column"> <!--Imagenes-->
              <form:errors path="activityImg" element="p" cssStyle="color: red"/>
              <form:label path="activityImg" class="form-label"><spring:message code="experienceForm.activityImg"/></form:label>
              <form:input type="file" class="form-control" path="activityImg"/>
            </div>
<%--            <div> <!--Tags-->--%>
<%--              <form:errors path="activityTags" element="p" cssStyle="color: red"/>--%>
<%--              <form:label path="activityTags" class="form-label"><spring:message code="experienceForm.activityTags"/></form:label>--%>
<%--              <form:input list="tagOptions" class="form-control" path="activityTags" placeholder="${placeholder}"/>--%>
<%--              <datalist id="tagOptions">--%>
<%--                <c:forEach var="tag" items="${tags}">--%>
<%--                <option value="${tag.name}">--%>
<%--                  </c:forEach>--%>
<%--              </datalist>--%>
<%--            </div>--%>
          </div>

          <div class="p-0 mt-3 mb-0 d-flex justify-content-around">
            <button class="btn btn-cancel-form px-3 py-2" onclick="history.back()">
              <spring:message code="experienceForm.cancel"/>
            </button>
            <button class="btn btn-submit-form px-3 py-2" type="submit">
              <spring:message code="experienceForm.submit"/>
            </button>
          </div>
        </form:form>
      </div>

      <%@ include file="../components/footer.jsp" %>
    </div>

    <%@ include file="../components/includes/bottomScripts.jsp" %>
  </body>
</html>
