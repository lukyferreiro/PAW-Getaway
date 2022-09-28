<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="fav" value="${false}"/>
<jsp:useBean id="favExperienceModels" scope="request" type="java.util.List"/>
<c:forEach var="favExperience" items="${favExperienceModels}">
   <c:if test="${favExperience == param.experienceId}">
      <c:set var="fav" value="${true}"/>
   </c:if>
</c:forEach>

<c:choose>
   <c:when test="${fav}">
      <a href="<c:url value="${param.urlFalse}"/>">
         <button type="button" class="btn" id="setFalse">
            <i class="fas fa-heart heart-color"></i>
         </button>
      </a>
   </c:when>
   <c:otherwise>
      <a href="<c:url value="${param.urlTrue}"/>">
         <button type="button" class="btn" id="setTrue">
            <i class="fas fa-heart"></i>
         </button>
      </a>
   </c:otherwise>
</c:choose>
