<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div class="dropdown">
        <button class="btn btn-header dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
            <spring:message code="order.title"/>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <a  class="dropdown-item" href="<c:url value = "${param.path1}"/>">
                <spring:message code="order.rankingAsc"/>
            </a>
            <a  class="dropdown-item" href="<c:url value = "${param.path2}"/>">
                <spring:message code="order.rankingDesc"/>
            </a>
            <a  class="dropdown-item" href="<c:url value = "${param.path3}"/>">
                <spring:message code="order.A_Z"/>
            </a>
            <a  class="dropdown-item" href="<c:url value = "${param.path4}"/>">
                <spring:message code="order.Z_A"/>
            </a>
            <a  class="dropdown-item" href="<c:url value = "${param.path5}"/>">
                <spring:message code="order.low_price"/>
            </a>
            <a  class="dropdown-item" href="<c:url value = "${param.path6}"/>">
                <spring:message code="order.high_price"/>
            </a>
        </ul>
    </div>
</div>
