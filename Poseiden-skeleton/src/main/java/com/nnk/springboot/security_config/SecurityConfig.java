package com.nnk.springboot.security_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.withUsername("user_test").password(passwordEncoder().encode("12345")).roles("USER").build();
        UserDetails admin = User.withUsername("admin_test").password(passwordEncoder().encode("12345")).roles("ADMIN", "USER").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        LOGGER.debug("This defaultSecurityFilterChain(from SecurityConfig) starts here.");

        // Lambda DSL
        return http
                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
                .authorizeRequests(auth -> auth
                        .antMatchers("/").permitAll()
                        .antMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                        .antMatchers("/css/**", "/error/**").permitAll()
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers("/user/list").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin()
                .defaultSuccessUrl("/api/bidList/list").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll().and()
                .exceptionHandling().accessDeniedPage("/403")
                .and().build();

        //regular expression
            /* return http
            .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
              /*  .antMatchers("** /user").hasRole("ADMIN")*/
               /* .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/api/bidList/list").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll()
                .and().build(); */

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}