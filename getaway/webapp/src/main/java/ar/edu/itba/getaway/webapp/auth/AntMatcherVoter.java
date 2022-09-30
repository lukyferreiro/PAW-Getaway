package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class AntMatcherVoter {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private UserService userService;

    public boolean canEditExperienceById(Authentication authentication , Long experienceId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        System.out.println("----------------");
        System.out.println(authentication.getName() );
        System.out.println("----------------");
        System.out.println(experienceId);
        System.out.println("----------------");
        System.out.println(experienceService.getUserEmailByExperienceId(experienceId));
        System.out.println("----------------");
        return experienceService.getUserEmailByExperienceId(experienceId).equals(authentication.getName());
    }
}
