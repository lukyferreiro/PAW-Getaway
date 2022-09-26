package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserInfo;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.ImageService;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.getaway.webapp.forms.RegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    @RequestMapping(value = "/user/profile", method = {RequestMethod.GET})
    public ModelAndView profile(@ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("user_profile");

        final Optional<UserModel> user = userService.getUserById(loggedUser.getId());

        mav.addObject("user", user.get());
        mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));

        return mav;
    }

    @RequestMapping(value = "/user/profile/edit", method = {RequestMethod.GET})
    public ModelAndView editProfileGet(@ModelAttribute ("loggedUser") final UserModel loggedUser, @ModelAttribute ("registerForm") final RegisterForm registerForm){
        final ModelAndView mav = new ModelAndView("user_profile_edit");
        Optional<UserModel> user=null;
        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
             user = userService.getUserById(loggedUser.getId());
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        if(user.isPresent()){
            mav.addObject("user", user.get());
        }

        return mav;
    }

    @RequestMapping(value = "/user/profile/edit", method = {RequestMethod.POST})
    public ModelAndView editProfilePost(@ModelAttribute ("loggedUser") final UserModel loggedUser, @ModelAttribute ("registerForm") final RegisterForm registerForm){
        final ModelAndView mav = new ModelAndView("user_profile_edit");

        UserModel user = userService.getUserById(loggedUser.getId()).get();

        userService.updateUserInfo(user.getId(), new UserInfo(registerForm.getName(), registerForm.getSurname()));


        return mav;
    }

    @RequestMapping(value = "/user/profileImage", method = {RequestMethod.GET}, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getUserProfileImage(@ModelAttribute("loggedUser") final UserModel loggedUser){
        LOGGER.debug(loggedUser.getEmail());


        Optional<ImageModel> optionalImageModel = imageService.getById(loggedUser.getProfileImageId());

        LOGGER.debug("Retrieving profileImage");

        return optionalImageModel.map(ImageModel::getImage).orElse(null);
    }

}
