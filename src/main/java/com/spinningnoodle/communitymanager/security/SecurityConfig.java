package com.spinningnoodle.communitymanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        AuthenticationEntryPoint aep = (request, response, authException) -> response.sendRedirect("/upcoming");
//
//        http
//            .exceptionHandling()
//            .authenticationEntryPoint(aep);
        
        http
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers( "/")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .defaultSuccessUrl("/loginSuccess")
//            .failureUrl("/loginFail")
//            .loginPage("/")
            .and()
            .logout().logoutSuccessUrl("/").permitAll();
    }
    
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }
}
