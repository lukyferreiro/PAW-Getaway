package ar.edu.itba.getaway.webapp.config;

import ar.edu.itba.getaway.webapp.auth.AntMatcherVoter;
import ar.edu.itba.getaway.webapp.auth.CustomAccessDeniedHandler;
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
    private Resource rememberMeKey;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
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

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
            .and().authorizeRequests()
                //Session routes
                .antMatchers("/login/**").anonymous()
                .antMatchers("/logout/**").authenticated()
                .antMatchers(HttpMethod.GET,"/register/**").anonymous()
                .antMatchers(HttpMethod.POST,"/register/**").anonymous()
                .antMatchers("/access-denied/**").permitAll()
                //Verify Account
                .antMatchers("/user/verifyAccount/{token}/**").permitAll()
                .antMatchers("/user/verifyAccount/status/send/**").hasRole("NOT_VERIFIED")
                .antMatchers("/user/verifyAccount/status/resend/**").hasRole("NOT_VERIFIED")
                .antMatchers("/user/verifyAccount/result/unsuccessfully/**").hasRole("NOT_VERIFIED")
                .antMatchers("/user/verifyAccount/result/successfully/**").hasRole("VERIFIED")
                //Reset Password
                .antMatchers(HttpMethod.GET,"/user/resetPasswordRequest/**").anonymous()
                .antMatchers(HttpMethod.POST,"/user/resetPasswordRequest/**").anonymous()
                .antMatchers("/user/resetPassword/{token}/**").anonymous()
                .antMatchers(HttpMethod.GET,"/user/resetPassword/**").denyAll()
                .antMatchers(HttpMethod.POST,"/user/resetPassword/**").anonymous()
                //User
                .antMatchers(HttpMethod.GET,"/search_result/**").permitAll()
                .antMatchers(HttpMethod.POST,"/search_result/**").permitAll()
                .antMatchers(HttpMethod.GET,"/user/profile/edit/**").hasRole("VERIFIED")
                .antMatchers(HttpMethod.POST,"/user/profile/edit/**").hasRole("VERIFIED")
                .antMatchers(HttpMethod.GET,"/user/profile/**").authenticated()
                .antMatchers(HttpMethod.GET,"/user/experiences/**").hasRole("PROVIDER")
                .antMatchers(HttpMethod.GET,"/user/favourites/**").authenticated()
                .antMatchers(HttpMethod.GET,"/user/reviews/**").hasRole("VERIFIED")
                .antMatchers("/user/profileImage/{imageId:[0-9]+}/**").permitAll()
//              ------------------------------SE USA @PreAuthorize-------------------------------
//                .antMatchers(HttpMethod.GET,"/user/experiences/delete/{experienceId:[0-9]+").access("...")
//                .antMatchers(HttpMethod.POST,"/user/experiences/delete/{experienceId:[0-9]+").access("...")
//                .antMatchers(HttpMethod.GET,"/user/experiences/edit/{experienceId:[0-9]+").access("...")
//                .antMatchers(HttpMethod.POST,"/user/experiences/edit/{experienceId:[0-9]+").access("...")
//                .antMatchers(HttpMethod.GET,"/user/reviews/delete/{reviewId:[0-9]+").access("...")
//                .antMatchers(HttpMethod.POST,"/user/reviews/delete/{reviewId:[0-9]+").access("...")
//                .antMatchers(HttpMethod.GET,"/user/reviews/edit/{reviewId:[0-9]+").access("...")
//                .antMatchers(HttpMethod.POST,"/user/reviews/edit/{reviewId:[0-9]+").access("...")
                //Experiences
                .antMatchers(HttpMethod.GET,"/create_experience/**").hasRole("VERIFIED")
                .antMatchers(HttpMethod.POST,"/create_experience/**").hasRole("VERIFIED")
                //Reviews
                .antMatchers(HttpMethod.GET, "/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/create_review/**").hasRole("VERIFIED")
                .antMatchers(HttpMethod.POST, "/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/create_review/**").hasRole("VERIFIED")
                //PermitAll Experiences
                .antMatchers(HttpMethod.GET,"/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/**").permitAll()
                .antMatchers(HttpMethod.GET,"/experiences/{categoryName:[A-Za-z_]+}/**").permitAll()
                .antMatchers(HttpMethod.POST,"/experiences/{categoryName:[A-Za-z_]+}/**").permitAll()
                .antMatchers(HttpMethod.GET,"/experiences/{experienceId:[0-9]+}/image/**").permitAll()
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
                .key(FileCopyUtils.copyToString(new InputStreamReader(rememberMeKey.getInputStream())))
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
