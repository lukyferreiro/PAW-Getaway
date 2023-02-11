import "../styles/star_rating.css";
import React from "react";

export default function StarRating(props: { score: number }) {

    const {score} = props

    return (
        <div className="star-rating">
            {[...Array(5)].map((star, index) => {
                index -= 5;
                return (
                    <div
                        key={index}
                        className={index >= -score ? "on" : "off"}
                    >
                        <span className="star">&#9733;</span>
                    </div>
                );
            })}
        </div>
    );

}