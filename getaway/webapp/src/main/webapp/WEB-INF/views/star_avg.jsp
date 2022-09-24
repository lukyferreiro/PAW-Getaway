<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
    <c:choose>
        <c:when test="${param.avgReview!=0}">
            <div class="information-title">
                <h6 class="information-title">
                    <spring:message code="experienceDetail.review"/>
                </h6>
            </div>
            <div class="star-rating">
                <c:choose>
                    <c:when test="${param.avgReview == 1 }">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.avgReview == 2 }">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.avgReview== 3 }">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.avgReview == 4 }">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.avgReview == 5}">
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                </c:choose>
            </div>

        </c:when>
    </c:choose>

</div>