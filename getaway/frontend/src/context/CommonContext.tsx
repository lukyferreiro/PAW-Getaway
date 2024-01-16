import React, {createContext, ReactNode, useEffect, useState} from "react"
import {CategoryModel, CountryModel} from "../types"
import {authedFetch} from "../scripts/authedFetch";
import {paths} from "../common";
import {useNavigate} from "react-router-dom";

interface CommonContextType {
    categories: CategoryModel[]
    getCategory: (categoryId: number) => CategoryModel | null
    country?: CountryModel
    getCountry: () => CountryModel | null
}

export const CommonContext = createContext<CommonContextType>(null!)

export function CommonProvider({children}: { children: ReactNode }) {

    const navigate = useNavigate()

    const [categories, setCategories] = useState<CategoryModel[]>(new Array(0))
    const [country, setCountry] = useState<CountryModel | undefined>(undefined)

    const getCountry14 = async () => {
        try {
            const country =  await authedFetch(paths.BASE_URL + paths.LOCATION + '/14', {method: "GET"})
            const parsedCountry: CountryModel = await country.json() as CountryModel;
            setCountry(parsedCountry)
        } catch (error) {
            navigate('/error', {state: {code: 500, message: 'Server error',}, replace: true,})
        }
    };

    const getAllCategories = async () => {
        try {
            const categories =  await authedFetch(paths.BASE_URL + paths.EXPERIENCES + '/categories', {method: "GET"})
            const parsedCategories: CategoryModel[] = await categories.json() as CategoryModel[];
            setCategories(parsedCategories)
        } catch (error) {
            navigate('/error', {state: {code: 500, message: 'Server error',}, replace: true,})
        }
    };

    useEffect(() => {
        if (country !== undefined) {
            getCountry14()
        }
        if (categories.length === 0) {
            getAllCategories()
        }
    }, []);


    const getCategory = (categoryId: number) => {
        return categories.find(category => category.id === categoryId) || null;
    }

    const getCountry = () => {
        return country || null;
    }

    const value = {categories, getCategory, country, getCountry}

    return <CommonContext.Provider value={value}>{children}</CommonContext.Provider>
}
