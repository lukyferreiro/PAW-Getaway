<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="star-rating my-1">
   <c:choose>
<%--      <c:when test="${param.avgReview == null || param.avgReview == ''}">--%>
<%--         <i class="fas fa-star"></i>--%>
<%--         <i class="fas fa-star"></i>--%>
<%--         <i class="fas fa-star"></i>--%>
<%--         <i class="fas fa-star"></i>--%>
<%--         <i class="fas fa-star"></i>--%>
<%--      </c:when>--%>
      <c:when test="${param.avgReview == 1 || param.avgReview == '1'}">
         <i class="fas fa-star"></i>
         <i class="fas fa-star"></i>
         <i class="fas fa-star"></i>
         <i class="fas fa-star"></i>
         <i class="fas fa-star star-color"></i>
      </c:when>
      <c:when test="${param.avgReview == 2 || param.avgReview == '2'}">
         <i class="fas fa-star"></i>
         <i class="fas fa-star"></i>
         <i class="fas fa-star"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
      </c:when>
      <c:when test="${param.avgReview== 3 || param.avgReview == '3'}">
         <i class="fas fa-star"></i>
         <i class="fas fa-star"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
      </c:when>
      <c:when test="${param.avgReview == 4 || param.avgReview == '4'}">
         <i class="fas fa-star"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
      </c:when>
      <c:when test="${param.avgReview == 5 || param.avgReview == '5'}">
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
         <i class="fas fa-star star-color"></i>
      </c:when>
   </c:choose>
</div>


