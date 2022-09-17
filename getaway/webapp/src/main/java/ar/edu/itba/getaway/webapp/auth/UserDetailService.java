package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UserDetailService implements UserDetailsService {

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Autowired
    private UserService us;

    @Autowired
    public UserDetailService(final UserService us) {
        this.us = us;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserModel user = us.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email" + username));
//        if(!BCRYPT_PATTERN.matcher(user.getPassword()).matches()){
//            us.changePassword(user.getEmail(), user.getPassword());
//            return loadUserByUsername(username);
//        }
        Collection<? extends GrantedAuthority> authorities = getAuthorities(user.getRoles());
        return new MyUserDetails(username, user.getPassword(), authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
    }
}