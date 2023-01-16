package com.nnk.springboot.security_config;

import com.nnk.springboot.service.user_service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    // JpaUserDetailsManager is used to load users form the database
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        LOGGER.debug("This defaultSecurityFilterChain(from SecurityConfig) starts here.");

        // Lambda DSL
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .antMatchers("/").permitAll()
                        .antMatchers("/css/**", "/error/**", "/img/**").permitAll()
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers("/user/list", "/secure/article-details").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin()
                .loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
                //.defaultSuccessUrl("/api/bidList/list").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll().and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .and().build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
