package com.example.memegenerator.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;

import com.example.memegenerator.security.UserDetailsAdapter;
import com.example.memegenerator.domain.service.impl.UserServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    
    private static final String USER_PATH = "/user";
    private static final String PASSWORD_RESET_REQUEST_PATH = "/user/password-reset-request";
    private static final String PASSWORD_RESET_PATH = "/user/password-reset";
    private static final String MEME_PATH = "/meme";
    private static final String LIKEDISLIKE_PATH = "/likedislike";
    private static final String LOGIN_PATH = "/login";
    private static final String LOGOUT_PATH = "/logout";
    private static final String HOME_PATH = "/";
    private static final String GET_CATEGORIES_PATH = "/category";
    private ModelMapper modelmapper = new ModelMapper();

    @Autowired
    UserServiceImpl userService;

    
    /** 
     * @param passwordEncoder
     * @return AuthenticationProvider
     */
    @Bean
    protected AuthenticationProvider authenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userService);
        return authProvider;
    }

    
    /** 
     * @return BCryptPasswordEncoder
     */
    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    /** 
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        assert auth != null;
        auth.authenticationProvider(authenticationProvider(passwordEncoder()));
    }

    
    /** 
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, HOME_PATH).permitAll()
                .antMatchers(HttpMethod.GET, GET_CATEGORIES_PATH).permitAll()
                .antMatchers(HttpMethod.GET, USER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, USER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, LOGIN_PATH).permitAll()
                .antMatchers(HttpMethod.POST, LOGOUT_PATH).permitAll()
                .antMatchers(HttpMethod.PUT, USER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, PASSWORD_RESET_REQUEST_PATH).permitAll()
                .antMatchers(HttpMethod.POST, PASSWORD_RESET_PATH).permitAll()
                .antMatchers(HttpMethod.GET, "/user/activate/{id:\\d+}/{token:\\d+}").permitAll()
                .antMatchers(LIKEDISLIKE_PATH).permitAll()
                .antMatchers(MEME_PATH).permitAll()
                .anyRequest().permitAll()
                .and().httpBasic().and().formLogin().successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
                        var principal = modelmapper.map(authentication.getPrincipal(), UserDetailsAdapter.class);
                        response.getWriter().write("{ \"status\": true, \"userId\": " + principal.getUserId() +" }");
                    }
                }).failureHandler(new AuthenticationFailureHandler() {

                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                            AuthenticationException exception) throws IOException, ServletException {
                        response.getWriter().write("{ \"status\": false }");
                    }
                }).and().sessionManagement().maximumSessions(1);
    }

    
    /** 
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
}