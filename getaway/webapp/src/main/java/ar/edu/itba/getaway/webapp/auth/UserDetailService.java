package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.UserService;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UserDetailService implements UserDetailsService {

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Autowired
    private UserService us;
    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();

    @Autowired
    public UserDetailService(final UserService us) {
        this.us = us;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        String message = messageSource.getMessage("error.invalidEmail2", new Object[]{}, locale);
        final UserModel userModel = us.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(message + email));
//        if(!BCRYPT_PATTERN.matcher(user.getPassword()).matches()){
//            us.changePassword(user.getEmail(), user.getPassword());
//            return loadUserByUsername(username);
//        }
        Collection<? extends GrantedAuthority> authorities = getAuthorities(userModel.getRoles());
        return new MyUserDetails(email, userModel.getPassword(), authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
    }
}