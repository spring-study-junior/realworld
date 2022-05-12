package com.study.realworld.auth;


import com.study.realworld.auth.filter.AjaxAuthenticationProcessingFilter;
import com.study.realworld.auth.handler.AjaxAccessDeniedHandler;
import com.study.realworld.auth.handler.AjaxAuthenticationEntryPoint;
import com.study.realworld.auth.handler.AjaxAuthenticationFailureHandler;
import com.study.realworld.auth.handler.AjaxAuthenticationSuccessHandler;
import com.study.realworld.auth.provider.AjaxAuthenticationProvider;
import com.study.realworld.auth.userdetails.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();// test를 위해서 잠시 꺼두기

        http.antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/member/signin").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AjaxAuthenticationProcessingFilter authenticationFilter() throws Exception {
        AjaxAuthenticationProcessingFilter filter = new AjaxAuthenticationProcessingFilter("/api/login");
        filter.setAuthenticationSuccessHandler(new AjaxAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new AjaxAuthenticationFailureHandler());
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AjaxAuthenticationProvider();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AjaxAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AjaxAccessDeniedHandler();
    }
}
