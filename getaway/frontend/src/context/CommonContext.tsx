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

    const [categories, setCategories] = useState<CategoryModel[]>(new Array(0))
    const [country, setCountry] = useState<CountryModel | undefined>(undefined)

    const getCountry14 = async () => {
        try {
            console.log("ACA")
            const country =  await authedFetch(paths.BASE_URL + paths.LOCATION + '/14', {method: "GET"})
            console.log(paths.BASE_URL + paths.LOCATION + '/14')
            const parsedCountry: CountryModel = await country.json() as CountryModel;
            console.log(parsedCountry)
            setCountry(parsedCountry)
        } catch (error) {
        }
    };

    const getAllCategories = async () => {
        try {
            const categories =  await authedFetch(paths.BASE_URL + paths.EXPERIENCES + '/categories', {method: "GET"})
            const parsedCategories: CategoryModel[] = await categories.json() as CategoryModel[];
            console.log(parsedCategories)
            setCategories(parsedCategories)
        } catch (error) {
        }
    };

    useEffect(() => {
        console.log("UseEffect common context")
        if (country === undefined) {
            console.log("LLAMADA COUNTRY COMMON")
            getCountry14()
        }
        if (categories.length === 0) {
            console.log("LLAMADA CATEGORY COMMON")
            getAllCategories()
        }
    }, []);


    const getCategory = (categoryId: number) => {
        console.log(categories)
        return categories.find(category => category.id === categoryId) || null;
    }

    const getCountry = () => {
        console.log("GET COUNTRY COMMON")
        console.log(country)
        return country || null;
    }

    const value = {categories, getCategory, country, getCountry}

    return <CommonContext.Provider value={value}>{children}</CommonContext.Provider>
}
