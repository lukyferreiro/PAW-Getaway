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

    @Autowired
    private BasicAuthProvider basicAuthProvider;

    @Autowired
    private JwtAuthProvider jwtAuthProvider;

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
                .and()
                    .headers().cacheControl().disable()
                .and().authorizeRequests()
                //------------------- /users -------------------
                    //anonymous porque sino no se puede acceder a boton register
                    .antMatchers(HttpMethod.POST, "/api/users").anonymous()
                    //permitAll, porque no se usa solo para usuarios logueados va para cualquiera
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/users/currentUser").permitAll() //TODO: maybe authenticated
                    //logueado y rol NOT VERIFIED
                    .antMatchers(HttpMethod.PUT, "/api/users/emailVerification").hasAuthority("NOT_VERIFIED")    //TODO check si es hasRole
                    .antMatchers(HttpMethod.POST, "/api/users/emailVerification").hasAuthority("NOT_VERIFIED")    //TODO check si es hasRole
                    //¿Olvidaste tu contraseña?
                    .antMatchers(HttpMethod.PUT, "/api/users/passwordReset").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/users/passwordReset").anonymous()
                    //logueado y hay que revisar que el id sea el mismo del logueado
                    .antMatchers(HttpMethod.PUT, "/api/users/{userId}").access("@antMatcherVoter.userEditHimself(authentication, #userId)")
                    //permitAll, igual que el get by id
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}/profileImage").permitAll()
                    //logueado y hay que revisar que el id sea el mismo del logueado
                    .antMatchers(HttpMethod.PUT, "/api/users/{userId}/profileImage").access("@antMatcherVoter.userEditHimself(authentication, #userId)")
                    //logueado y tiene que tener rol PROVIDER
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}/experiences").hasAuthority("PROVIDER")    //TODO check si es hasRole
                    //logueado y tiene que tener rol VERIFIED
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}/reviews").hasAuthority("VERIFIED")    //TODO check si es hasRole
                    //logueado y tiene que tener rol USER nada más
                    .antMatchers(HttpMethod.GET, "/api/users/{userId}/favExperiences").access("@antMatcherVoter.accessFavs(authentication, #userId)")
                //------------------- /experiences -------------------
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/experiences/landingPage").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/experiences/category/{category}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/experiences/name/{name}").permitAll()
                    //logueado y VERIFIED (rol PROVIDER) se asigna en el momento
                    .antMatchers(HttpMethod.POST, "/api/experiences").hasAuthority("VERIFIED")    //TODO check si es hasRole
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/experiences/experience/{experienceId}").permitAll()
                    //logueado, VERIFIED y PROVIDER, chequear que sea el mismo usuario
                    .antMatchers(HttpMethod.PUT, "/api/experiences/experience/{experienceId}").access("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
                    .antMatchers(HttpMethod.PUT, "/api/experiences/experience/{experienceId}/experienceImage").access("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
                    .antMatchers(HttpMethod.DELETE, "/api/experiences/experience/{experienceId}").access("@antMatcherVoter.canDeleteExperienceById(authentication, #experienceId)")
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/experiences/experience/{experienceId}/experienceImage").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/experiences/experience/{experienceId}/reviews").permitAll()
                    //logueado y VERIFIED
                    .antMatchers(HttpMethod.POST, "/api/experiences/experience/{experienceId}/reviews").hasAuthority("VERIFIED")    //TODO check si es hasRole
                    .antMatchers(HttpMethod.PUT, "/experience/{experienceId}/fav").authenticated()
                    .antMatchers(HttpMethod.PUT, "/experience/{experienceId}/observable").access("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
                //------------------- /reviews -------------------
                    //logueado y VERIFIED, chequear que sea el mismo usuario
                    .antMatchers(HttpMethod.GET, "/api/reviews/{reviewId}").hasAuthority("VERIFIED")    //TODO check si es hasRole
                    .antMatchers(HttpMethod.PUT, "/api/reviews/{reviewId}").access("@antMatcherVoter.canEditReviewById(authentication, #reviewId)")
                    .antMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}").access("@antMatcherVoter.canDeleteReviewById(authentication, #reviewId)")
                //------------------- /location -------------------
                    //permitAll para explorar
                    .antMatchers(HttpMethod.GET, "/api/location/countries").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/location/countries/{countryId}/cities").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/location/cities/{cityId}").permitAll()
                //------------------- Others --------------------
                    .antMatchers("/**").permitAll()
                .and()
                    .addFilterBefore(bridgeAuthFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}