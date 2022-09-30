package ar.edu.itba.getaway.webapp.config;

import ar.edu.itba.getaway.webapp.auth.AntMatcherVoter;
import ar.edu.itba.getaway.webapp.auth.CustomAccessDeniedHandler;
import ar.edu.itba.getaway.webapp.auth.RefererRedirectionAuthenticationSuccessHandler;
import ar.edu.itba.getaway.webapp.auth.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("ar.edu.itba.getaway.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Value("classpath:auth/auth_key.pem")
    private Resource authKey;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RefererRedirectionAuthenticationSuccessHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AntMatcherVoter antMatcherVoter() {
        return new AntMatcherVoter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/403");
    }

    /* Es importante el orden de las reglas */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
            .and().authorizeRequests()
                //Session routes
                .antMatchers("/login", "/register").anonymous()
                .antMatchers("/logout").authenticated()
                //Verify Account
                .antMatchers("/user/verifyAccount/{token}").permitAll()
                .antMatchers("/user/verifyAccount/status/send").hasRole("NOT_VERIFIED")
                .antMatchers("/user/verifyAccount/status/resend").hasRole("NOT_VERIFIED")
                .antMatchers("/user/verifyAccount/result/unsuccessfully").hasRole("NOT_VERIFIED")
                .antMatchers("/user/verifyAccount/result/successfully").hasRole("VERIFIED")
                //Reset Password
                .antMatchers("/user/resetPasswordRequest").anonymous()
                .antMatchers(HttpMethod.POST,"/user/resetPasswordRequest").anonymous()
                .antMatchers("/user/resetPassword/{token}").anonymous()
                .antMatchers(HttpMethod.GET, "/user/resetPassword").denyAll()
                .antMatchers(HttpMethod.POST,"/user/resetPassword").anonymous()
                //User profile
                .antMatchers("/user/experiences").authenticated()
                .antMatchers("/user/profileImage/{imageId}").permitAll()
                //TODO
                // ...
                //Experiences
                .antMatchers(HttpMethod.GET,"/create_experience").hasRole("VERIFIED")
                .antMatchers(HttpMethod.POST,"/create_experience").hasRole("VERIFIED")
                .antMatchers(HttpMethod.GET,"/experiences/{categoryName}/{id}/create_review").hasRole("VERIFIED")
                .antMatchers(HttpMethod.POST,"/experiences/{categoryName}/{id}/create_review").hasRole("VERIFIED")
                .antMatchers(HttpMethod.GET,"/experiences/{categoryName}/{experienceId}").permitAll()
                .antMatchers(HttpMethod.GET,"/experiences/{categoryName}").permitAll()
                .antMatchers(HttpMethod.POST,"/experiences/{categoryName}").permitAll()
                .antMatchers(HttpMethod.GET,"/experiences/{experienceId}/image").permitAll()
                //Reviews
                //TODO
                // ...
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
                .userDetailsService(myUserDetailsService)
                .key(FileCopyUtils.copyToString(new InputStreamReader(authKey.getInputStream())))
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
//                    .accessDeniedPage("/")
            .and().csrf().disable();
    }

}
