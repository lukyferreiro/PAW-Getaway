<%--
  Created by IntelliJ IDEA.
  User: Agustina
  Date: 9/2/2022
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
  <title>Experience Form</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
  <link href="<c:url value = "/resources/css/activity.css" />" rel="stylesheet">
</head>
<body>
<%@ include file="index.jsp" %>
<div class="card">
  <h2 class="card-title">Crea tu experiencia</h2>
  <div>
    <c:url value="/experience_form" var="postPath"/>
    <form:form modelAttribute="activityForm" action="${postPath}" method="post">
      <div class="modal-body">
        <div>
          <form:errors path="activityName" element="p" cssStyle="color: red"/>
          <form:label path="activityName" class="form-label">Nombre</form:label>
          <form:input type="text" class="form-control" path="activityName"/>
        </div>
        <div>
          <form:errors path="activityCategory" element="p" cssStyle="color: red"/>
          <form:label path="activityCategory" class="form-label" >Tags</form:label>
          <form:input list="datalistOptions" class="form-control" path="activityCategory" placeholder="Escribe para buscar..."/>
          <datalist id="categoryOptions">
            <option value="Aventura">
            <option value="Gastronomia">
            <option value="Hoteleria">
            <option value="Relax">
            <option value="Vida nocturna">
            <option value="Historico">
          </datalist>
        </div>
        <div>
          <form:errors path="activityAddress" element="p" cssStyle="color: red"/>
          <form:label path="activityAddress" class="form-label">Direccion</form:label>
          <form:input type="text" class="form-control" path="activityAddress"/>
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
          <form:errors path="activityInfo" element="p" cssStyle="color: red"/>
          <form:label path="activityInfo" class="form-label" >Descripcion</form:label>
          <form:textarea path="activityInfo" class="form-control" rows="3"/>
        </div>
        <div>
          <form:errors path="activityTags" element="p" cssStyle="color: red"/>
          <form:label path="activityTags" class="form-label">Tags</form:label>
          <form:input list="datalistOptions" class="form-control" path="activityTags" placeholder="Escribe para buscar..."/>
          <datalist id="datalistOptions">
            <option value="Paracaidismo">
            <option value="Hoteleria">
            <option value="Spa">
            <option value="Comida">
            <option value="Cerveza">
          </datalist>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn" type="button">Cancelar</button>
        <button class="btn" type="submit">Guardar</button>
      </div>
    </form:form>
  </div>
</div>


<!-- Bootstrap y Popper -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js" integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK" crossorigin="anonymous"></script>

</body>
</html>
