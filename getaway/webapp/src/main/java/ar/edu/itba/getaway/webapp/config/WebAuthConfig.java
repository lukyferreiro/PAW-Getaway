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
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource(value= {"classpath:application.properties"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private BasicAuthProvider basicAuthProvider;
    @Autowired
    private JwtAuthProvider jwtAuthProvider;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AntMatcherVoter antMatcherVoter() {
        return new AntMatcherVoter();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
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
    public AuthFilter authFilter() throws Exception {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setAuthenticationManager(authenticationManagerBean());
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        authFilter.setAuthenticationFailureHandler(authFailureHandler);
        return authFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Location", "Cache-Control"));
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization", "X-Total-Pages", "Content-Disposition",
                "Location", "Link", "Cache-Control", "Content-Type",
                "Getaway-Access-JWT", "Getaway-Refresh-JWT"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
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
                .csrf()
                .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .headers().cacheControl().disable()
                .and().authorizeRequests()
                //------------------- /users -------------------
                    //anonymous para crear un usuario
                    .antMatchers(HttpMethod.POST, "/api/users").anonymous()
                    //se usa solo para usuarios logueados para su propia info
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}").permitAll()
                    //logueado y hay que revisar que el id sea el mismo del logueado
                    .antMatchers(HttpMethod.PUT, "/api/users/{userId}").access("@antMatcherVoter.userEditHimself(authentication, #userId)")
                    //¿Olvidaste tu contraseña?
                    .antMatchers(HttpMethod.PATCH, "/api/users").anonymous()
                    // .antMatchers(HttpMethod.POST, "/api/users").anonymous()
                    //permitAll
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}/profileImage").permitAll()
                    //logueado y hay que revisar que el id sea el mismo del logueado
                    .antMatchers(HttpMethod.PUT, "/api/users/{userId}/profileImage").access("@antMatcherVoter.userEditHimself(authentication, #userId)")
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}/favourites/{experienceId}").access("@antMatcherVoter.accessUserInfo(authentication, #userId)")
                    .antMatchers(HttpMethod.PUT, "/api/users/{userId}/favourites/{experienceId}").access("@antMatcherVoter.accessUserInfo(authentication, #userId)")

                //------------------- /experiences -------------------
                    //logueado y VERIFIED (pues rol PROVIDER se asigna en el momento)
                    .antMatchers(HttpMethod.POST, "/api/experiences").hasAuthority("VERIFIED")
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/experiences").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/experiences/orders").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/experiences/categories").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/experiences/categories/{categoryId}").permitAll()
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/experiences/{experienceId}").permitAll()
                    //logueado, VERIFIED y PROVIDER, chequear que sea el mismo usuario
                    .antMatchers(HttpMethod.PUT, "/api/experiences/{experienceId}").access("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
                    .antMatchers(HttpMethod.PATCH, "/api/experiences/{experienceId}").access("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
                    .antMatchers(HttpMethod.DELETE, "/api/experiences/{experienceId}").access("@antMatcherVoter.canDeleteExperienceById(authentication, #experienceId)")
                    .antMatchers(HttpMethod.PUT, "/api/experiences/{experienceId}/experienceImage").access("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/experiences/{experienceId}/experienceImage").permitAll()
                    //logueado
                    .antMatchers(HttpMethod.PUT, "/api/experience/{experienceId}/fav").authenticated()
                //------------------- /reviews -------------------
                    //logueado y VERIFIED, chequear que sea el mismo usuario
                    .antMatchers(HttpMethod.GET, "/api/reviews").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/reviews").hasAuthority("VERIFIED")
                    .antMatchers(HttpMethod.GET, "/api/reviews/{reviewId}").permitAll()
                    .antMatchers(HttpMethod.PUT, "/api/reviews/{reviewId}").access("@antMatcherVoter.canEditReviewById(authentication, #reviewId)")
                    .antMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}").access("@antMatcherVoter.canDeleteReviewById(authentication, #reviewId)")
                //------------------- /location -------------------
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/location/countries").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/location/countries/{countryId}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/location/countries/{countryId}/cities").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/location/cities/{cityId}").permitAll()
                //------------------- Others --------------------
                    .antMatchers("/**").permitAll()
                .and()
                    .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}