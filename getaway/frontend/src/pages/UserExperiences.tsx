import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useNavigate, useSearchParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {ExperienceModel, OrderByModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService, userService} from "../services";
import {IconButton} from "@mui/material";
import {useForm} from "react-hook-form";
import Pagination from "../components/Pagination";
import OrderDropdown from "../components/OrderDropdown";
import {Close} from "@mui/icons-material";
import DataLoader from "../components/DataLoader";
import ConfirmDialogModal from "../components/ConfirmDialogModal";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import AddPictureModal from "../components/AddPictureModal";
import {showToast} from "../scripts/toast";
import UserExperiencesTable from "../components/UserExperiencesTable";
// @ts-ignore
import VisibilityIcon from "@mui/icons-material/Visibility";
// @ts-ignore
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";


type FormUserExperiencesSearch = {
    name: string
};

export default function UserExperiences() {

    const navigate = useNavigate()

    const {t} = useTranslation()
    const {user, isProvider} = useAuth()

    const isProviderValue = isProvider
    const [searchParams, setSearchParams] = useSearchParams();
    const query = useQuery()

    const [userExperiences, setUserExperiences] = useState<ExperienceModel[]>(new Array(0))
    const experienceId = useState(-1)
    const [isLoading, setIsLoading] = useState(false)
    const isOpenImage = useState(false)

    const [userName, setUserName] = useState("")
    const [orders, setOrders] = useState<OrderByModel[]>(new Array(0))
    const order = useState<string>(getQueryOrDefault(query, "order", "OrderByAZ"))
    const [maxPage, setMaxPage] = useState(1)
    const currentPage = useState<number>(parseInt(getQueryOrDefault(query, "page", "1")))
    const onEdit = useState(false)

    const {register, handleSubmit, formState: {errors}, reset}
        = useForm<FormUserExperiencesSearch>({criteriaMode: "all"})

    useEffect(() => {
        if (!isProviderValue) {
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
        document.title = `${t('PageName')} - ${t('PageTitles.userExperiences')}`
    }, [])

    useEffect(() => {
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
    }, [currentPage[0], userName, order[0], onEdit[0]])

    const onSubmit = handleSubmit((data: FormUserExperiencesSearch) => {
        setUserName(data.name);
    });

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
                                <>
                                    <UserExperiencesTable experiences={userExperiences}
                                                          onEdit={onEdit}
                                                          setExperienceId={experienceId[1]}
                                                          isOpenImage={isOpenImage}
                                    />

                                    <div className="mt-auto d-flex justify-content-center align-items-center">
                                        {maxPage > 1 && (
                                            <Pagination
                                                currentPage={currentPage}
                                                maxPage={maxPage}
                                            />
                                        )}
                                    </div>
                                </>
                            }
                        </div>
                    </>
                }
            </div>
            <ConfirmDialogModal/>
            <AddPictureModal isOpen={isOpenImage} experienceId={experienceId[0]}/>
        </DataLoader>
    );

}