import React, {useState} from 'react'
import {BrowserRouter, Route, Routes} from 'react-router-dom'

import Landing from "./pages/Landing";
import UserPage from "./pages/UserPage";
import UserProfile from "./pages/UserProfile";
import UserExperiences from "./pages/UserExperiences";
import UserFavourites from "./pages/UserFavourites";
import UserReviews from "./pages/UserReviews";
import Experiences from "./pages/Experiences";
import ExperienceDetails from "./pages/ExperienceDetails";
import ExperienceForm from "./pages/ExperienceForm";
import ReviewForm from "./pages/ReviewForm";
import Error from "./pages/Error";
import Custom404 from "./pages/Custom404";
import Home from "./pages/Home";
import {AuthProvider} from "./context/AuthContext";
import Login from "./pages/Login";
import CreateAccount from "./pages/CreateAccount";
import ChangePassword from "./pages/ChangePassword";
import EditAccount from "./pages/EditAccount";
import RequireAuth from "./components/RequireAuth";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

function App() {
    const categoryProp = useState<string | undefined>(undefined)
    const nameProp = useState<string | undefined>(undefined)

    return (
        <AuthProvider>
            <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>

                <Navbar categoryProp={categoryProp} nameProp={nameProp}/>
                <hr className="separator"/>

                <Routes>
                    <Route path='/' element={<RequireAuth><Landing/></RequireAuth>}>
                        <Route index element={<Home/>}/>
                        <Route path='/user' element={<UserPage/>}>
                            <Route index element={<UserProfile/>}/>
                            <Route path='profile' element={<UserProfile/>}/>
                            <Route path='editAccount' element={<EditAccount/>}/>
                            <Route path='experiences' element={<UserExperiences/>}/>
                            <Route path='favourites' element={<UserFavourites/>}/>
                            <Route path='reviews' element={<UserReviews/>}/>
                        </Route>
                        <Route path='experiences' element={<Experiences categoryProp={categoryProp} nameProp={nameProp}/>}/>
                        <Route path='experiences/:experienceId' element={<ExperienceDetails/>}/>
                        <Route path='experiences/:experienceId/reviewForm' element={<ReviewForm/>} />
                        <Route path='experienceForm' element={<ExperienceForm/>}/>
                        <Route path='error' element={<Error/>}/>
                        <Route path='*' element={<Custom404/>}/>
                    </Route>
                    <Route path='/login' element={<Login categoryProp={categoryProp} nameProp={nameProp}/>}/>
                    <Route path='/createAccount' element={<CreateAccount/>}/>
                    <Route path='/changePassword' element={<ChangePassword/>}/>
                </Routes>

                <Footer/>

            </BrowserRouter>
        </AuthProvider>
    )
}

export default App;
