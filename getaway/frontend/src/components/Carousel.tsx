import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import '../styles/carousel.css'
import {useEffect, useState} from "react"; //will be added later


export default function Carousel(props: { title: any, experiences: any; show: any; }) {

    const {t} = useTranslation();

    const {title, experiences, show} = props
    const [currentIndex, setCurrentIndex] = useState(0)
    const [length, setLength] = useState(experiences.length)

    // Set the length to match current children from props
    useEffect(() => {
        setLength(experiences.length)
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
            <div className="carousel-wrapper">
                {
                    currentIndex > 0 &&
                    <button onClick={prev} className="left-arrow">
                        &lt;
                    </button>
                }
                <div className="carousel-content-wrapper">
                    <div
                        className={`carousel-content show-${show}`}
                        style={{transform: `translateX(-${currentIndex * (100 / show)}%)`}}
                    >
                        {experiences}
                    </div>
                </div>
                {
                    currentIndex < (length - show) &&
                    <button onClick={next} className="right-arrow">
                        &gt;
                    </button>
                }
            </div>
        </div>
    );

}