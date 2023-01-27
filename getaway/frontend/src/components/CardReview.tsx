import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import CountryModel from "../types/CountryModel";
import CityModel from "../types/CityModel";
import {CategoryModel, ExperienceModel, ReviewModel} from "../types";
import UserModel from "../types/UserModel";
import { Link } from "react-router-dom";

export default function CardReview() {

    const {t} = useTranslation();

    const countryModel: CountryModel =
        {
            countryId: 2,
            name: "Pais campeon del mundo"
        }

    const cityModel: CityModel =
        {
            cityId: 1,
            name: "avellaneda city",
            country: countryModel
        }


    const categoryModel: CategoryModel =
        {categoryId: 1, name: 'Aventura'}


    const userModel: UserModel = {
        userId: 1,
        name: "LUCAS FERREIRO PA",
        surname: "LUCAS FERREIRO PA",
        email: "lferreiro@itba.edu.ar PA"
    }

    const experience: ExperienceModel =
        {
            experienceId: 1,
            name: "hola soy una prueba",
            price: 10,
            address: "SAN PEDRO 47",
            email: "LFERREIRO@ITBA.EDU.AR",
            description: "HOLA SOY LUQUITAS",
            siteUrl: "HOLA.COM",
            city: cityModel,
            category: categoryModel,
            user: userModel,
            observable: true,
            views: 200,
            score: 5,
            reviewsCount: 0}

    const reviewModel: ReviewModel = {
        reviewId: 1,
        title: "Holaaaaa",
        description: "HOLAAAAAAAAAAA",
        score: 2,
        date: "19/09/1999",
        experience: experience,
        user: userModel
    }


    return (
<div></div>

    //     <div className={"card m-2"}>
    //     <c:if test="${param.isEditing}">
    //         <div className="card-title m-2 d-flex justify-content-center align-content-center">
    //         <Link to="/experiences/${experience.category}/${experience.experienceId}">
    //         <c:param name="view" value="${true}"/></c:url>">
    //         <h4 class="text-center"
    //                         style="font-weight: bold; text-decoration: underline; word-break: break-all; color:black;">
    //                         <c:out value="${experience.name}"/>
    //                     </h4>
    //                 </Link>
    //
    //             </div>
    //         </c:if>
    //         <div className="card-title m-2 d-flex justify-content-between">
    //         <div className="d-flex">
    //          <c:choose>
    //              <c:when test="${param.hasImage}">
    //                  <img className="user-img" src="<c:url value='/user/profileImage/${param.profileImageId}'/>" alt="Imagen" />
    //                       style="margin-right: 8px"/>
    //              </c:when>
    //              <c:otherwise>
    //                 <img className="user-img" src="<c:url value=" />public/images/user_default.png/images/user_default.png" />" alt="Imagen"/>
    //            </c:otherwise>
    //          </c:choose>
    //         <div className="d-flex flex-column justify-content-center align-content-center">
    //         <h5 className="my-1">
    //         {t('review.user', {userName: UserModel.name}, {userSurname: UserModel.surname})}
    //     </h5>
    //         <h6 className="my-1" style="font-size: small;">
    //             {t('review.date', {reviewDate: ReviewModel.date})}
    //         </h6>
    //     </div>
    //     </div>
    // <div class="my-2 d-flex">
    //     {/*<jsp:include page="/WEB-INF/components/starAvg.jsp">*/}
    //     {/*    <jsp:param name="avgReview" value="${param.score}"/>*/}
    //     {/*</jsp:include>*/}
    // </div>
    // <div className="card-body m-2 p-0">
    //     <div className="card-text">
    //         <h2 className="m-0 align-self-center" style="font-size: x-large">
    //             <c:out value="${param.title}"/>
    //         </h2>
    //         <h6 className="m-0" style="font-size: medium;" id="reviewDescription">
    //             <c:out value="${param.description}"/>
    //         </h6>
    //         <c:if test="${!param.observable && param.isEditing}">
    //             <p className="obs-info">{t('experience.notVisible')}/></p>
    //         </c:if>
    //     </div>
    // </div>
    // <c:if test="${param.isEditing}">
    //     <div className="btn-group card-body container-fluid p-1 d-flex justify-content-center align-items-end"
    //          role="group">
    //         <Link to="/user/reviews/edit/${param.reviewId}/">
    //             <button type="button" class="btn btn-pencil" style="font-size: large">
    //                 <i className="bi bi-pencil"></i>
    //             </button>
    //         </Link>
    //         <Link="/>user/reviews/delete/${reviewModel.reviewId}"/>
    //         <button type="button" className="btn btn-trash" style="font-size: large">
    //             <i className="bi bi-trash"></i>
    //         </button>
    //     </Link>
    // </div>
    //
    // </c:if>
    //
    // <script src='<c:url value="/resources/js/revParse.js"/>'></script>

    );

}