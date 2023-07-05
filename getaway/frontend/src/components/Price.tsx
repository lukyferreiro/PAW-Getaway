import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import React from "react";

export default function Price(props: { price: number | undefined }) {

    const {t} = useTranslation()
    const {price} = props

    return (
        <>
            {
                (price === undefined ?
                    <div>
                        <p>
                            {t('Experience.price.null')}
                        </p>
                    </div>

                    :
                    (price === 0 ?
                            <div>
                                <p>
                                    {t('Experience.price.free')}
                                </p>
                            </div>
                            :
                            <div>
                                <p>
                                    {t('Experience.price.exist', {price: price})}
                                </p>
                            </div>
                    ))
            }
        </>
    );
}