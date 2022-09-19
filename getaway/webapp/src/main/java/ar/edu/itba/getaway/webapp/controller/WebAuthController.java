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
    public ModelAndView accessDenied(@ModelAttribute("loggedUser") final UserModel loggedUser) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.accessDenied", null, locale);
        Long code = Long.valueOf(HttpStatus.FORBIDDEN.toString());
        final ModelAndView mav = new ModelAndView("errors");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
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

    @RequestMapping(path = "/user/verifyAccount/{token}")
    public ModelAndView verifyAccount(HttpServletRequest request,
                                      @PathVariable("token") final String token,
                                      @ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav;

        final Optional<UserModel> userOptional = userService.verifyAccount(token);
        if (userOptional.isPresent()) {
            forceLogin(loggedUser, request);
            mav = new ModelAndView("redirect:/user/verifyAccount/result/successfully");

            try {
                mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
            } catch (NullPointerException e) {
                mav.addObject("loggedUser", false);
            }

            return mav ;
        }
        mav = new ModelAndView("redirect:/user/verifyAccount/result/unsuccessfully");
        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/status/send")
    public ModelAndView sendAccountVerification(@ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("verifySent");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }
        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/status/resend")
    public ModelAndView resendAccountVerification() {
        final UserModel user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(UserNotFoundException::new);
        userService.resendVerificationToken(user);

        final ModelAndView mav = new ModelAndView("redirect:/user/verifyAccount/status/send");
        mav.addObject("loggedUser", user);

        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/result/unsuccessfully")
    public ModelAndView unsuccesfullyAccountVerification(@ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("verifyUnsuccessfully");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/result/successfully")
    public ModelAndView succesfullyAccountVerification(@ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("verifySuccessfully");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        return mav;
    }

    /*-----------------------------------------------------
    --------------------Reset Password---------------------
     -----------------------------------------------------*/
    @RequestMapping(path = "/user/resetPasswordRequest")
    public ModelAndView resetPasswordRequest(@ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form,
                                             @ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("resetPasswordRequest");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        return mav;
    }

    @RequestMapping(path = "/user/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView sendPasswordReset(@Valid @ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form,
                                          final BindingResult errors,
                                          @ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("resetEmailConfirmation");

        if (errors.hasErrors()) {
            return resetPasswordRequest(form, loggedUser);
        }

        final Optional<UserModel> user = userService.getUserByEmail(form.getEmail());
        if (!user.isPresent()) {
            errors.rejectValue("email", "error.invalidEmail");
            return resetPasswordRequest(form, loggedUser);
        }

        userService.generateNewPassword(user.get());

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        return mav;
    }

    @RequestMapping(path = "/user/resetPassword/{token}")
    public ModelAndView resetPassword(@PathVariable("token") String token,
                                      @ModelAttribute("resetPasswordForm") final ResetPasswordForm form,
                                      @ModelAttribute("loggedUser") final UserModel loggedUser) {
        if (userService.validatePasswordReset(token)) {
            final ModelAndView mav = new ModelAndView("reset");
            try {
                mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
            } catch (NullPointerException e) {
                mav.addObject("loggedUser", false);
            }
            mav.addObject("token", token);
            return mav;
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(path = "/user/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(HttpServletRequest request,
                                      @Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm form,
                                      final BindingResult errors,
                                      @ModelAttribute("loggedUser") final UserModel loggedUser) {
        ModelAndView mav = new ModelAndView("reset");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        if (errors.hasErrors()) {
            return mav;
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("", "validation.user.passwordsDontMatch");
            return mav;
        }

        mav = new ModelAndView("resetResult");
        final Optional<UserModel> userOptional = userService.updatePassword(form.getToken(), form.getPassword());
        boolean success = false;

        if (userOptional.isPresent()) {
            success = true;
            UserModel user = userOptional.get();
            forceLogin(user, request);
            try {
                mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
            } catch (NullPointerException e) {
                mav.addObject("loggedUser", false);
            }
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

}
