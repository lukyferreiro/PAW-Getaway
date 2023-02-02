import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import Carousel from "../components/Carousel";
import CardExperience from "../components/CardExperience";

export default function Home() {

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
            <Carousel title={"Las experiencias mas buscadas"} experiences={experiences} show={3}/>
            <Carousel title={"Segun tus favs"} experiences={experiences} show={3}/>
        </div>

    )

}