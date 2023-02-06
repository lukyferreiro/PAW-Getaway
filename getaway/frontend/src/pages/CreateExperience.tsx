import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {CategoryModel, CityModel, CountryModel} from "../types";

export default function CreateExperience() {

    const {t} = useTranslation();

    //TODO obtenerlas de un llamado a la API ??
    const categories: CategoryModel[] = [
        {categoryId: 1, name: 'Aventura'},
        {categoryId: 2, name: 'Gastronomia'},
        {categoryId: 3, name: 'Hoteleria'},
        {categoryId: 4, name: 'Relax'},
        {categoryId: 5, name: 'Vida_nocturna'},
        {categoryId: 6, name: 'Historico'},
    ]

    //TODO obtenerlas de un llamado a la API ??
    const country: CountryModel = {
        countryId: 1,
        name: "Argentina",
    }

    //TODO obtenerlas de un llamado a la API ??
    const cities: CityModel[] = [
        {cityId: 1, name: "Lucas", country: country,},
        {cityId: 2, name: "Pedro", country: country,},
        {cityId: 3, name: "Carlos", country: country,},
    ]

    return (
        <div className="d-flex flex-column justify-content-center mx-5 my-2 p-0">
            <h2 className="text-center font-weight-bold">
                {t('CreateExperience.title')}
            </h2>

            {/*<c:url value="${endpoint}" var="postPath"/>*/}
            <form id="createExperienceForm">
                <div className="container-inputs">
                    <div className="p-0 m-0 d-flex">
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between">
                                <div>
                                    {t('Experience.name')}
                                    <span className="required-field">*</span>
                                </div>
                                <div className="align-self-center">
                                    <h6 className="max-input-text">
                                        {t('Input.maxValue', {value: 50})}
                                    </h6>
                                </div>
                            </label>
                            <input max="50" type="text" className="form-control"/>
                            {/*TODO ver como poner esto del css de error*/}
                            {/*cssErrorClass="form-control is-invalid"*/}
                            {/*<form:errors path="experienceName" element="p" cssClass="form-error-label"/>*/}
                        </div>
                        <div className="col m-2">
                            <label className="form-label">
                                {t('Experience.category')}
                                <span className="required-field">*</span>
                            </label>
                            {/*TODO ver como poner esto del css de error*/}
                            {/*cssErrorClass="form-control is-invalid"*/}
                            <select className="form-select">
                                {/*<c:if test="${formCategory == null}">*/}
                                {/*    <option value="" disabled selected hidden>*/}
                                {/*        <c:out value="${placeholder}"/>*/}
                                {/*    </option>*/}
                                {/*</c:if>*/}

                                {categories.map((category) => (
                                    <option selected>
                                        {t('Categories.' + category.name)}
                                    </option>
                                ))}
                            </select>
                            {/*TODO ver como poner esto del css de error*/}
                            {/*<form:errors path="experienceCategory" element="p" cssClass="form-error-label"/>*/}
                        </div>
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between">
                                <div>
                                    {t('Experience.price')}
                                    <span className="optional-text">
                                        {t('Input.optional')}
                                    </span>
                                </div>
                                <div className="align-self-center">
                                    <h6 className="max-input-text">
                                        {t('Input.maxValue', {value: 7})}
                                    </h6>
                                </div>
                            </label>
                            <input type="text" className="form-control" id="experienceFormPriceInput" placeholder="0"/>
                            {/*TODO ver como usar los errores*/}
                            {/*cssErrorClass="form-control is-invalid"*/}
                            {/*<form:errors path="experiencePrice" element="p" cssClass="form-error-label"/>*/}
                        </div>
                    </div>

                    <div className="p-0 m-2 d-flex flex-column">
                        <label className="form-label d-flex justify-content-between">
                            <div>
                                {t('Experience.information')}
                                <span className="optional-text">
                                    {t('Input.optional')}
                                </span>
                            </div>
                            <div className="align-self-center">
                                <h6 className="max-input-text">
                                    {t('Input.maxValue', {value: 500})}
                                </h6>
                            </div>
                        </label>
                        <textarea maxLength={500} className="form-control" style={{maxHeight: "300px"}}/>
                        {/*TODO ver como usar los errores*/}
                        {/*cssErrorClass="form-control is-invalid"*/}
                        {/*<form:errors path="experienceInfo" element="p" cssClass="form-error-label"/>*/}
                    </div>

                    <div className="p-0 m-0 d-flex">
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between">
                                <div>
                                    {t('Experience.mail.field')}
                                    <span className="required-field">*</span>
                                </div>
                                <div className="align-self-center">
                                    <h6 className="max-input-text">
                                        {t('Input.maxValue', {value: 255})}
                                    </h6>
                                </div>
                            </label>
                            <input max="255" type="email" className="form-control"
                                   placeholder={t('Experience.mail.placeholder')}/>
                            {/*TODO ver como usar los errores*/}
                            {/*cssErrorClass="form-control is-invalid"*/}
                            {/*<form:errors path="experienceMail" element="p" cssClass="form-error-label"/>*/}
                        </div>
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between">
                                <div>
                                    {t('Experience.url.field')}
                                    <span className="optional-text">
                                        {t('Input.optional')}
                                    </span>
                                </div>
                                <div className="align-self-center">
                                    <h6 className="max-input-text">
                                        {t('Input.maxValue', {value: 500})}
                                    </h6>
                                </div>
                            </label>
                            <input max="500" id="experienceFormUrlInput" type="text"
                                   className="form-control" placeholder={t('Experience.url.placeholder')}/>
                            {/*TODO ver como usar los errores*/}
                            {/*cssErrorClass="form-control is-invalid"*/}
                            {/*<form:errors path="experienceUrl" element="p" cssClass="form-error-label"/>*/}
                        </div>
                    </div>

                    <div className="p-0 m-0 d-flex">
                        <div className="col m-2">
                            <label className="form-label">
                                {t('Experience.country')}
                                <span className="required-field">*</span>
                            </label>
                            {/*TODO*/}
                            {/*cssErrorClass="form-control is-invalid"*/}
                            <select id="experienceFormCountryInput" className="form-select">
                                {/*<option disabled selected value>*/}
                                {/*    <c:out value="${placeholder}"/>*/}
                                {/*</option>*/}
                                <option selected>
                                    Argentina
                                </option>
                            </select>
                            {/*<form:errors path="experienceCountry" element="p" cssClass="form-error-label"/>*/}
                        </div>
                        <div className="col m-2">
                            <label className="form-label">
                                {t('Experience.city')}
                                <span className="required-field">*</span>
                            </label>
                            <select id="experienceFormCityInput" className="form-select">
                                {cities.map((city) => (
                                    <option selected>
                                        {city.name}
                                    </option>
                                ))}
                            </select>
                            {/*cssErrorClass="form-control is-invalid"*/}
                            {/*<form:errors path="experienceCity" element="p" cssClass="form-error-label"/>*/}
                        </div>
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between">
                                <div>
                                    {t('Experience.address')}
                                    <span className="required-field">*</span>
                                </div>
                                <div className="align-self-center">
                                    <h6 className="max-input-text">
                                        {t('Input.maxValue', {value: 100})}
                                    </h6>
                                </div>
                            </label>
                            <input max="100" type="text" className="form-control"/>
                            {/*TODO*/}
                            {/*cssErrorClass="form-control is-invalid"*/}
                            {/*<form:errors path="experienceAddress" element="p" cssClass="form-error-label"/>*/}
                        </div>
                    </div>

                    <div className="p-0 m-2 d-flex flex-column">
                        <label className="form-label">
                            {t('Experience.image')}
                            <span className="optional-text">
                                 {t('Input.optional')}
                            </span>
                        </label>
                        <input type="file" className="form-control"/>
                        {/*TODO*/}
                        {/*cssErrorClass="form-control is-invalid"*/}
                        {/*<form:errors path="experienceImg" element="p" cssClass="form-error-label"/>*/}
                    </div>
                </div>

                <div className="p-0 mt-3 mb-0 d-flex justify-content-around">
                    <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton">
                        {t('Button.cancel')}
                    </button>
                    <button type="submit" className="btn btn-submit-form px-3 py-2" id="createExperienceFormButton"
                            form="createExperienceForm">
                        {t('Button.createExperience')}
                    </button>
                </div>
            </form>
        </div>
    )

}