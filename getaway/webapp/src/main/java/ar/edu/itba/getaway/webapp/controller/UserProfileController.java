//package ar.edu.itba.getaway.webapp.controller;
//
//import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
//import ar.edu.itba.getaway.models.*;
//import ar.edu.itba.getaway.interfaces.services.ImageService;
//import ar.edu.itba.getaway.interfaces.services.UserService;
//import ar.edu.itba.getaway.webapp.forms.EditProfileForm;
//import ar.edu.itba.getaway.webapp.forms.SearchForm;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.List;
//
//@Controller
//public class UserProfileController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ImageService imageService;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);
//
//    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");
//    private static final int MAX_SIZE_PER_FILE = 10000000;
//
//    @RequestMapping(value = "/user/profile", method = {RequestMethod.GET})
//    public ModelAndView profile(Principal principal,
//                                @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
//                                HttpServletRequest request) {
//        LOGGER.debug("Endpoint GET {}", request.getServletPath());
//
//        final ModelAndView mav = new ModelAndView("userProfile");
//
//        final UserModel userModel = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        mav.addObject("user", userModel);
//        mav.addObject("hasImage", userModel.getImage() != null);
//
//        return mav;
//    }
//
//    @RequestMapping(value = "/user/profile/edit", method = {RequestMethod.GET})
//    public ModelAndView editProfileGet(@ModelAttribute("editProfileForm") final EditProfileForm editProfileForm,
//                                       @ModelAttribute("searchForm") final SearchForm searchForm,
//                                       Principal principal,
//                                       HttpServletRequest request) {
//        LOGGER.debug("Endpoint GET {}", request.getServletPath());
//
//        final ModelAndView mav = new ModelAndView("userProfileEdit");
//        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        editProfileForm.setName(user.getName());
//        editProfileForm.setSurname(user.getSurname());
//        editProfileForm.setEmail(user.getEmail());
//
//        return mav;
//    }
//
//    @RequestMapping(value = "/user/profile/edit", method = {RequestMethod.POST})
//    public ModelAndView editProfilePost(@Valid @ModelAttribute("editProfileForm") final EditProfileForm editProfileForm,
//                                        final BindingResult errors,
//                                        @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
//                                        Principal principal,
//                                        HttpServletRequest request) throws Exception {
//        LOGGER.debug("Endpoint GET {}", request.getServletPath());
//
//        if (errors.hasErrors()) {
//            return editProfileGet(editProfileForm, searchForm, principal, request);
//        }
//
//        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final MultipartFile profileImg = editProfileForm.getProfileImg();
//
//        if (!profileImg.isEmpty()) {
//            if (!contentTypes.contains(profileImg.getContentType())) {
//                errors.rejectValue("profileImg", "editProfileForm.validation.imageFormat");
//                return editProfileGet(editProfileForm, searchForm, principal, request);
//            } else if (profileImg.getSize() > MAX_SIZE_PER_FILE) {
//                errors.rejectValue("experienceImg", "editProfileForm.validation.imageSize");
//                return editProfileGet(editProfileForm, searchForm, principal, request);
//            } else {
//                imageService.updateImg(profileImg.getBytes(), user.getProfileImage());
//            }
//        }
//
//        userService.updateUserInfo(user, new UserInfo(editProfileForm.getName(), editProfileForm.getSurname()));
//
//        return new ModelAndView("redirect:/user/profile");
//    }
//
//}
