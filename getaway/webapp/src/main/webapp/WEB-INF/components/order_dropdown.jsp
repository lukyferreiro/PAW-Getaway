<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<button class="btn btn-search my-2 dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
   <spring:message code="order.title"/>
</button>
<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
   <c:forEach var="orderBy" items="${param.orderByModels}">
      <a class="dropdown-item" href="<c:url value="${param.mainPath}${orderBy.getFullUrl()}" />">
         <spring:message code="order.${orderBy.toString()}"/>
      </a>
   </c:forEach>
</ul>

