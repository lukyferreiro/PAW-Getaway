package ar.edu.itba.getaway.webapp.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//https://www.baeldung.com/spring-security-custom-access-denied-page
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.warn("User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
//        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            LOGGER.warn("User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
//            LOGGER.warn("User: " + auth.getName() + " has roles " + auth.getAuthorities());
//
//            final Collection<GrantedAuthority> authorities = createAuthorities(Collections.singletonList(Roles.VERIFIED));
//            final Collection<? extends GrantedAuthority> currentAuthorities = auth.getAuthorities();
//            for (final GrantedAuthority grantedAuthority : authorities) {
//                //Si el usuario no esta verificado
//                if (!currentAuthorities.contains(grantedAuthority)) {
//                    response.sendRedirect(request.getContextPath() + "/pleaseVerify");
//                    return;
//                }
//            }
//        }
//
//        System.out.println("------------------");
//        System.out.println("ACA LLEGUE 2222222");
//        System.out.println("------------------");
//        response.sendRedirect(request.getContextPath() + "/access-denied");
//    }
//
//    private Collection<GrantedAuthority> createAuthorities(Collection<Roles> roles) {
//        return roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
//    }

}