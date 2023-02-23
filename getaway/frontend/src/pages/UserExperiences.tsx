import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Link, useNavigate, useSearchParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {ExperienceModel, OrderByModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService, userService} from "../services";
import StarRating from "../components/StarRating";
import {IconButton} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import {useForm} from "react-hook-form";
import Pagination from "../components/Pagination";
import OrderDropdown from "../components/OrderDropdown";
import {Close} from "@mui/icons-material";
import DataLoader from "../components/DataLoader";
import ConfirmDialogModal, { confirmDialogModal } from "../components/ConfirmDialogModal";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import AddPictureModal from "../components/AddPictureModal";
// @ts-ignore
import VisibilityIcon from "@mui/icons-material/Visibility";
// @ts-ignore
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import {showToast} from "../scripts/toast";

type FormUserExperiencesSearch = {
    name: string
};

export default function UserExperiences() {

    const isProvider = localStorage.getItem("isProvider") === "true"
    const navigate = useNavigate()

    const {t} = useTranslation()

    const [searchParams, setSearchParams] = useSearchParams();
    const query = useQuery()

    const [userExperiences, setUserExperiences] = useState<ExperienceModel[]>(new Array(0))
    const [isLoading, setIsLoading] = useState(false)
    const [showModal, setShowModal] = useState(false)
    const isOpenImage = useState(false)

    const [userName, setUserName] = useState("")
    const [orders, setOrders] = useState<OrderByModel[]>(new Array(0))
    const order = useState<string>(getQueryOrDefault(query, "order", "OrderByAZ"))
    const [maxPage, setMaxPage] = useState(1)
    const currentPage = useState<number>(parseInt(getQueryOrDefault(query, "page", "1")))
    const [onEdit, setOnEdit] = useState(false)

    const {user} = useAuth()

    const {register, handleSubmit, formState: {errors}, reset}
        = useForm<FormUserExperiencesSearch>({criteriaMode: "all"})

    useEffect(() => {
        if (!isProvider) {
            navigate("/")
            showToast(t('User.toast.experiences.forbidden'), 'error')
        }
        serviceHandler(
            experienceService.getProviderOrderByModels(),
            navigate, (orders) => {
                setOrders(orders)
            },
            () => {
            },
            () => {
                setOrders(new Array(0))
            }
        );
    }, [])

    useEffect(() => {
        setOnEdit(false)
        setIsLoading(true);
        serviceHandler(
            userService.getUserExperiences(user ? user.id : -1, userName, order[0], currentPage[0]),
            navigate, (experiences) => {
                setUserExperiences(experiences.getContent())
                setMaxPage(experiences ? experiences.getMaxPage() : 1)
                searchParams.set("order", order[0])
                searchParams.set("page", currentPage[0].toString())
                setSearchParams(searchParams)
            },
            () => {
                setIsLoading(false);
            }, () => {
                setUserExperiences(new Array(0))
                setMaxPage(1)
            }
        )
    }, [currentPage[0], userName, order[0], onEdit])

    const onSubmit = handleSubmit((data: FormUserExperiencesSearch) => {
        setUserName(data.name);
    });

    function setVisibility(experience: ExperienceModel, visibility: boolean) {
        experienceService.setExperienceObservable(experience.id, visibility)
            .then(() => {
                if (visibility) {
                    showToast(t('Experience.toast.visibilitySuccess', {experienceName: experience.name}), "success")
                } else {
                    showToast(t('Experience.toast.noVisibilitySuccess', {experienceName: experience.name}), "success")
                }
            })
            .catch(() => {
                showToast(t('Experience.toast.visibilityError', {experienceName: experience.name}), "error")
            });
    }

    const deleteExperience = (experience: ExperienceModel) => {
        experienceService.deleteExperienceById(experience.id)
            .then(() => {
                showToast(t('Experience.toast.deleteSuccess', {experienceName: experience.name}), "success")
            })
            .catch(() => {
                showToast(t('Experience.toast.deleteError', {experienceName: experience.name}), "error")
            });
        setOnEdit(true)
    }

    function editExperience(experienceId: number) {
        navigate({pathname: "/experienceForm", search: `?id=${experienceId}`}, {replace: true});
    }

    function resetForm() {
        setUserName("")
        reset()
    }

    return (
        <DataLoader spinnerMultiplier={2} isLoading={isLoading}>
            <div className="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
                {(userExperiences.length === 0 && userName.length === 0) ?
                    <div className="my-auto d-flex justify-content-center align-content-center">
                        <h2 className="title">
                            {t('User.noExperiences')}
                        </h2>
                    </div>
                    :
                    <>
                        {/*SEARCH and ORDER*/}
                        <div className="d-flex justify-content-center align-content-center">
                            <div style={{margin: "0 auto 0 20px", flex: "1"}}>
                                <OrderDropdown orders={orders} order={order} currentPage={currentPage}/>
                            </div>

                            <h3 className="title m-0">
                                {t('User.experiencesTitle')}
                            </h3>

                            <div className="d-flex justify-content-center align-content-center"
                                 style={{margin: "0 20px 0 auto", flex: "1"}}>
                                <button className="btn btn-search-navbar p-0" type="submit"
                                        form="searchExperiencePrivateForm">
                                    <img src={'./images/ic_lupa.svg'} alt="Icono lupa"/>
                                </button>
                                <form className="my-auto" id="searchExperiencePrivateForm" onSubmit={onSubmit}>
                                    <input type="text" className="form-control" placeholder={t('Navbar.search')}
                                           {...register("name", {
                                               max: 255,
                                               pattern: {
                                                   value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                                   message: t("ExperienceForm.error.name.pattern"),
                                               }
                                           })}
                                           defaultValue={""}
                                    />
                                    {errors.name?.type === "max" && (
                                        <p className="form-control is-invalid form-error-label">
                                            {t("ExperienceForm.error.name.max")}
                                        </p>
                                    )}
                                </form>
                                <IconButton onClick={resetForm}>
                                    <Close/>
                                </IconButton>
                            </div>
                        </div>

                        <div className="mt-4 mx-5">
                            {userExperiences.length === 0 ?
                                <div className="my-auto mx-5 px-3 d-flex justify-content-center align-content-center">
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
                                            {t('EmptyResult')}
                                        </h4>
                                    </div>
                                </div>
                                :
                                <div>
                                    <table className="table table-bordered table-hover table-fit">
                                        <thead className="table-light">
                                        <tr key={1}>
                                            <th scope="col">
                                                <h4 className="table-title"> {t('User.experiences.title')}</h4>
                                            </th>
                                            <th scope="col">
                                                <h4 className="table-title"> {t('User.experiences.category')}</h4>
                                            </th>
                                            <th scope="col">
                                                <h4 className="table-title"> {t('User.experiences.score')}</h4>
                                            </th>
                                            <th scope="col">
                                                <h4 className="table-title"> {t('User.experiences.price')}</h4>
                                            </th>
                                            <th scope="col">
                                                <h4 className="table-title"> {t('User.experiences.views')}</h4>
                                            </th>
                                            <th scope="col">
                                                <h4 className="table-title"> {t('User.experiences.actions')}</h4>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {userExperiences.map((experience) => (
                                            <>
                                                <tr key={experience.id}>
                                                    <th scope="row">
                                                        <div className="title-link" style={{width: "350px"}}>
                                                            <Link to={"/experiences/" + experience.id}>
                                                                <h4 className="experience card-title container-fluid p-0"
                                                                    style={{wordBreak: "break-all"}}>
                                                                    {experience.name}
                                                                </h4>
                                                            </Link>
                                                        </div>
                                                    </th>
                                                    <td>
                                                        <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                            <h4 className="container-fluid p-0">
                                                                {t('Categories.' + experience.category.name)}
                                                            </h4>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                            <h5 className="mb-1">
                                                                {t("User.experiences.reviewsCount", {count: experience.reviewCount})}
                                                            </h5>
                                                            <StarRating score={experience.score}/>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                            <h5 className="mb-1">
                                                                {
                                                                    (experience.price === undefined ?
                                                                        <div>
                                                                            <h6>
                                                                                {t('Experience.price.null')}
                                                                            </h6>
                                                                        </div>

                                                                        :
                                                                        (experience.price === 0 ?
                                                                                <div>
                                                                                    <h6>
                                                                                        {t('Experience.price.free')}
                                                                                    </h6>
                                                                                </div>
                                                                                :
                                                                                <div>
                                                                                    <h6>
                                                                                        {t('Experience.price.exist', {price: experience.price})}
                                                                                    </h6>
                                                                                </div>
                                                                        ))
                                                                }
                                                            </h5>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                            <h5 className="mb-1">
                                                                {experience.views}
                                                            </h5>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div
                                                            className="btn-group w-auto container-fluid p-2 d-flex align-items-end"
                                                            role="group">
                                                            {experience.observable ?
                                                                <IconButton
                                                                    onClick={() => setVisibility(experience, false)}
                                                                    aria-label="visibilityOn" component="span"
                                                                    style={{fontSize: "x-large"}} id="setFalse">
                                                                    <VisibilityIcon/>
                                                                </IconButton>
                                                                :
                                                                <IconButton
                                                                    onClick={() => setVisibility(experience, true)}
                                                                    aria-label="visibilityOff" component="span"
                                                                    style={{fontSize: "xx-large"}} id="setTrue">
                                                                    <VisibilityOffIcon/>
                                                                </IconButton>
                                                            }

                                                            <IconButton onClick={() => editExperience(experience.id)}
                                                                        aria-label="edit" component="span"
                                                                        style={{fontSize: "x-large"}}>
                                                                <EditIcon/>
                                                            </IconButton>

                                                            {/*TODO: check*/}
                                                            {/*<IconButton*/}
                                                            {/*    onClick={() => {*/}
                                                            {/*        isOpenImage[1](true)*/}
                                                            {/*    }}*/}
                                                            {/*    aria-label="picture"*/}
                                                            {/*    component="span"*/}
                                                            {/*    style={{fontSize: "xx-large"}}>*/}
                                                            {/*    <AddPhotoAlternateIcon/>*/}
                                                            {/*</IconButton>*/}

                                                            <IconButton onClick={() => confirmDialogModal(t('User.experiences.deleteTitle'), t('User.experiences.confirmDelete',{experienceName: experience.name}),() => deleteExperience(experience))}
                                                                        aria-label="trash" component="span"
                                                                        style={{fontSize: "x-large"}}>
                                                                <DeleteIcon/>
                                                            </IconButton>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <AddPictureModal isOpen={isOpenImage} experienceId={experience.id}/>
                                            </>
                                            ))}
                                        </tbody>
                                    </table>


                                    <div className="mt-auto d-flex justify-content-center align-items-center">
                                        {maxPage > 1 && (
                                            <Pagination
                                                currentPage={currentPage}
                                                maxPage={maxPage}
                                            />
                                        )}
                                    </div>
                                </div>
                            }
                        </div>
                    </>
                }
            </div>
            <ConfirmDialogModal/>
        </DataLoader>
    );

}