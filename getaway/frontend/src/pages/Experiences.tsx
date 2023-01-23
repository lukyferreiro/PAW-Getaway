import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import OrderDropdown from "../components/OrderDropdown";

export default function Experiences() {

    const {t} = useTranslation();

    return (
        <div className="container-fluid p-0 mt-3 d-flex">
            <div className="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
                {/*FILTERS*/}
                <p className="filters-title m-0">
                    {t('Filters.title')}
                </p>

                <form id="submitForm" className="filter-form">
                    <div>
                        <label key="cityId" className="form-label">
                            {t('Filters.city')}
                        </label>
                        <select name="cityId" className="form-select" style={{boxShadow: "none"}}>
                            <option disabled selected>
                                {t('Filters.city.placeholder')}
                            </option>
                            {/*TODO cities traer api*/}
                            {/*<c:forEach var="city" items="${cities}">*/}
                            {/*    <option<c:if test="${city.cityId == cityId}"> selected</c:if> value="${city.cityId}">*/}
                            {/* <c:out value="${city.cityName}"/></option>*/}
                            {/*</c:forEach>*/}
                        </select>
                        {/*TODO ver como agregar esto*/}
                        {/*<form:errors path="cityId" element="p" cssClass="form-error-label"/>*/}
                    </div>

                    <div className="container-slider-price">
                        <label key="maxPrice" className="form-label">
                            {t('Filters.price.title')}
                        </label>
                        <output id="priceRange" name="priceRange" htmlFor="customRange">
                            {/*TODO obtener el max price de todas las experiencias*/}
                            100000
                        </output>
                        <div className="slider-price">
                            <div className="value left">
                                {t('Filters.price.min')}
                            </div>
                            <div className="slider">
                                {/*TODO ver como agregar esto al input*/}
                                {/*onInput={document.getElementById('priceRange').value = this.value | null}*/}
                                <input id="customRange" name="customRange" type="range"
                                       min="0" max="TODO" value="TODO"/>
                            </div>
                            <div className="value right">
                                {/*TODO obtener el maximo actual, usar useState*/}
                                100
                            </div>
                        </div>
                    </div>

                    <div>
                        <label className="form-label">
                            {t('Review.scoreAssign')}
                        </label>
                        {/*TODO poner las estrellas*/}
                        {/*<jsp:include page="/WEB-INF/components/starForm.jsp"/>*/}
                        {/*TODO ver como poner esto cuando el input falla*/}
                        {/*cssErrorClass="form-control is-invalid"*/}
                        <input value="TODO" type="hidden" className="form-control" id="scoreInput"/>
                        {/*TODO ver como poner esto*/}
                        {/*<form:errors path="score" element="p" cssClass="form-error-label"/>*/}
                    </div>
                </form>

                {/*TODO ver como se aplican todos los filtros*/}
                <button className="btn btn-search px-3 py-2 my-2" type="submit" id="submitFormButton" form="submitForm">
                    {t('Filters.btn.submit')}
                </button>


                {/*TODO ver como se borran todos los filtros*/}
                <button className="btn btn-clean-filter px-3 py-2 my-2" type="button" id="cleanFilterFormButton"
                        form="submitForm">
                    {t('Filters.btn.clear')}
                </button>
            </div>

            <div className="container-experiences container-fluid p-0 mx-2 mt-0 mb-3 d-flex
                            flex-column justify-content-center align-content-center"
                 style={{minHeight: "650px"}}>
                <div className="d-flex justify-content-start">
                    <OrderDropdown/>
                    {/*<jsp:include page="/WEB-INF/components/orderDropdown.jsp">*/}
                    {/*    <jsp:param name="orderByModels" value="${orderByModels}"/>*/}
                    {/*    <jsp:param name="path" value="${path}"/>*/}
                    {/*    <jsp:param name="score" value="${score}"/>*/}
                    {/*    <jsp:param name="cityId" value="${cityId}"/>*/}
                    {/*    <jsp:param name="maxPrice" value="${maxPrice}"/>*/}
                    {/*    <jsp:param name="orderPrev" value="${orderBy}"/>*/}
                    {/*</jsp:include>*/}
                </div>

                {/*TODO*/}
                {/*<c:choose>*/}
                {/*    <c:when test="${experiences.size() == 0}">*/}
                <div className="my-auto mx-5 px-3 d-flex justify-content-center align-content-center">
                    <div className="d-flex justify-content-center align-content-center">
                        <img src={'./images/ic_no_search.jpeg'} alt="Imagen lupa"
                             style={{width: "150px", height: "150px", minWidth: "150px", minHeight: "150px", marginRight: "5px"}}/>
                            <h1 className="d-flex align-self-center">
                                {t('Experience.emptyResult')}
                            </h1>
                    </div>
                </div>

                {/*TODO*/}
                {/*                    </c:when>*/}
                {/*                    <c:otherwise>*/}
                {/*                        <div class="d-flex flex-wrap justify-content-center">*/}
                {/*                            <c:forEach var="experience" varStatus="myIndex" items="${experiences}">*/}
                {/*                                <jsp:include page="/WEB-INF/components/cardExperience.jsp">*/}
                {/*                                    <jsp:param name="hasImage" value="${experience.image != null}"/>*/}
                {/*                                    <jsp:param name="categoryName" value="${experience.category.categoryName}"/>*/}
                {/*                                    <jsp:param name="id" value="${experience.experienceId}"/>*/}
                {/*                                    <jsp:param name="name" value="${experience.experienceName}"/>*/}
                {/*                                    <jsp:param name="isFav" value="${experience.isFav}"/>*/}
                {/*                                    <jsp:param name="description" value="${experience.description}"/>*/}
                {/*                                    <jsp:param name="address" value="${experience.getLocationName()}"/>*/}
                {/*                                    <jsp:param name="price" value="${experience.price}"/>*/}
                {/*                                    <jsp:param name="path" value="/experiences/${categoryName}"/>*/}
                {/*                                    <jsp:param name="score" value="${score}"/>*/}
                {/*                                    <jsp:param name="cityId" value="${cityId}"/>*/}
                {/*                                    <jsp:param name="maxPrice" value="${maxPrice}"/>*/}
                {/*                                    <jsp:param name="filter" value="true"/>*/}
                {/*                                    <jsp:param name="pageNum" value="${currentPage}"/>*/}
                {/*                                    <jsp:param name="orderBy" value="${orderBy}"/>*/}
                {/*                                    <jsp:param name="avgReviews" value="${experience.averageScore}"/>*/}
                {/*                                    <jsp:param name="reviewCount" value="${experience.reviewCount}"/>*/}
                {/*                                    <jsp:param name="observable" value="${experience.observable}"/>*/}
                {/*                                </jsp:include>*/}
                {/*                            </c:forEach>*/}
                {/*                        </div>*/}

                {/*                        <div class="mt-auto d-flex justify-content-center align-items-center">*/}
                {/*                            <ul class="pagination m-0">*/}
                {/*                                <li class="page-item">*/}
                {/*                                    <a class="page-link "*/}
                {/*                                       href="<c:url value = "/experiences/${categoryName}">*/}
                {/*                                          <c:param name = " pageNum" value = "1"/>*/}
                {/*                                    <c:param name="score" value="${score}"/>*/}
                {/*                                    <c:param name="cityId" value="${cityId}"/>*/}
                {/*                                    <c:param name="maxPrice" value="${maxPrice}"/>*/}
                {/*                                    <c:param name="orderBy" value="${orderBy}"/>*/}
                {/*                                </c:url>*/}
                {/*                                ">*/}
                {/*                                <spring:message code="pagination.start"/>*/}
                {/*                            </a>*/}
                {/*                        </li>*/}
                {/*                        <c:forEach var="i" begin="${minPage}" end="${maxPage}">*/}
                {/*                            <li class="page-item">*/}
                {/*                                <a class="page-link ${i == currentPage ? 'current-page-link' : ''}"*/}
                {/*                                   href="<c:url value = "/experiences/${categoryName}">*/}
                {/*                                          <c:param name = " pageNum" value = " ${i}"/>*/}
                {/*                                          <c:param name = " score" value = " ${score}"/>*/}
                {/*                                          <c:param name = " cityId" value = " ${cityId}"/>*/}
                {/*                                          <c:param name = " maxPrice" value = " ${maxPrice}"/>*/}
                {/*                                          <c:param name = " orderBy" value = " ${orderBy}" />*/}
                {/*                                          </c:url>">*/}
                {/*                                    <c:out value="${i}"/>*/}
                {/*                                </a>*/}
                {/*                            </li>*/}
                {/*                        </c:forEach>*/}
                {/*                        <li class="page-item">*/}
                {/*                            <a class="page-link "*/}
                {/*                               href="<c:url value = "/experiences/${categoryName}">*/}
                {/*                                          <c:param name = " pageNum" value = " ${totalPages}"/>*/}
                {/*                                          <c:param name = " score" value = " ${score}"/>*/}
                {/*                                          <c:param name = " cityId" value = " ${cityId}"/>*/}
                {/*                                          <c:param name = " maxPrice" value = " ${maxPrice}"/>*/}
                {/*                                          <c:param name = " orderBy" value = " ${orderBy}" />*/}
                {/*                                    </c:url>">*/}
                {/*                                <spring:message code="pagination.end"/>*/}
                {/*                            </a>*/}
                {/*                        </li>*/}
                {/*                    </ul>*/}
                {/*            </div>*/}
                {/*        </c:otherwise>*/}
                {/*</c:choose>*/}
            </div>
        </div>
    )
}