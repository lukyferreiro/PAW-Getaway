import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {CategoryModel, CityModel, CountryModel, ExperienceModel} from "../types";
import {categoryService, experienceService, locationService} from "../services";
import React, {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {useForm} from "react-hook-form";
import {Navigate, useLocation, useNavigate} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";

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

export default function ExperienceForm() {

    const {t} = useTranslation();
    const navigate = useNavigate();
    const {user} = useAuth();

    const [experience, setExperience] = useState<ExperienceModel | undefined>(undefined)
    const [categories, setCategories] = useState<CategoryModel[]>(new Array(0))
    const [countries, setCountries] = useState<CountryModel[]>(new Array(0))
    const [cities, setCities] = useState<CityModel[]>(new Array(0))

    const query = useQuery();
    const currentId = getQueryOrDefault(query, "id", "-1");

    useEffect(() => {
        if (parseInt(currentId) != -1){
            serviceHandler(
                experienceService.getExperienceById(parseInt(currentId)),
                navigate, (fetchedExperience) => {
                    setExperience(fetchedExperience)
                    loadCities(fetchedExperience.country.id)
                },
                () => {}
            )
            if (experience?.user.id !== user?.id) {
                //TODO: add foribdden error
                navigate("/", {replace: true});
            }
        }
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

    function loadCities(countryId: number){
        serviceHandler(
            locationService.getCitiesByCountry(countryId),
            navigate, (city) => {
                setCities(city)
            },
            () => {}
        ) ;
    }
    const {register, handleSubmit, formState: { errors },}
        = useForm<FormDataExperience>({ criteriaMode: "all" });

    const onSubmit = handleSubmit((data: FormDataExperience) => {
            if (experience!==undefined){
                experienceService.updateExperienceById(parseInt(currentId),data.name, data.category, data.country, data.city,
                    data.address, data.price.toString(), data.url, data.mail, data.description)
                    .then((result) => {
                            if (!result.hasFailed()) {
                                navigate("/experiences/" + currentId, {replace: true})
                            }
                        }
                    )
                    .catch(() => {});
            }else {
                experienceService.createExperience(data.name, data.category, data.country, data.city,
                    data.address, data.mail, data.price.toString(), data.url, data.description)
                    .then((result) => {
                            if (!result.hasFailed()) {
                                console.log(result.getData().url.toString())
                                navigate(result.getData().url.toString(), {replace: true})
                            }
                        }
                    )
                    .catch(() => {});
            }
        }
    );

    const location = useLocation();
    const readUser = localStorage.getItem("user");
    const isVerified = localStorage.getItem("isVerified") === "true";
    const isProvider = localStorage.getItem("isProvider") === "true";
    const rememberMe = localStorage.getItem("rememberMe") === "true";

    if (!user && !readUser) {
        return <Navigate to="/login" state={{from: "/experienceForm"}} replace/>;
    }
    if (!isVerified) {
        return <Navigate to="/user/profile" replace/>;
    }


    return (
        <div className="d-flex flex-column justify-content-center mx-5 my-2 p-0">
            <h2 className="text-center font-weight-bold">
                {t('ExperienceForm.title')}
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
                                           message: t("ExperienceForm.error.name.pattern"),
                                       },
                                   })}
                                defaultValue={experience ? experience.name : ""}
                            />
                            {errors.name?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.name.isRequired")}
                                </p>
                            )}
                            {errors.name?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.name.max")}
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
                                {experience === undefined &&
                                    <option hidden value="">{t('Experience.placeholder')}</option>
                                }

                                {categories.map((category) => (
                                    <option defaultValue={experience ? experience.category.id : ""} key={category.id} value={category.id}>
                                        {t('Categories.' + category.name)}
                                    </option>
                                ))}
                            </select>
                            {errors.category?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.category.isRequired")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label className="form-label d-flex justify-content-between"
                                   htmlFor="price">
                                <div>
                                    {t('Experience.price.name')}
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
                                   defaultValue={experience ? experience.price : ""}
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
                                          message: t("ExperienceForm.error.description.pattern"),
                                      },
                                  })}
                            defaultValue={experience ? experience.description : ""}
                        />
                        {errors.description?.type === "required" && (
                            <p className="form-control is-invalid form-error-label">
                                {t("ExperienceForm.error.description.isRequired")}
                            </p>
                        )}
                        {errors.description?.type === "maxLength" && (
                            <p className="form-control is-invalid form-error-label">
                                {t("ExperienceForm.error.description.max")}
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
                                           message: t("ExperienceForm.error.mail.pattern"),
                                       },
                                   })}
                                   defaultValue={experience ? experience.email : ""}
                            />
                            {errors.mail?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.mail.isRequired")}
                                </p>
                            )}
                            {errors.mail?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.mail.max")}
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
                                           message: t("ExperienceForm.error.url.pattern"),
                                       },
                                   })}
                                   defaultValue={experience ? experience.siteUrl : ""}
                            />
                            {errors.url?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.url.max")}
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
                                    onChange={e => loadCities(parseInt(e.target.value))}
                            >
                                {experience === undefined &&
                                    <option hidden value="">{t('Experience.placeholder')}</option>
                                }

                                {countries.map((country) => (
                                    <option defaultValue={experience ? experience.country.id : ""} key={country.id} value={country.id}>
                                        {country.name}
                                    </option>
                                ))}
                            </select>
                            {errors.country?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.country.isRequired")}
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
                                disabled={cities.length <= 0 && experience === undefined }
                            >
                                {experience === undefined &&
                                <option hidden value="">{t('Experience.placeholder')}</option>
                                }

                                {cities.map((city) => (
                                    <option defaultValue={experience ? experience.city.id : ""} key={city.id} value={city.name}>
                                        {city.name}
                                    </option>
                                ))}
                            </select>
                            {errors.city?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.city.isRequired")}
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
                                           message: t("ExperienceForm.error.address.pattern"),
                                       },
                                   })}
                                defaultValue={experience ? experience.address : ""}
                            />
                            {errors.address?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.address.isRequired")}
                                </p>
                            )}
                            {errors.address?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.address.isRequired")}
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