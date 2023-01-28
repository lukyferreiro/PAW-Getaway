import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import CountryModel from "../types/CountryModel";
import CityModel from "../types/CityModel";
import {CategoryModel, ExperienceModel} from "../types";
import UserModel from "../types/UserModel";
import React from "react";
import {Link} from "react-router-dom";
import {IconButton} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import CardExperience from "../components/CardExperience";
import CardExperienceDetails from "../components/CardExperienceDetails";
export default function ExperienceDetails() {

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

    //TODO: Habria q recibirla por el id que se clickea e ir a buscar esta info a la api
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


    return (
        <div className="container-fluid px-5 d-flex justify-content-center align-content-center flex-column">
            <div className="card mx-5 my-3 px-5 pt-4">
                <div className="d-flex justify-content-center align-content-center">
                    <h1 className="text-center" style={{wordBreak: "break-all"}}>
                        {experience.name}
                    </h1>
                </div>

                <CardExperienceDetails experience={experience}
                                categoryModel={categoryModel}/>
            </div>

        </div>

    );
}