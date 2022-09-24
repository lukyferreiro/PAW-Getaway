package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.AccessDeniedException;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
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
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebAuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;


    @RequestMapping(path = "/access-denied")
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public ModelAndView accessDenied() {
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.accessDenied", null, locale);
        Long code = Long.valueOf(HttpStatus.FORBIDDEN.toString());
        final ModelAndView mav = new ModelAndView("errors");

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

        return new ModelAndView("redirect:/user/verifyAccount/status/send");
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

    //This is the endpoint that It's called from the email
    @RequestMapping(path = "/user/verifyAccount/{token}")
    public ModelAndView verifyAccount(HttpServletRequest request,
                                      @PathVariable("token") final String token) {
        final ModelAndView mav;

        final Optional<UserModel> userOptional = userService.verifyAccount(token);

        if (userOptional.isPresent()) {
            forceLogin(userOptional.get(), request);
            mav = new ModelAndView("redirect:/user/verifyAccount/result/successfully");
            return mav ;
        }
        mav = new ModelAndView("redirect:/user/verifyAccount/result/unsuccessfully");
        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/status/send")
    public ModelAndView sendAccountVerification() {
        return new ModelAndView("verifySent");
    }

    @RequestMapping(path = "/user/verifyAccount/status/resend")
    public ModelAndView resendAccountVerification(Principal principal) {
        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        userService.resendVerificationToken(user);
        return new ModelAndView("redirect:/user/verifyAccount/status/send");

    }

    @RequestMapping(path = "/user/verifyAccount/result/unsuccessfully")
    public ModelAndView unsuccesfullyAccountVerification() {
        return new ModelAndView("verifyUnsuccessfully");
    }

    @RequestMapping(path = "/user/verifyAccount/result/successfully")
    public ModelAndView succesfullyAccountVerification() {
        return new ModelAndView("verifySuccessfully");
    }

    /*-----------------------------------------------------
    --------------------Reset Password---------------------
     -----------------------------------------------------*/
    @RequestMapping(path = "/user/resetPasswordRequest")
    public ModelAndView resetPasswordRequest(@ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form) {
        return new ModelAndView("resetPasswordRequest");
    }

    @RequestMapping(path = "/user/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView sendPasswordReset(@Valid @ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form,
                                          final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("resetEmailConfirmation");

        if (errors.hasErrors()) {
            return resetPasswordRequest(form);
        }

        final Optional<UserModel> user = userService.getUserByEmail(form.getEmail());
        if (!user.isPresent()) {
            errors.rejectValue("email", "error.invalidEmail");
            return resetPasswordRequest(form);
        }

        userService.generateNewPassword(user.get());

        return mav;
    }

    //This is the endpoint that It's called from the email
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
                                      final BindingResult errors) {
        ModelAndView mav = new ModelAndView("reset");

        if (errors.hasErrors()) {
            return mav;
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("passwordsNotEquals", "validation.user.passwordsDontMatch");
            return mav;
        }

        mav = new ModelAndView("resetResult");
        final Optional<UserModel> userOptional = userService.updatePassword(form.getToken(), form.getPassword());
        boolean success = false;

        if (userOptional.isPresent()) {
            success = true;
            UserModel user = userOptional.get();
            forceLogin(user, request);
        }

        mav.addObject("success", success);
        return mav;
    }

    /*-----------------------------------------------------
    -------------------------------------------------------
     -----------------------------------------------------*/

    //This method is user to update the SpringContextHolder
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

}
