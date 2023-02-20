import {Outlet} from "react-router-dom";
import Navbar from "../components/Navbar";
import React, {useState} from "react";
import Footer from "../components/Footer";

export default function Landing() {

    const categoryProp = useState<string>("")
    const nameProp = useState<string>("")

    return (
        <>
            <Navbar categoryProp={categoryProp} nameProp={nameProp}/>
            <hr className="separator"/>
            <Outlet/>
            <Footer/>
        </>
    )

}