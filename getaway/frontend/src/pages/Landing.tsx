import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Outlet} from "react-router-dom";
import Carousel from "../components/Carousel";
import CountryModel from "../types/CountryModel";
import CityModel from "../types/CityModel";
import {CategoryModel, ExperienceModel} from "../types";
import UserModel from "../types/UserModel";
import CardExperience from "../components/CardExperience";

export default function Landing() {

    const {t} = useTranslation();
    const experiences =
        [<CardExperience/>,
            <CardExperience/>,
            <CardExperience/>,
            <CardExperience/>,
            <CardExperience/>,
            <CardExperience/>
        ]
    return (
        <div>
            <Outlet/>
        </div>

    )

}