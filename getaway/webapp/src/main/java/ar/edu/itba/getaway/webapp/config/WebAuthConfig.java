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
                    //Session routes
                    .antMatchers("/login", "/register").anonymous()
                    .antMatchers("/user/verifyAccount/send").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount/resend").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount/unsuccessfull").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount/successfull").hasRole("VERIFIED")
                    .antMatchers("/logout").authenticated()

                    //Profile routes
//                    .antMatchers("/user/account").hasRole("USER")
//                    .antMatchers("/user/account/search", "/user/account/update",
//                            "/user/account/updateCoverImage", "/user/account/updateInfo",
//                            "/user/account/updateProfileImage").hasRole("VERIFIED")
                    //Experiences
                    .antMatchers("/create_experience").authenticated()
//                    .antMatchers("/experiences/{categoryName}").permitAll()
//                    .antMatchers("/create_experience").hasRole("VERIFIED")
                    //else
                    .antMatchers("/**").permitAll()

                .and().formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false) //Tras logearme me lleva a /
                    .failureUrl("/login?error=true")
                .and().rememberMe()
                    .rememberMeParameter("rememberMe")
                    .userDetailsService(userDetailsService)
                    .key(FileCopyUtils.copyToString(new InputStreamReader(authKey.getInputStream())))
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and().exceptionHandling()
                    .accessDeniedPage("/")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/403");
    }

}
