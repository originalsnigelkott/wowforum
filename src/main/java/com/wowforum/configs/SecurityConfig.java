package com.wowforum.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private MyUserDetailsService myUserDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(myUserDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
        .and()
      .csrf().disable()
      .authorizeRequests()
        .antMatchers(
          HttpMethod.GET,
          "/api/v1/forums",
          "/api/v1/forums/**",
          "/api/v1/threads/**",
          "/api/v1/posts/**"
        ).permitAll()
        .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
        .anyRequest().authenticated()
        .and()
      .formLogin()
          .loginPage("/login")
          .loginProcessingUrl("/api/auth/login").permitAll()
          .successHandler(successHandler())
          .failureHandler(failureHandler())
          .and()
      .logout()
          .logoutUrl("/api/auth/logout")
          .logoutSuccessHandler(logoutSuccessHandler())
          .deleteCookies("JSESSIONID")
          .and()
      .exceptionHandling()
        .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private AuthenticationSuccessHandler successHandler() {
    return new AuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        response.getWriter().append("Authentication successful.");
        response.setStatus(200);
      }
    };
  }

  private AuthenticationFailureHandler failureHandler() {
    return new AuthenticationFailureHandler() {
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.getWriter().append("Authentication failed.");
        response.setStatus(401);
      }
    };
  }

  private LogoutSuccessHandler logoutSuccessHandler() {
    return new LogoutSuccessHandler() {
      @Override
      public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        response.getWriter().append("Successfully logged out.");
        response.setStatus(200);
      }
    };
  }
}