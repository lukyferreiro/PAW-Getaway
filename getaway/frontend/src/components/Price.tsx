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
                        <h6>
                            {t('Experience.price.null')}
                        </h6>
                    </div>

                    :
                    (price === 0 ?
                            <div>
                                <h6>
                                    {t('Experience.price.free')}
                                </h6>
                            </div>
                            :
                            <div>
                                <h6>
                                    {t('Experience.price.exist', {price: price})}
                                </h6>
                            </div>
                    ))
            }
        </>
    );
}