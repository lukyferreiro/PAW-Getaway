package ar.edu.itba.getaway.webapp.auth;


import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//        final User user = us.getUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user by the name" + username));
//        Collection<? extends GrantedAuthority> authorities = getAuthorities(user.getRoles());
//        //final Collection<? extends GrantedAuthority> authorities = Arrays.asList(
//        //        new SimpleGrantedAuthority("ROLE_USER"),
//        //        new SimpleGrantedAuthority("ROLE_ADMIN")
//        //);
//        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.
                stream()
                .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }
}