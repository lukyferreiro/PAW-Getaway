package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.security.models.MyUserDetails;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;

    private final Pattern BCRYPT_HASH_PATTERN = Pattern.compile("^\\$2[ayb]\\$.{56}$");

    private final UserService userService;
    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    public MyUserDetailsService(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        String message = messageSource.getMessage("error.invalidEmail2", new Object[]{}, locale);
        final UserModel userModel = userService.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(message + email));
//        final Collection<Roles> userRoles = userService.getRolesByUser(userModel);
//        Collection<? extends GrantedAuthority> authorities = getAuthorities(userRoles);
//        return new MyUserDetails(email, userModel.getPassword(), authorities);
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if(userModel.isVerified()){
            authorities.add(new SimpleGrantedAuthority("VERIFIED"));
            if(userModel.isProvider()){
                authorities.add(new SimpleGrantedAuthority("PROVIDER"));
            }
        } else {
            authorities.add(new SimpleGrantedAuthority("NOT_VERIFIED"));
        }
        LOGGER.debug("Logged user with email {} and authorities {}", email, authorities);
        final String password;
        if(userModel.getPassword() == null || !BCRYPT_HASH_PATTERN.matcher(userModel.getPassword()).matches()) {
            password = encoder.encode(userModel.getPassword());
            userModel.setPassword(password);
            userService.updateUser(userModel.getUserId(), userModel);
        } else {
            password = userModel.getPassword();
        }
        return new MyUserDetails(email, password, authorities, userModel.getUserId(), userModel.getName(),
                userModel.getSurname(), userModel.isVerified(), userModel.isProvider(), userModel.getProfileImage());

    }

//    public static Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
//        return roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
//    }
}