package com.sparta.jpahibernate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig {
    @Bean
    public InMemoryUserDetailsManager configureUsers() throws Exception {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build();
        UserDetails update = User.withDefaultPasswordEncoder()
                .username("update")
                .password("password")
                .roles("UPDATE")
                .build();
        UserDetails basic = User.withDefaultPasswordEncoder()
                .username("basic")
                .password("password")
                .roles("BASIC")
                .build();
        return new InMemoryUserDetailsManager(admin, update, basic);
    }

    @Bean
    public SecurityFilterChain configurePolicy(HttpSecurity http) throws Exception {
        return http.authorizeRequests(auth -> {
                    auth.requestMatchers("/web/department/new",
                                    "/web/department/update/**",
                                    "/web/employee/new",
                                    "/web/employee/update/**")
                            .hasAnyRole("ADMIN", "UPDATE");
                })
                .authorizeRequests(auth -> {
                    auth.requestMatchers("/web/department/delete",
                                    "/web/employee/delete")
                            .hasRole("ADMIN");
                })
                .authorizeRequests(auth -> {
                    auth.requestMatchers("/web/employee/**",
                                    "/web/department/**")
                            .hasAnyRole("ADMIN", "UPDATE", "BASIC");
                })
                .formLogin()
                .loginPage("/web/user/login")
//                .loginProcessingUrl("/login")
                .successForwardUrl("/web/user/loginSuccess")
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .and()
                .build();
    }
}