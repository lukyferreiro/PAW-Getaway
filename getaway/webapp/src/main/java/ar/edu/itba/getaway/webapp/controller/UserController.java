package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @ModelAttribute("loggedUser") 
    public UserModel loggedUser(Model model) {

        if (model.containsAttribute("loggedUser")) {
            LOGGER.debug("Retrieved current user passed by controller");
            return (UserModel) model.asMap().get("loggedUser");
        }

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!isAnonymous(auth)) {
            //TODO CHECK
            //Cuando me verifico, no se me actualiza el auth.getAuthorities()
            //me sigue diciendo que sigo sin estar verificado
            //no se esta actualizando el SecurityContextHolder.getContext().getAuthentication();
            //Para logrrar que fumciones hay que cerrar el servidor y vorlo a abrir
            LOGGER.debug("Retrieved current user via service");
            return userService.getUserByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);
        }

        LOGGER.debug("User is anonymous");
        return null;
    }

    private boolean isAnonymous(Authentication auth) {
        return auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ANONYMOUS"));
    }

}
