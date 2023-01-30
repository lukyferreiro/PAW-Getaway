package ar.edu.itba.getaway.webapp.config;

import ar.edu.itba.getaway.webapp.security.api.*;
import ar.edu.itba.getaway.webapp.security.api.handlers.AuthFailureHandler;
import ar.edu.itba.getaway.webapp.security.api.handlers.AuthSuccessHandler;
import ar.edu.itba.getaway.webapp.security.api.handlers.CustomAccessDeniedHandler;
import ar.edu.itba.getaway.webapp.security.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.getaway.webapp.security")
@PropertySource(value= {"classpath:application.properties"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

//    @Autowired
//    private AuthEntryPoint authEntryPoint;

    @Autowired
    private BasicAuthProvider basicAuthProvider;

    @Autowired
    private JwtAuthProvider jwtAuthProvider;

//    @Autowired
//    private AuthSuccessHandler authSuccessHandler;
//
//    @Autowired
//    private AuthFailureHandler authFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AntMatcherVoter antMatcherVoter() { return new AntMatcherVoter();}

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthEntryPoint();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(basicAuthProvider).authenticationProvider(jwtAuthProvider);
    }

    @Bean
    public BridgeAuthFilter bridgeAuthFilter() throws Exception {
        BridgeAuthFilter bridgeAuthFilter = new BridgeAuthFilter();
        bridgeAuthFilter.setAuthenticationManager(authenticationManagerBean());
        bridgeAuthFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        bridgeAuthFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return bridgeAuthFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "X-Total-Pages", "Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/", "*.css", "/*.js", "/favicon.ico", "/manifest.json");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //TODO check si esto va
//                .and()
//                    .headers().cacheControl().disable()
                .and().authorizeRequests()
                //------------------- /users -------------------
                    .antMatchers(HttpMethod.POST, "/api/users").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/users/{id}").anonymous()
                    .antMatchers(HttpMethod.PUT, "/api/users/{id}").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/users/{id}/profileImage").anonymous()
                    .antMatchers(HttpMethod.PUT, "/api/users/{id}/profileImage").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/users/{id}/experiences").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/users/{id}/reviews").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/users/{id}/favExperiences").anonymous()
                    .antMatchers(HttpMethod.PUT, "/api/users/emailVerification").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/users/emailVerification").anonymous()
                    .antMatchers(HttpMethod.PUT, "/api/users/passwordReset").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/users/passwordReset").anonymous()
                //------------------- /experiences -------------------
                    .antMatchers(HttpMethod.GET, "/api/experiences/{category}").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/experiences").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/experiences/experience/{id}").anonymous()
                    .antMatchers(HttpMethod.PUT, "/api/experiences/experience/{id}").anonymous()
                    .antMatchers(HttpMethod.DELETE, "/api/experiences/experience/{id}").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/experiences/experience/{id}/image").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/experiences/experience/{id}/reviews").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/experiences/experience/{id}/reviews").anonymous()
                //------------------- /reviews -------------------
                    .antMatchers(HttpMethod.GET, "/api/reviews/{id}").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/reviews/{id}").anonymous()
                    .antMatchers(HttpMethod.DELETE, "/api/reviews/{id}").anonymous()
                //------------------- /location -------------------
                    .antMatchers(HttpMethod.GET, "/api/location/countries").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/location/countries/{id}/cities").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/location/cities/{id}").anonymous()
                //------------------- Others --------------------
                    .antMatchers("/**").permitAll()

                //-------------------Session routes-------------------
//                .antMatchers("/login/**").anonymous()
//                .antMatchers("/logout/**").authenticated()
//                .antMatchers(HttpMethod.GET, "/register/**").anonymous()
//                .antMatchers(HttpMethod.POST, "/register/**").anonymous()
//                .antMatchers("/access-denied/**").permitAll()
                //-------------------Verify Account-------------------
//                .antMatchers("/pleaseVerify").hasRole("NOT_VERIFIED")
//                .antMatchers("/user/verifyAccount/result/unsuccessfully/**").hasRole("NOT_VERIFIED")
//                .antMatchers("/user/verifyAccount/status/resend/**").hasRole("NOT_VERIFIED")
//                .antMatchers("/user/verifyAccount/status/send/**").hasRole("NOT_VERIFIED")
//                .antMatchers("/user/verifyAccount/result/successfully/**").hasRole("VERIFIED")
//                .antMatchers("/user/verifyAccount/{token}/**").permitAll()
                //-------------------Reset Password-------------------
//                .antMatchers(HttpMethod.GET, "/user/resetPasswordRequest/**").anonymous()
//                .antMatchers(HttpMethod.POST, "/user/resetPasswordRequest/**").anonymous()
//                .antMatchers("/user/resetPassword/{token}/**").anonymous()
//                .antMatchers(HttpMethod.GET, "/user/resetPassword/**").denyAll()
//                .antMatchers(HttpMethod.POST, "/user/resetPassword/**").anonymous()
                //-------------------User-------------------
//                .antMatchers(HttpMethod.GET, "/search_result/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/search_result/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/profile/edit/**").hasRole("VERIFIED")
//                .antMatchers(HttpMethod.POST, "/user/profile/edit/**").hasRole("VERIFIED")
//                .antMatchers(HttpMethod.GET, "/user/profile/**").authenticated()
//                .antMatchers(HttpMethod.GET, "/user/experiences/**").hasRole("PROVIDER")
//                .antMatchers(HttpMethod.GET, "/user/favourites/**").authenticated()
//                .antMatchers(HttpMethod.GET, "/user/reviews/**").hasRole("VERIFIED")
//                .antMatchers("/user/profileImage/{imageId:[0-9]+}/**").permitAll()
                //------------------------------SE USA @PreAuthorize-------------------------------
                //                .antMatchers(HttpMethod.GET,"/user/experiences/delete/{experienceId:[0-9]+").access("...")
                //                .antMatchers(HttpMethod.POST,"/user/experiences/delete/{experienceId:[0-9]+").access("...")
                //                .antMatchers(HttpMethod.GET,"/user/experiences/edit/{experienceId:[0-9]+").access("...")
                //                .antMatchers(HttpMethod.POST,"/user/experiences/edit/{experienceId:[0-9]+").access("...")
                //                .antMatchers(HttpMethod.GET,"/user/reviews/delete/{reviewId:[0-9]+").access("...")
                //                .antMatchers(HttpMethod.POST,"/user/reviews/delete/{reviewId:[0-9]+").access("...")
                //                .antMatchers(HttpMethod.GET,"/user/reviews/edit/{reviewId:[0-9]+").access("...")
                //                .antMatchers(HttpMethod.POST,"/user/reviews/edit/{reviewId:[0-9]+").access("...")
                //-------------------Experiences-------------------
//                .antMatchers(HttpMethod.GET, "/create_experience/**").hasRole("VERIFIED")
//                .antMatchers(HttpMethod.POST, "/create_experience/**").hasRole("VERIFIED")
                //-------------------Reviews-------------------
//                .antMatchers(HttpMethod.GET, "/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/create_review/**").hasRole("VERIFIED")
//                .antMatchers(HttpMethod.POST, "/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/create_review/**").hasRole("VERIFIED")
                //PermitAll Experiences
//                .antMatchers(HttpMethod.GET, "/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/experiences/{categoryName:[A-Za-z_]+}/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/experiences/{categoryName:[A-Za-z_]+}/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/experiences/{experienceId:[0-9]+}/image/**").permitAll()
                //else
//                .antMatchers("/**").permitAll()
                .and()
                    .addFilterBefore(bridgeAuthFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}