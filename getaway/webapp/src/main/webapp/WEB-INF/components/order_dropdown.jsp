<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<button class="btn btn-search my-2 dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
   <spring:message code="order.title"/>
</button>
<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
   <jsp:useBean id="orderByModels" scope="request" type="ar.edu.itba.getaway.models.OrderByModel[]"/>
   <c:forEach var="orderBy" items="${orderByModels}">
      <a class="dropdown-item" href="
         <c:url value="${param.path}">
            <c:param name="orderBy" value="${orderBy}"/>
               <c:param name="score" value="${param.score}"/>
               <c:param name="cityId" value="${param.cityId}"/>
               <c:param name="maxPrice" value="${param.maxPrice}"/>
         </c:url>">
         <spring:message code="order.${orderBy.toString()}"/>
      </a>
   </c:forEach>
</ul>
