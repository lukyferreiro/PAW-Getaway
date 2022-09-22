<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
    <div class="card-title d-flex">
        <div class="col m-2">
            <img class="user-img" src="<c:url value = "/resources/images/ic_user.png" />" alt="User"/>
            <h5><c:out value="${param.userName}"/> <c:out value="${param.userSurname}"/></h5>
        </div>
        <h2 class="col m-2"><c:out value="${param.title}"/></h2>
    </div>
    <div class="col card-body p-2 mw-25">
        <div class="card-text">
            <h5 class="text-truncate"><c:out value="${param.description}"/></h5>
            <h6><spring:message code="review.date"/> <c:out value="${param.reviewDate}"/></h6>
            <h6><spring:message code="review.score"/></h6>
            <div class="star-rating">
                <c:choose>
                    <c:when test="${param.score == '1'}">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.score == '2'}">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.score == '3'}">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.score == '4'}">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                    <c:when test="${param.score == '5'}">
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                        <i class="fas fa-star star-color"></i>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>