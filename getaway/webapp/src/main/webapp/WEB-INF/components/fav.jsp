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
      <a href="<c:url value="${param.path}">
            <c:param name="set" value="${false}"/>
            <c:param name="experience" value="${param.experienceId}"/>
         </c:url>
      ">
         <button type="button" class="btn btn-fav" id="setFalse">
            <i class="fas fa-heart heart-color"></i>
         </button>
      </a>
   </c:when>
   <c:otherwise>
      <a href="<c:url value="${param.path}">
            <c:param name="set" value="${true}"/>
            <c:param name="experience" value="${param.experienceId}"/>
         </c:url>
      ">
         <button type="button" class="btn btn-fav" id="setTrue">
            <i class="fas fa-heart"></i>
         </button>
      </a>
   </c:otherwise>
</c:choose>
