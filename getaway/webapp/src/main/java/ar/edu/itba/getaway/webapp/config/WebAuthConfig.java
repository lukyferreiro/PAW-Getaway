package ar.edu.itba.getaway.webapp.config;

import ar.edu.itba.getaway.webapp.auth.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.getaway.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailsService;

    @Value("classpath:auth/auth_key.pem")
    private Resource authKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AccessDecisionManager decisionManager(){
//        //TODO
//        return new ConsensusBased(List.of("decicion boters"));
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /* Es importante el orden de las reglas escritas, ya que la primera que coincida con
     la url solicitada será aplicada y no se evaluarán las subsiguientes. Es por esto
      que la regla default antMatchers("/**").authenticated() es la última en aplicarse */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
//                .invalidSessionUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/**").anonymous()
//                    .antMatchers("/login").anonymous()
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/**").authenticated()
//                    .antMatchers(HttpMethod.POST, "/edit").hasAnyRole("EDITOR")
//                    .antMatchers("/**").permitAll()
//                    .accessDecisionManager(decisionManager())
                .and().formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginPage("/login")
                    .defaultSuccessUrl("/", false) //Tras logearme me lleva a /
                .and().rememberMe()
                    .rememberMeParameter("rememberme")
                    .userDetailsService(userDetailsService)
                    .key(FileCopyUtils.copyToString(new InputStreamReader(authKey.getInputStream())))
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/images/**", "/403");
    }

//    private String loadRememberMeKey(){
//        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("auth_key.pem"))){
//            return FileCopyUtils.copyToString(reader);
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

}
