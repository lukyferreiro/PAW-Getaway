import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {CategoryModel, CityModel, CountryModel} from "../types";
import {categoryService, experienceService, locationService} from "../services";
import {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {useForm} from "react-hook-form";
import {Navigate, useLocation, useNavigate} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";

type FormDataExperience = {
    name: string,
    category: number,
    country: string,
    city: string,
    address: string,
    price: string,
    url: string,
    mail: string,
    description: string
};

export default function CreateExperience() {

    const {t} = useTranslation();
    const navigate = useNavigate();


    const [categories, setCategories] = useState<CategoryModel[]>(new Array(1))
    const [countries, setCountries] = useState<CountryModel[]>(new Array(1))
    const [cities, setCities] = useState<CityModel[]>(new Array(0))

    useEffect(() => {
        serviceHandler(
            categoryService.getCategories(),
            navigate, (category) => {
                setCategories(category)
            },
            () => {}
        ) ;
        serviceHandler(
            locationService.getCountries(),
            navigate, (country) => {
                setCountries(country)
            },
            () => {}
        ) ;
    }, [])

    function loadCities(countryName: string){
        serviceHandler(
            locationService.getCitiesByCountry(parseInt(countryName)),
            navigate, (city) => {
                setCities(city)
            },
            () => {}
        ) ;
    }
    const {register, handleSubmit, formState: { errors },}
        = useForm<FormDataExperience>({ criteriaMode: "all" });


    const onSubmit = handleSubmit((data: FormDataExperience) => {
            experienceService.createExperience(data.name, data.category, data.country, data.city,
                data.address, data.price.toString(), data.url, data.mail, data.description)
                .then((result) => {
                    if (!result.hasFailed()) {
                        navigate(result.getData().url.toString(), {replace: true})
                    }
                    }
                )
                .catch(() => {});
        }
    );

    const {user} = useAuth();
    const location = useLocation();
    const readUser = localStorage.getItem("user");
    const isVerified = localStorage.getItem("isVerified") === "true";
    const isProvider = localStorage.getItem("isProvider") === "true";
    const rememberMe = localStorage.getItem("rememberMe") === "true";

    if (!user && !readUser) {
        return <Navigate to="/login" state={{from: "/createExperience"}} replace/>;
    }
    if (!isVerified) {
        return <Navigate to="/user/profile" replace/>;
    }


    return (
        <div className="d-flex flex-column justify-content-center mx-5 my-2 p-0">
            <h2 className="text-center font-weight-bold">
                {t('CreateExperience.title')}
            </h2>

            <form id="createExperienceForm" acceptCharset="utf-8"
                  onSubmit={onSubmit} method="post">
                <div className="container-inputs">
                    <div className="p-0 m-0 d-flex">
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between"
                                   htmlFor="experienceName">
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
                            <input max="50" type="text" className="form-control"
                                   {...register("name", {
                                       required: true,
                                       max: 50,
                                       pattern: {
                                           value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                           message: t("CreateExperience.error.name.pattern"),
                                       },
                                   })}
                            />
                            {errors.name?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.name.isRequired")}
                                </p>
                            )}
                            {errors.name?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.name.max")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label className="form-label" htmlFor="experienceCategory">
                                {t('Experience.category')}
                                <span className="required-field">*</span>
                            </label>
                            <select className="form-select" required
                                    {...register("category", {required: true})}
                            >
                                <option hidden value="">{t('Experience.placeholder')}</option>

                                {/*<c:if test="${formCategory == null}">*/}
                                {/*    <option value="" disabled selected hidden>*/}
                                {/*        <c:out value="${placeholder}"/>*/}
                                {/*    </option>*/}
                                {/*</c:if>*/}
                                {categories.map((category) => (
                                    <option key={category.id} value={category.id}>
                                        {category.name}
                                    </option>
                                ))}
                            </select>
                            {errors.category?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.category.isRequired")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between"
                                   htmlFor="price">
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
                            <input type="number" max="9999999" className="form-control" id="experienceFormPriceInput" placeholder="0"
                                   {...register("price", {})}
                            />
                        </div>
                    </div>

                    <div className="p-0 m-2 d-flex flex-column">
                        <label className="form-label d-flex justify-content-between"
                                htmlFor="description">
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
                        <textarea maxLength={500} className="form-control" style={{maxHeight: "300px"}}
                                  {...register("description", {
                                      required: false,
                                      maxLength: 500,
                                      pattern: {
                                          value: /^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°";$%#&=:¿?!¡\n\s\t/.-])*$/,
                                          message: t("CreateExperience.error.description.pattern"),
                                      },
                                  })}
                        />
                        {errors.description?.type === "required" && (
                            <p className="form-control is-invalid form-error-label">
                                {t("CreateExperience.error.description.isRequired")}
                            </p>
                        )}
                        {errors.description?.type === "maxLength" && (
                            <p className="form-control is-invalid form-error-label">
                                {t("CreateExperience.error.description.max")}
                            </p>
                        )}
                    </div>

                    <div className="p-0 m-0 d-flex">
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between"
                                    htmlFor="mail">
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
                                   placeholder={t('Experience.mail.placeholder')}
                                   {...register("mail", {
                                       required: true,
                                       max: 250,
                                       pattern: {
                                           value: /^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$/,
                                           message: t("CreateExperience.error.mail.pattern"),
                                       },
                                   })}
                            />
                            {errors.mail?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.mail.isRequired")}
                                </p>
                            )}
                            {errors.mail?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.mail.max")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between"
                                    htmlFor="url">
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
                                   className="form-control" placeholder={t('Experience.url.placeholder')}
                                   {...register("url", {
                                       max: 500,
                                       pattern: {
                                           value: /^([(http(s)?):\/\/(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*))?$/,
                                           message: t("CreateExperience.error.url.pattern"),
                                       },
                                   })}
                            />
                            {errors.mail?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.url.max")}
                                </p>
                            )}
                        </div>
                    </div>

                    <div className="p-0 m-0 d-flex">
                        <div className="col m-2">
                            <label className="form-label" htmlFor="country">
                                {t('Experience.country')}
                                <span className="required-field">*</span>
                            </label>
                            <select id="experienceFormCountryInput" className="form-select" required
                                    {...register("country", {required: true})}
                                    onChange={e => loadCities(e.target.value)}
                            >
                                <option hidden value="">{t('Experience.placeholder')}</option>

                                {/*<c:if test="${formCategory == null}">*/}
                                {/*    <option value="" disabled selected hidden>*/}
                                {/*        <c:out value="${placeholder}"/>*/}
                                {/*    </option>*/}
                                {/*</c:if>*/}
                                {countries.map((country) => (
                                    <option key={country.id} value={country.id} >
                                        {country.name}
                                    </option>
                                ))}
                            </select>
                            {errors.country?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.country.isRequired")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label className="form-label" htmlFor="city">
                                {t('Experience.city')}
                                <span className="required-field">*</span>
                            </label>
                            <select id="experienceFormCityInput" className="form-select" required
                                    {...register("city", {required: true})}
                                disabled={cities.length <= 0}
                            >
                                {cities.map((city) => (
                                    <option key={city.id} value={city.name}>
                                        {city.name}
                                    </option>
                                ))}
                            </select>
                            {errors.city?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.city.isRequired")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between"
                                    htmlFor="address">
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
                            <input max="100" type="text" className="form-control"
                                   {...register("address", {
                                       required: true,
                                       max: 100,
                                       pattern: {
                                           value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                           message: t("CreateExperience.error.address.pattern"),
                                       },
                                   })}
                            />
                            {errors.address?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.address.isRequired")}
                                </p>
                            )}
                            {errors.address?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateExperience.error.address.isRequired")}
                                </p>
                            )}
                        </div>
                    </div>

                </div>

                <div className="p-0 mt-3 mb-0 d-flex justify-content-around">
                    <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton"
                            onClick={() => navigate(-1)}>
                        {t('Button.cancel')}
                    </button>
                    <button className="btn btn-submit-form px-3 py-2" id="createExperienceFormButton"
                            form="createExperienceForm" type="submit">
                        {t('Button.create')}
                    </button>
                </div>
            </form>
        </div>
    )

}