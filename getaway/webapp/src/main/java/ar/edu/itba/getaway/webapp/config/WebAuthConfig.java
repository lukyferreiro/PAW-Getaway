package ar.edu.itba.getaway.webapp.config;

import ar.edu.itba.getaway.webapp.auth.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
                    .antMatchers("/logout").authenticated()
                    //Verify Account
                    .antMatchers("/user/verifyAccount/{token}").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount/status/send").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount/status/resend").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount/result/unsuccessfully").hasRole("NOT_VERIFIED")
//                    .antMatchers("/user/verifyAccount/result/successfully").hasRole("NOT_VERIFIED")
                    .antMatchers("/user/verifyAccount/result/successfully").hasRole("VERIFIED")
                    //Reset Password
                    .antMatchers("/user/resetPasswordRequest").authenticated()
                    .antMatchers(HttpMethod.POST,"/user/resetPasswordRequest").authenticated()
                    .antMatchers(HttpMethod.POST,"/user/resetPassword").authenticated()
                    .antMatchers("/user/resetPassword/{token}").authenticated()
                    //User routes
                    .antMatchers("/user/experiences").authenticated()
                    //Experiences
                    .antMatchers(HttpMethod.GET,"/create_experience").hasRole("VERIFIED")
                    .antMatchers(HttpMethod.POST,"/create_experience").hasRole("VERIFIED")
                    .antMatchers(HttpMethod.GET,"/experiences/{categoryName}/{id}/create_review").hasRole("VERIFIED")
                    .antMatchers(HttpMethod.POST,"/experiences/{categoryName}/{id}/create_review").hasRole("VERIFIED")
                    .antMatchers(HttpMethod.GET,"/experiences/{categoryName}/{experienceId}").permitAll()
                    .antMatchers(HttpMethod.GET,"/experiences/{categoryName}").permitAll()
                    .antMatchers(HttpMethod.POST,"/experiences/{categoryName}").permitAll()
                    .antMatchers(HttpMethod.GET,"/{experienceId}/image").permitAll()
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
                    .accessDeniedHandler(accessDeniedHandler())
//                    .accessDeniedPage("/errors")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/403");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, e) ->
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/access-denied");
    }

}
