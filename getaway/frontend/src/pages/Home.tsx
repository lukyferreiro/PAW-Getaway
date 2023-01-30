import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import Carrousel from "../components/Carrousel";
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
            {/*<Outlet/>*/}
            <Carrousel experiences={experiences} show={3}/>
        </div>

    )

}