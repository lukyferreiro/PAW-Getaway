package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.controller.forms.ExperienceFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ForceLogin {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForceLogin.class);

    //This method is used to update the SpringContextHolder
    //https://stackoverflow.com/questions/9910252/how-to-reload-authorities-on-user-update-with-spring-security
    public void forceLogin(UserModel user, HttpServletRequest request) {
        //generate authentication
        final PreAuthenticatedAuthenticationToken token =
                new PreAuthenticatedAuthenticationToken(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));

        token.setDetails(new WebAuthenticationDetails(request));

        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        LOGGER.debug("Updated SpringContextHolder in order to update user roles");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.stream()
                .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

}
