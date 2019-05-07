package com.spinningnoodle.communitymanager.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/", "/css/main.css", "/js/available_dates.js", "/images/logo_draft_1.png", "/venue", "/venueSignUp")
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
