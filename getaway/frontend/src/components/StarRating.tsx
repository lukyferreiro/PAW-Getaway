import "../styles/star_rating.css";
import {useState} from "react";
export default function StarRating() {

    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(0);

    return (
        <div className="star-rating">
            {[...Array(5)].map((star, index) => {
                index -=5;
                return (
                    <button
                        type="button"
                        key={index}
                        className={index >= ((rating && hover) || hover) ? "on" : "off"}
                        onClick={() => setRating(index)}
                        onMouseEnter={() => setHover(index)}
                        onMouseLeave={() => setHover(rating)}
                    >
                        <span className="star">&#9733;</span>
                    </button>
                );
            })}
        </div>
    );

}