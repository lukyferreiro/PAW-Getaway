<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
  <head>
    <title>Getaway - Create an experience</title>
    <%@ include file="../components/includes/headers.jsp" %>
  </head>

  <body>


    <div class="container-main">
      <%@ include file="../components/navbar.jsp" %>

      <div class="card card-form-create-experience">
        <h2 class="card-title">Crea tu experiencia</h2>
        <div>
          <c:url value="/create_experience" var="postPath"/>
          <form:form modelAttribute="experienceForm" action="${postPath}" method="post">
            <div class="modal-body">
              <div>
                <form:errors path="activityName" element="p" cssStyle="color: red"/>
                <form:label path="activityName" class="form-label">Nombre</form:label>
                <form:input type="text" class="form-control" path="activityName"/>
              </div>
              <div>
                <form:errors path="activityCategory" element="p" cssStyle="color: red"/>
                <form:label path="activityCategory" class="form-label" >Categoria</form:label>
                <form:input list="categoryOptions" class="form-control" path="activityCategory" placeholder="Escribe para buscar..."/>
                <datalist id="categoryOptions">
                  <c:forEach var="category" items="${categories}">
                    <option value="${category}">
                  </c:forEach>
                </datalist>
              </div>
<%--                <div>--%>
<%--                  <form:errors path="activityCountry" element="p" cssStyle="color: red"/>--%>
<%--                  <form:label path="activityCountry" class="form-label">Pais</form:label>--%>
<%--                  <form:input list="countriesOptions" class="form-control" path="activityCountry" placeholder="Escribe para buscar..."/>--%>
<%--                  <datalist id="countriesOptions">--%>
<%--                    <c:forEach var="country" items="${countries}">--%>
<%--                      <option value="${country.name}">--%>
<%--                    </c:forEach>--%>
<%--                  </datalist>--%>
<%--                </div>--%>

                <div>
                  <form:errors path="activityCity" element="p" cssStyle="color: red"/>
                  <form:label path="activityCity" class="form-label">Ciudad</form:label>
                  <form:input list="citiesOptions" class="form-control" path="activityCity" placeholder="Escribe para buscar..."/>
                  <datalist id="citiesOptions">
                    <c:forEach var="city" items="${cities}">
                      <option value="${city.name}">
                    </c:forEach>
                  </datalist>
                </div>
              <div>
                <form:errors path="activityAddress" element="p" cssStyle="color: red"/>
                <form:label path="activityAddress" class="form-label">Direccion</form:label>
                <form:input type="text" class="form-control" path="activityAddress"/>
              </div>
              <div>
                <form:errors path="activityUrl" element="p" cssStyle="color: red"/>
                <form:label path="activityUrl" class="form-label">URL</form:label>
                <form:input type="text" class="form-control" path="activityUrl"/>
              </div>
              <div>
                <form:errors path="activityMail" element="p" cssStyle="color: red"/>
                <form:label path="activityMail" class="form-label">Email</form:label>
                <form:input type="email" class="form-control" path="activityMail" placeholder="name@example.com"/>
              </div>
              <div>
                <form:errors path="activityImg" element="p" cssStyle="color: red"/>
                <form:label path="activityImg" class="form-label">Agregar imagen</form:label>
                <form:input type="file" class="form-control" path="activityImg"/>
              </div>
              <div>
                <form:errors path="activityPrice" element="p" cssStyle="color: red"/>
                <form:label path="activityPrice" class="form-label">Precio</form:label>
                <form:input type="long" class="form-control" path="activityPrice"/>
              </div>
              <div>
                <form:errors path="activityInfo" element="p" cssStyle="color: red"/>
                <form:label path="activityInfo" class="form-label" >Descripcion</form:label>
                <form:textarea path="activityInfo" class="form-control" rows="3"/>
              </div>
              <div>
                <form:errors path="activityTags" element="p" cssStyle="color: red"/>
                <form:label path="activityTags" class="form-label">Tags</form:label>
                <form:input list="tagOptions" class="form-control" path="activityTags" placeholder="Escribe para buscar..."/>
                <datalist id="tagOptions">
                  <c:forEach var="tag" items="${tags}">
                  <option value="${tag.name}">
                    </c:forEach>
                </datalist>
              </div>
            </>
            <div class="modal-footer">
              <button class="btn" onclick="history.back()">Cancelar</button>
              <button class="btn" type="submit">Guardar</button>
            </div>
          </form:form>
        </div>
      </div>

      <%@ include file="../components/footer.jsp" %>
    </div>

    <!-- Bootstrap y Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>
  </body>
</html>
