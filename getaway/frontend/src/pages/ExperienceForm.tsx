import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {CategoryModel, CityModel, CountryModel, ExperienceModel} from "../types";
import {categoryService, experienceService, locationService} from "../services";
import React, {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import {showToast} from "../scripts/toast";
import {Simulate} from "react-dom/test-utils";
import load = Simulate.load;

type FormDataExperience = {
    name: string,
    category: number,
    country: number,
    city: number,
    address: string,
    mail: string,
    price?: number,
    url?: string,
    description?: string
};

export default function ExperienceForm() {

    const {user, isVerified, isProvider, makeProvider} = useAuth()
    const readUser = localStorage.getItem("user");
    const isVerifiedValue = isVerified()
    const isProviderValue = isProvider()
    const navigate = useNavigate()

    const {t} = useTranslation()

    const [experience, setExperience] = useState<ExperienceModel | undefined>(undefined)
    const [categories, setCategories] = useState<CategoryModel[]>(new Array(0))
    const [countries, setCountries] = useState<CountryModel[]>(new Array(0))
    const [cities, setCities] = useState<CityModel[]>(new Array(0))

    const [country, setCountry] = useState<number>(-1)
    const [city, setCity] = useState<number>(-1)

    const [isLoadingData, setIsLoadingData] = useState(false)

    const query = useQuery()
    const currentId = getQueryOrDefault(query, "id", "-1")

    if (!user && !readUser) {
        navigate("/login")
        showToast(t('ExperienceForm.toast.forbidden.noUser'), 'error')
    }
    if (user && readUser && !isVerifiedValue) {
        navigate("/user/profile")
        showToast(t('ExperienceForm.toast.forbidden.notVerified'), 'error')
    }

    const {register, handleSubmit, reset, setValue, formState: {errors},}
        = useForm<FormDataExperience>({criteriaMode: "all"})

    useEffect(()=> {
        serviceHandler(
            categoryService.getCategories(),
            navigate, (category) => {
                setCategories(category)
            },
            () => {
            },
            () => {
                setCategories(new Array(0))
            }
        )
        serviceHandler(
            locationService.getCountries(),
            navigate, (country) => {
                setCountries(country)
            },
            () => {
            },
            () => {
                setCountries(new Array(0))
            }
        )
    }, [])

    useEffect(() => {
        if (parseInt(currentId) !== -1) {
            serviceHandler(
                experienceService.getExperienceById(parseInt(currentId), false),
                navigate, (fetchedExperience) => {
                    if (fetchedExperience.user.id !== user?.id) {
                        navigate("/", {replace: true})
                    }
                    setExperience(fetchedExperience)
                    loadCities(fetchedExperience.country.id)

                    setValue('name', fetchedExperience.name)
                    setValue('category', fetchedExperience.category.id)
                    setValue('country', fetchedExperience.country.id)
                    setCountry(fetchedExperience.country.id)
                    setValue('city', fetchedExperience.city.id)
                    setCity(fetchedExperience.city.id)
                    setValue('address', fetchedExperience.address)
                    setValue('mail', fetchedExperience.email)
                    setValue('price', fetchedExperience.price)
                    setValue('url', fetchedExperience.siteUrl)
                    setValue('description', fetchedExperience.description)
                },
                () => {
                },
                () => {
                    setExperience(undefined)
                }
            )
            document.title = `${t('PageName')} - ${t('PageTitles.experienceForm.edit')}`
        }
        else {
            document.title = `${t('PageName')} - ${t('PageTitles.experienceForm.create')}`
            reset()
            setExperience(undefined)
            setCities(new Array(0))
        }
    }, [currentId])


    function loadCities(countryId: number) {
        serviceHandler(
            locationService.getCitiesByCountry(countryId),
            navigate, (city) => {
                setCities(city)
            },
            () => {
            },
            () => {
                setCities(new Array(0))
            }
        )
    }

    function handleCountryChange(countryId: number) {
        setCountry(countryId)
        loadCities(countryId)
    }

    function checkUrl(url: string | undefined): string | undefined {
        if (
            url
            && url.length !== 0
            && !url.startsWith("http://")
            && !url.startsWith("https://")
        ) {
            url = "https://" + url;
        }
        return url;
    }

    const onSubmit = handleSubmit((data: FormDataExperience) => {
            const newUrl = checkUrl(data.url)

            if (experience !== undefined) {
                experienceService.updateExperienceById(parseInt(currentId), data.name, data.category, data.country, data.city,
                    data.address, data.mail, data.price, newUrl, data.description)
                    .then((result) => {
                            if (!result.hasFailed()) {
                                navigate("/experiences/" + currentId, {replace: true})
                                showToast(t('ExperienceForm.toast.updateSuccess', {experienceName: data.name}), 'success')
                            }
                        }
                    )
                    .catch(() => {
                        showToast(t('ExperienceForm.toast.updateError', {experienceName: data.name}), 'error')
                    })
            } else {
                experienceService.createExperience(data.name, data.category, data.country, data.city,
                    data.address, data.mail, data.price, newUrl, data.description)
                    .then((result) => {
                            if (!result.hasFailed()) {
                                if (isProviderValue) {
                                    navigate("/user/experiences")
                                } else {
                                    makeProvider(() => navigate("/user/experiences"))
                                }
                                console.log(result.getData().url.toString())
                                showToast(t('ExperienceForm.toast.createSuccess', {experienceName: data.name}), 'success')
                            }
                        }
                    )
                    .catch(() => {
                        showToast(t('ExperienceForm.toast.createError', {experienceName: data.name}), 'error')
                    })
            }
        }
    )

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
                            <input min="3" max="50" type="text" className="form-control"
                                   {...register("name", {
                                       required: true,
                                       validate: {
                                           length: (name) =>
                                               name.length >= 3 && name.length <= 50,
                                       },
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
                            {errors.name?.type === "length" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.name.length")}
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
                                   {...register("price", {
                                       validate: {
                                           isNotGreater: (price) => {
                                               return (!price) || price <= 9999999
                                           },
                                       }
                                   })}
                                   defaultValue={experience ? experience.price : ""}
                            />
                            {errors.price && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.price.max")}
                                </p>
                            )}
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
                                      validate: {
                                          length: (description) =>
                                              (!description) || (description.length >= 0 && description.length <= 500),
                                      },
                                      pattern: {
                                          value: /^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°";$%#&=:¿?!¡\n\s\t/.-])*$/,
                                          message: t("ExperienceForm.error.description.pattern"),
                                      },
                                  })}
                                  defaultValue={experience ? experience.description : ""}
                        />
                        {errors.description?.type === "length" && (
                            <p className="form-control is-invalid form-error-label">
                                {t("ExperienceForm.error.description.length")}
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
                                       validate: {
                                           length: (mail) =>
                                               mail.length >= 0 && mail.length <= 250,
                                       },
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
                            {errors.mail?.type === "length" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.mail.length")}
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
                                       required: true,
                                       validate: {
                                           length: (url) =>
                                               (!url) || (url.length >= 0 && url.length <= 500),
                                       },
                                       pattern: {
                                           value: /^([(http(s)?):\/\/(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*))?$/,
                                           message: t("ExperienceForm.error.url.pattern"),
                                       },
                                   })}
                                   defaultValue={experience ? experience.siteUrl : ""}
                            />
                            {errors.url?.type === "length" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.url.length")}
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
                                    onChange={(e) => handleCountryChange(parseInt(e.target.value))}
                                    value={country}
                            >
                                {experience === undefined &&
                                    <option hidden value="">{t('Experience.placeholder')}</option>
                                }

                                {countries.map((country) => (
                                    <option key={country.id} value={country.id}>
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
                                    disabled={cities.length <= 0 && experience === undefined}
                                    onChange={(e) => setCity(parseInt(e.target.value))}
                                    value={city}
                            >
                                {experience === undefined &&
                                    <option hidden value="">{t('Experience.placeholder')}</option>
                                }

                                {cities.map((city) => (
                                    <option key={city.id} value={city.id}>
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
                            <input min="5" max="100" type="text" className="form-control"
                                   {...register("address", {
                                       required: true,
                                       validate: {
                                           length: (address) =>
                                               address.length >= 5 && address.length <= 100,
                                       },
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
                            {errors.address?.type === "length" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.address.max")}
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