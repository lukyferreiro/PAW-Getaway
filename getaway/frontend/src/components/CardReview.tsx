import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import CountryModel from "../types/CountryModel";
import CityModel from "../types/CityModel";
import {CategoryModel, ExperienceModel, ReviewModel} from "../types";
import UserModel from "../types/UserModel";
import {Link} from "react-router-dom";
import {IconButton} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import React from "react";
import DeleteIcon from "@mui/icons-material/Delete";

export default function CardReview() {

    const {t} = useTranslation();

    const countryModel: CountryModel =
        {
            id: 2,
            name: "Pais campeon del mundo"
        }

    const cityModel: CityModel =
        {
            id: 1,
            name: "avellaneda city",
            country: countryModel
        }


    const categoryModel: CategoryModel =
        {id: 1, name: 'Aventura'}


    const userModel: UserModel = {
        id: 1,
        name: "LUCAS FERREIRO PA",
        surname: "LUCAS FERREIRO PA",
        email: "lferreiro@itba.edu.ar PA",
        favsCount: 0,
        verified: true,
        provider: true
    }

    const experience: ExperienceModel =
        {
            id: 1,
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
            reviewsCount: 0
        }

    const reviewModel: ReviewModel = {
        reviewId: 1,
        title: "Holaaaaa",
        description: "HOLAAAAAAAAAAA",
        score: 2,
        date: "19/09/1999",
        experience: experience,
        user: userModel
    }

    const isEditing = true;
    const hasImage = false;

    return (
        <div className="card m-2">
            {isEditing &&
                <div className="card-title m-2 d-flex justify-content-center align-content-center">
                    {/*            <a href="<c:url value="/experiences/${param.experienceCategory}/${param.experienceId}">*/}
                    {/*<c:param name=" view" value=" ${true}"/> </c:url>">*/}
                    <h4 className="text-center"
                        style={{
                            fontWeight: "bold",
                            textDecoration: "underline",
                            wordBreak: "break-all",
                            color: "black"
                        }}>
                        {experience.name}
                    </h4>
                    {/*</a>*/}

                </div>
            }

            <div className="card-title m-2 d-flex justify-content-between">
                <div className="d-flex">
                    {hasImage &&
                        <img className="user-img" src="<c:url value='/user/profileImage/${param.profileImageId}'/>"
                             alt="Imagen"
                             style={{marginRight: "8px"}}/>
                    }
                    {!hasImage &&
                        <img className="user-img"
                             src="./images/user_default.png" alt="Imagen"/>
                    }

                    <div className="d-flex flex-column justify-content-center align-content-center">
                        <h5 className="my-1">
                            {userModel.name} , {userModel.surname}
                        </h5>
                        <h6 className="my-1" style={{fontSize: "small"}}>
                            {reviewModel.date}
                        </h6>
                    </div>
                </div>
                <div className="my-2 d-flex">
                    {/*<jsp:include page="/WEB-INF/components/starAvg.jsp">*/}
                    {/*<jsp:param name="avgReview" value="${param.score}"/>*/}
                    {/*</jsp:include>*/}
                </div>
            </div>

            <div className="card-body m-2 p-0">
                <div className="card-text">
                    <h2 className="m-0 align-self-center" style={{fontSize: "x-large"}}>
                        {reviewModel.title}
                    </h2>
                    <h6 className="m-0" style={{fontSize: "medium"}} id="reviewDescription">
                        {reviewModel.description}
                    </h6>
                    {/* No me acuerdo si era chquear que la experience o la review sea observable*/}
                    {!experience.observable && isEditing &&
                        <p className="obs-info">
                            {t('ExperienceDetail.notVisible')}
                        </p>
                    }
                </div>
            </div>

            {isEditing &&
                <div className="btn-group card-body container-fluid p-1 d-flex justify-content-center align-items-end"
                     role="group">
                    {/*<a href="<c:url value="/user/reviews/edit/${param.reviewId}"/>">*/}
                    <IconButton aria-label="edit" component="span" style={{fontSize: "xx-large"}}>
                        <EditIcon/>
                    </IconButton>
                    {/*</a>*/}
                    {/*<a href="<c:url value="/user/reviews/delete/${param.reviewId}"/>">*/}
                    <IconButton aria-label="trash" component="span" style={{fontSize: "xx-large"}}>
                        <DeleteIcon/>
                    </IconButton>
                    {/*</a>*/}
                </div>
            }
        </div>
    );
}