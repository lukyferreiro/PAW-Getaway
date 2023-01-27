import React from 'react'
import {BrowserRouter, Route, Routes} from 'react-router-dom'

import Landing from "./pages/Landing";
import UserPage from "./pages/UserPage";
import UserProfile from "./pages/UserProfile";
import UserExperiences from "./pages/UserExperiences";
import UserFavourites from "./pages/UserFavourites";
import UserReviews from "./pages/UserReviews";
import Experiences from "./pages/Experiences";
import ExperienceDetails from "./pages/ExperienceDetails";
import CreateExperience from "./pages/CreateExperience";
import Error from "./pages/Error";
import Custom404 from "./pages/Custom404";
import Login from "./pages/Login";
import Register from "./pages/Register";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";

function App() {
    return (

        <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>

            <Navbar/>
            <hr className="separator"/>

            <Routes>
                <Route path='/' element={<Landing/>}>
                    <Route index element={<Home/>}/>
                    <Route path='user' element={<UserPage/>}>
                        <Route index element={<UserProfile/>}/>
                        <Route path='profile' element={<UserProfile/>}/>
                        <Route path='experiences' element={<UserExperiences/>}/>
                        <Route path='favourites' element={<UserFavourites/>}/>
                        <Route path='reviews' element={<UserReviews/>}/>
                    </Route>
                    <Route path='experiences' element={<Experiences/>}/>
                    <Route path='experiences/:experienceId' element={<ExperienceDetails/>}/>
                    <Route path='createExperience' element={<CreateExperience/>}/>
                    <Route path='error' element={<Error/>}/>
                    <Route path='*' element={<Custom404/>}/>
                </Route>
                <Route path='/login' element={<Login/>}/>
                <Route path='/register' element={<Register/>}/>
            </Routes>

            <Footer/>

        </BrowserRouter>
    )
}

export default App;
