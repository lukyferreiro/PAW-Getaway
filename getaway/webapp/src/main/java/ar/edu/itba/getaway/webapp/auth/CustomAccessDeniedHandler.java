package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.models.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

//https://www.baeldung.com/spring-security-custom-access-denied-page
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOGGER.warn("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());

            final Collection<GrantedAuthority> authorities = createAuthorities(Collections.singletonList(Roles.VERIFIED));
            final Collection<? extends GrantedAuthority> currentAuthorities = auth.getAuthorities();
            for (final GrantedAuthority grantedAuthority : authorities) {
                if (!currentAuthorities.contains(grantedAuthority)) {
                    response.sendRedirect(request.getContextPath() + "/");
                    return;
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/access-denied");
    }

    private Collection<GrantedAuthority> createAuthorities(Collection<Roles> roles) {
        return roles.stream()
                .map((role) -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

}
