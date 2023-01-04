package com.nnk.springboot.security_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityFilterChain {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilterChain.class);
    @Bean
    org.springframework.security.web.SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        LOGGER.debug("This defaultSecurityFilterChain(from SecurityConfig) starts here.");
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/bidList/list").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }
}
