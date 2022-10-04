package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.interfaces.services.ImageService;
import ar.edu.itba.interfaces.services.UserService;
import ar.edu.itba.getaway.webapp.forms.RegisterForm;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    @RequestMapping(value = "/user/profile", method = {RequestMethod.GET})
    public ModelAndView profile(Principal principal,
                                @Valid @ModelAttribute("searchForm") final SearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("userProfile");

        final UserModel userModel = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final ImageModel imageModel = imageService.getImgById(userModel.getProfileImageId()).get();

        mav.addObject("user", userModel);
        mav.addObject("hasImage", imageModel.getImage() != null);

        return mav;
    }

    @RequestMapping(value = "/user/profile/edit", method = {RequestMethod.GET})
    public ModelAndView editProfileGet(Principal principal,
                                       @ModelAttribute ("registerForm") final RegisterForm registerForm){
        final ModelAndView mav = new ModelAndView("userProfileEdit");
        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        registerForm.setName(user.getName());
        registerForm.setSurname(user.getSurname());
        registerForm.setEmail(user.getEmail());

        return mav;
    }

    @RequestMapping(value = "/user/profile/edit", method = {RequestMethod.POST})
    public ModelAndView editProfilePost(Principal principal,
                                        @ModelAttribute ("registerForm") final RegisterForm registerForm,
                                        final BindingResult errors) throws Exception {
        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        userService.updateUserInfo(user.getUserId(), new UserInfo(registerForm.getName(), registerForm.getSurname()));
//        userService.updateProfileImage();

        final MultipartFile profileImg = registerForm.getProfileImg();

        if(!profileImg.isEmpty()){
            if (contentTypes.contains(profileImg.getContentType())) {
                imageService.updateImg(profileImg.getBytes(), user.getProfileImageId());
            } else {
                //Todo: Enviar mensaje de formato de imagen inválido
                errors.rejectValue("profileImg", "experienceForm.validation.imageFormat");
                return editProfileGet(principal, registerForm);
            }
        }

        return new ModelAndView("redirect:/user/profile");
    }

}
