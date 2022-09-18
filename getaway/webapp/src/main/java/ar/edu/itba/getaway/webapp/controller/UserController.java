package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute("loggedUser")
    public UserModel loggedUser(Model model) {

        if (model.containsAttribute("loggedUser"))
            return (UserModel) model.asMap().get("loggedUser");

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!isAnonymous(auth))
            return userService.getUserByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

        return null;
    }

    private boolean isAnonymous(Authentication auth) {
        return auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ANONYMOUS"));
    }


}
