import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import '../styles/carousel.css'
import React, {useEffect, useState} from "react";
import CardExperience from "./CardExperience";
import {ExperienceModel} from "../types";


export default function Carousel(props: { title: any, experiences: ExperienceModel[] | undefined; show: any; }) {

    const {t} = useTranslation();

    const {title, experiences, show} = props
    const [currentIndex, setCurrentIndex] = useState(0)
    const [length, setLength] = useState(0)

    // Set the length to match current children from props
    useEffect(() => {
        setLength(experiences === undefined ? 0 : experiences.length)
    }, [experiences])

    const next = () => {
        if (currentIndex < (length - show)) {
            setCurrentIndex(prevState => prevState + 1)
        }
    }

    const prev = () => {
        if (currentIndex > 0) {
            setCurrentIndex(prevState => prevState - 1)
        }
    }

    return (
        <div className="carousel-container">
            <div className="d-flex justify-content-center align-content-center">
                <h2>{title}</h2>
            </div>
            {experiences === undefined ?
                <div className="d-flex justify-content-center align-content-center">
                    <img src={'./images/ic_no_search.jpeg'} alt="Imagen lupa"
                         style={{
                             width: "150px",
                             height: "150px",
                             minWidth: "150px",
                             minHeight: "150px",
                             marginRight: "5px"
                         }}/>
                    <h4 className="d-flex align-self-center">
                        {t('Carousel.experienceEmpty')}
                    </h4>
                </div>
                :
                <div className="carousel-wrapper">
                    {currentIndex > 0 &&
                        <button onClick={prev} className="left-arrow">
                            &lt;
                        </button>
                    }

                    <div className="carousel-content-wrapper">
                        <div className={`carousel-content show-${show}`}
                             style={{transform: `translateX(-${currentIndex * (100 / show)}%)`}}>
                            {experiences.map((exp) => (
                                <CardExperience experience={exp} key={exp.id}/>
                            ))}
                        </div>
                    </div>

                    {currentIndex < (length - show) &&
                        <button onClick={next} className="right-arrow">
                            &gt;
                        </button>
                    }
                </div>
            }

        </div>
    );

}