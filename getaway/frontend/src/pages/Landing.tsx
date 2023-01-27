import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Outlet} from "react-router-dom";
import Carrousel from "../components/Carrousel";
import CountryModel from "../types/CountryModel";
import CityModel from "../types/CityModel";
import {CategoryModel, ExperienceModel} from "../types";
import UserModel from "../types/UserModel";
import CardExperience from "../components/CardExperience";

export default function Landing() {

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

    const experiences =
        [<CardExperience/>,
            <CardExperience/>,
            <CardExperience/>,
            <CardExperience/>,
            <CardExperience/>,
            <CardExperience/>
            // {
            // experienceId: 1,
            // name: "hola soy una prueba",
            // price: 10,
            // address: "SAN PEDRO 47",
            // email: "LFERREIRO@ITBA.EDU.AR",
            // description: "HOLA SOY LUQUITAS",
            // siteUrl: "HOLA.COM",
            // city: cityModel,
            // category: categoryModel,
            // user: userModel,
            // observable: true,
            // views: 200,
            // score: 5,
            // reviewsCount: 0},
            // {
            //     experienceId: 2,
            //     name: "hola soy una prueba 2",
            //     price: 10,
            //     address: "SAN PEDRO 47",
            //     email: "LFERREIRO@ITBA.EDU.AR",
            //     description: "HOLA SOY LUQUITAS",
            //     siteUrl: "HOLA.COM",
            //     city: cityModel,
            //     category: categoryModel,
            //     user: userModel,
            //     observable: true,
            //     views: 200,
            //     score: 5,
            //     reviewsCount: 0},
            // {
            //     experienceId: 3,
            //     name: "hola soy una prueba 3",
            //     price: 10,
            //     address: "SAN PEDRO 47",
            //     email: "LFERREIRO@ITBA.EDU.AR",
            //     description: "HOLA SOY LUQUITAS",
            //     siteUrl: "HOLA.COM",
            //     city: cityModel,
            //     category: categoryModel,
            //     user: userModel,
            //     observable: true,
            //     views: 200,
            //     score: 5,
            //     reviewsCount: 0}
        ]
    return (
        <div>
            {/*<Outlet/>*/}
            <Carrousel experiences={experiences} show={3}/>
        </div>

    )

}