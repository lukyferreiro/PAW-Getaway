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
import {CommonProvider} from "./context/CommonContext";
import Login from "./pages/Login";
import CreateAccount from "./pages/CreateAccount";
import ChangePassword from "./pages/ChangePassword";
import UserEditProfile from "./pages/UserEditProfile";
import RequireAuth from "./components/RequireAuth";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import 'react-toastify/dist/ReactToastify.css'
import {ToastContainer} from "react-toastify";
import {paths} from "./common";
import RequireNoAuth from "./components/RequireNoAuth";

function App() {
    const categoryProp = useState<string | undefined>(undefined)
    const nameProp = useState<string | undefined>(undefined)

    return (
        <AuthProvider>
            <CommonProvider>
                <BrowserRouter basename={paths.LOCAL_BASE_URL}>

                    <Navbar categoryProp={categoryProp} nameProp={nameProp}/>
                    <hr className="separator"/>

                    <Routes>
                        <Route path='/' element={<Landing/>}>
                            <Route index element={<RequireAuth><Home/></RequireAuth>}/>
                        </Route>
                        <Route path='/user' element={<RequireAuth><UserPage/></RequireAuth>}>
                            <Route path='profile' element={<UserProfile/>}/>
                            <Route path='editProfile' element={<UserEditProfile/>}/>
                            <Route path='experiences' element={<UserExperiences/>}/>
                            <Route path='favourites' element={<UserFavourites/>}/>
                            <Route path='reviews' element={<UserReviews/>}/>
                        </Route>
                        <Route path='/experiences' element={<RequireAuth><Experiences categoryProp={categoryProp} nameProp={nameProp}/></RequireAuth>}/>
                        <Route path='/experiences/:experienceId' element={<RequireAuth><ExperienceDetails categoryProp={categoryProp} nameProp={nameProp}/></RequireAuth>}/>
                        <Route path='/experiences/:experienceId/reviewForm' element={<RequireAuth><ReviewForm/></RequireAuth>} />
                        <Route path='/experienceForm' element={<RequireAuth><ExperienceForm/></RequireAuth>}/>
                        <Route path='error' element={<Error/>}/>
                        <Route path='*' element={<Custom404/>}/>
                        <Route path='/login' element={<RequireNoAuth><Login/></RequireNoAuth>}/>
                        <Route path='/createAccount' element={<RequireNoAuth><CreateAccount/></RequireNoAuth>}/>
                        <Route path='/changePassword' element={<RequireNoAuth><ChangePassword/></RequireNoAuth>}/>
                    </Routes>

                    <ToastContainer position='top-left' autoClose={5000} hideProgressBar={false}
                                    newestOnTop={false} closeOnClick rtl={false} pauseOnFocusLoss draggable />

                    <Footer/>

                </BrowserRouter>
            </CommonProvider>
        </AuthProvider>
    )
}

export default App;
