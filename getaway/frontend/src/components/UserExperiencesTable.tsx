import React, {Dispatch, SetStateAction} from "react";
import {useTranslation} from "react-i18next";
import {ExperienceModel} from "../types";
import UserExperiencesTableRow from "./UserExperiencesTableRow";

export default function UserExperiencesTable(props: {
    experiences: ExperienceModel[],
    onEdit: [boolean, Dispatch<SetStateAction<boolean>>],
    setExperienceId: React.Dispatch<React.SetStateAction<number>>,
    isOpenImage: [boolean, Dispatch<SetStateAction<boolean>>],
}) {

    const {t} = useTranslation()
    const {experiences, onEdit, setExperienceId, isOpenImage} = props

    return (
        <div>
            <table className="table table-bordered table-hover table-fit">
                <thead className="table-light">
                <tr>
                    <th scope="col" key={1}>
                        <h4 className="table-title"> {t('User.experiences.title')}</h4>
                    </th>
                    <th scope="col" key={2}>
                        <h4 className="table-title"> {t('User.experiences.category')}</h4>
                    </th>
                    <th scope="col" key={3}>
                        <h4 className="table-title"> {t('User.experiences.score')}</h4>
                    </th>
                    <th scope="col" key={4}>
                        <h4 className="table-title"> {t('User.experiences.price')}</h4>
                    </th>
                    <th scope="col" key={5}>
                        <h4 className="table-title"> {t('User.experiences.views')}</h4>
                    </th>
                    <th scope="col" key={6}>
                        <h4 className="table-title"> {t('User.experiences.actions')}</h4>
                    </th>
                </tr>
                </thead>
                <tbody>
                {experiences.map((experience) => (
                    <UserExperiencesTableRow experience={experience}
                                             onEdit={onEdit}
                                             setExperienceId={setExperienceId}
                                             isOpenImage={isOpenImage}
                                             key={experience.id}/>
                ))}
                </tbody>
            </table>

        </div>
    )

}