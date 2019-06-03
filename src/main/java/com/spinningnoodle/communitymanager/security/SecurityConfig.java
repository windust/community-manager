package com.spinningnoodle.communitymanager.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This security config class helps with the google oauth login.
 * The antMatchers authorizes every file that doesn't need login
 * permission to access then everything after that needs ouath login
 * permission.
 * @author Cream 4 Ur Coffee
 * @version 0.1
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/", "/css/main.css", "/js/available_dates.js", "/images/logo_draft_1.png",
                    "/venue", "/venueSignUp", "/food", "/foodSignUp")
                    .permitAll()
            .anyRequest().authenticated()
                .and()
            .oauth2Login()
                .defaultSuccessUrl("/loginSuccess", true)
                .and()
            .logout()
                .logoutUrl("/log_out")
                .logoutSuccessUrl("/")
                .and()
            .csrf().disable();
    }
}
