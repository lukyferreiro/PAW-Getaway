package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.auth.MyUserDetails;
import ar.edu.itba.getaway.webapp.exceptions.AccessDeniedException;
import ar.edu.itba.getaway.webapp.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebAuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageExperienceService imageExperienceService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(path = "/access-denied")
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public ModelAndView accessDenied(@AuthenticationPrincipal MyUserDetails userDetails) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.accessDenied", null, locale);
        Long code = Long.valueOf(HttpStatus.FORBIDDEN.toString());
        final ModelAndView mav = new ModelAndView("errors");

        try {
            String email = userDetails.getUsername();
            UserModel userModel = userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
            mav.addObject("hasSign", userModel.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("hasSign", false);
        }

        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @RequestMapping(path = "/register")
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm form) {
        return new ModelAndView("register");
    }
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final RegisterForm form,
                                     final BindingResult errors,
                                     final HttpServletRequest request) {
        if (errors.hasErrors()) {
            return register(form);
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("password", "validation.user.passwordsDontMatch");
            return register(form);
        }

        UserModel user;
        try {
            user = userService.createUser(form.getPassword(), form.getName(),
                    form.getSurname(), form.getEmail());
            forceLogin(user, request);
        } catch (DuplicateUserException e) {
            errors.rejectValue("email", "validation.user.DuplicateEmail");
            return register(form);
        }

        return new ModelAndView("redirect:/user/verifyAccount/send");
    }

    @RequestMapping(path ="/login")
    public ModelAndView login(@RequestParam(value = "error", defaultValue = "false") boolean error) {
        final ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", error);
        return mav;
    }

    /*-----------------------------------------------------
    --------------------Verify Account---------------------
     -----------------------------------------------------*/

    @RequestMapping(path = "/user/verifyAccount/send")
    public ModelAndView sendAccountVerification() {
        return new ModelAndView("verifySended");
    }

    @RequestMapping(path = "/user/verifyAccount/{token}")
    public ModelAndView verifyAccount(HttpServletRequest request,
                                      @PathVariable("token") final String token) {

        final Optional<UserModel> userOptional = userService.verifyAccount(token);
//        boolean success = false;

        if (userOptional.isPresent()) {
//            success = true;
//            UserModel user = userOptional.get();
//            forceLogin(user, request);
//            mav.addObject("loggedUser", user);
            return new ModelAndView("redirect:/user/verifyAccount/succesfull");
        }
//        mav.addObject("success", success);
        return new ModelAndView("redirect:/user/verifyAccount/unsuccesfull");
    }

    @RequestMapping(path = "/user/verifyAccount/resend")
    public ModelAndView resendAccountVerification() {
        final UserModel user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(UserNotFoundException::new);
        userService.resendVerificationToken(user);

        final ModelAndView mav = new ModelAndView("redirect:/user/verifyAccount/send");
        mav.addObject("loggedUser", user);

        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/unsuccessfull")
    public ModelAndView unsuccesfullyAccountVerification() {
        return new ModelAndView("verifyUnsuccefully");
    }

    @RequestMapping(path = "/user/verifyAccount/successfull")
    public ModelAndView succesfullyAccountVerification() {
        return new ModelAndView("verifySuccefully");
    }

    /*-----------------------------------------------------
    --------------------Reset Password---------------------
     -----------------------------------------------------*/
    @RequestMapping(path = "/user/resetPasswordRequest")
    public ModelAndView resetPasswordRequest(@ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form) {
        return new ModelAndView("/resetPasswordRequest");
    }

    @RequestMapping(path = "/user/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView sendPasswordReset(@Valid @ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form,
                                          final BindingResult errors) {

        if (errors.hasErrors()) {
            return resetPasswordRequest(form);
        }

        final Optional<UserModel> user = userService.getUserByEmail(form.getEmail());
        if (!user.isPresent()) {
            errors.rejectValue("email", "error.invalidEmail");
            return resetPasswordRequest(form);
        }

        userService.generateNewPassword(user.get());

        return new ModelAndView("/resetEmailConfirmation");
    }

    @RequestMapping(path = "/user/resetPassword/{token}")
    public ModelAndView resetPassword(@PathVariable("token") String token,
                                      @ModelAttribute("resetPasswordForm") final ResetPasswordForm form) {
        if (userService.validatePasswordReset(token)) {
            final ModelAndView mav = new ModelAndView("reset");
            mav.addObject("token", token);
            return mav;
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(path = "/user/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(HttpServletRequest request,
                                      @Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm form,
                                      BindingResult errors) {

        if (errors.hasErrors()) {
            return new ModelAndView("reset");
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("", "validation.user.passwordsDontMatch");
            return new ModelAndView("reset");
        }

        final ModelAndView mav = new ModelAndView("resetResult");
        final Optional<UserModel> userOptional = userService.updatePassword(form.getToken(), form.getPassword());
        boolean success = false;

        if (userOptional.isPresent()) {
            success = true;
            UserModel user = userOptional.get();
            forceLogin(user, request);
            mav.addObject("loggedUser", user);
        }
        mav.addObject("success", success);
        return mav;
    }

    /*-----------------------------------------------------
    -------------------------------------------------------
     -----------------------------------------------------*/

    private void forceLogin(UserModel user, HttpServletRequest request) {
        //generate authentication
        final PreAuthenticatedAuthenticationToken token =
                new PreAuthenticatedAuthenticationToken(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));

        token.setDetails(new WebAuthenticationDetails(request));

        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.stream()
                .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

//    EDICION Y ELIMINACION DE EXPERIENCIAS -> RECOMIENDO PASARLO A OTRO CONTROLLER

    @RequestMapping(value = "/user/experiences", method = {RequestMethod.GET})
    public ModelAndView experience(@AuthenticationPrincipal MyUserDetails userDetails) {
        final ModelAndView mav = new ModelAndView("userExperiences");
        try {
            String email = userDetails.getUsername();
            UserModel userModel = userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
            List<ExperienceModel> experienceList = experienceService.getByUserId(userModel.getId());
            mav.addObject("activities", experienceList);
            mav.addObject("hasSign", userModel.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("hasSign", false);
        }

        return mav;
    }
    @RequestMapping(value = "/delete/{experienceId}", method = {RequestMethod.GET})
    public ModelAndView experienceDelete(@PathVariable("experienceId") final long experienceId,
                                       @ModelAttribute("deleteForm") final DeleteForm form,
                                       @AuthenticationPrincipal MyUserDetails userDetails) {
        final ModelAndView mav = new ModelAndView("deleteExperience");
        ExperienceModel experience = experienceService.getById(experienceId).get();
        mav.addObject("experience", experience);

        return mav;
    }

    @RequestMapping(value = "/delete/{experienceId}", method = {RequestMethod.POST})
    public ModelAndView experienceDeletePost(@PathVariable(value = "experienceId") final long experienceId,
                                             @ModelAttribute("deleteForm") final DeleteForm form,
                                           @AuthenticationPrincipal MyUserDetails userDetails,
                                           final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return experienceDelete(experienceId, form, userDetails);
        }

        experienceService.delete(experienceId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/edit/{experienceId}", method = {RequestMethod.GET})
    public ModelAndView experienceEdit(@PathVariable("experienceId") final long experienceId,
                                       @ModelAttribute("experienceForm") final ExperienceForm form,
                                   @AuthenticationPrincipal MyUserDetails userDetails) {
        final ModelAndView mav = new ModelAndView("experience_edit_form");

        ExperienceCategory[] categoryModels = ExperienceCategory.values();
        List<String> categories = new ArrayList<>();
        for (ExperienceCategory categoryModel : categoryModels) {
            categories.add(categoryModel.getName());
        }

        List<CountryModel> countryModels = countryService.listAll();
        List<CityModel> cityModels = cityService.listAll();
        ExperienceModel experience = experienceService.getById(experienceId).get();

        form.setActivityName(experience.getName());
        form.setActivityAddress(experience.getAddress());
        form.setActivityInfo(experience.getDescription());
        form.setActivityPrice(experience.getPrice().toString());
        form.setActivityUrl(experience.getSiteUrl());
        //form.setImg();
        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);
        mav.addObject("countries", countryModels);
        mav.addObject("experience", experience);
        mav.addObject("formCountry", "Argentina");
        mav.addObject("formCity", cityService.getById(experience.getCityId()).get().getId());

        return mav;
    }

    @RequestMapping(value = "/edit/{experienceId}", method = {RequestMethod.POST})
    public ModelAndView experienceEditPost(@PathVariable(value = "experienceId") final long experienceId,
                                       @ModelAttribute("experienceForm") final ExperienceForm form,
                                       @AuthenticationPrincipal MyUserDetails userDetails,
                                       final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return experienceEdit(experienceId, form, userDetails);
        }

        long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();

        long userId;
        try {
            String email = userDetails.getUsername();
            UserModel userModel = userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
            userId = userModel.getId();
        } catch (NullPointerException e) {
            throw new AccessDeniedException();
        }
        boolean hasImg = false;
        if (!form.getActivityImg().isEmpty()) {
            hasImg = true;
            final ImageModel imageModel = imageService.create(form.getActivityImg().getBytes());
            imageExperienceService.create(imageModel.getId(), experienceId, true);
        }
        Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();

        ExperienceModel experienceModel = new ExperienceModel(experienceId, form.getActivityName(), form.getActivityAddress(), description, url, price, cityId, categoryId + 1, userId, hasImg);
        experienceService.update(experienceId, experienceModel);


        return new ModelAndView("redirect:/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
    }

}
