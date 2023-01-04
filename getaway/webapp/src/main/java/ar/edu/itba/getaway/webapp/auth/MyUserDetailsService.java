package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        String message = messageSource.getMessage("error.invalidEmail2", new Object[]{}, locale);
        final UserModel userModel = userService.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(message + email));
        final Collection<Roles> userRoles = userService.getRolesByUser(userModel);
        Collection<? extends GrantedAuthority> authorities = getAuthorities(userRoles);
        LOGGER.debug("Logged user with email {} and authorities {}", email, authorities);
        return new MyUserDetails(email, userModel.getPassword(), authorities);
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
    }
}