package com.finalProject.linkedin.config.security;

import com.finalProject.linkedin.entity.User;
import com.finalProject.linkedin.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.springframework.http.HttpStatus.OK;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/login",
                                "/api/auth",
                                "/",
                                "/api/confirm",
                                "/oauth2/**",
                                "api/password-forgot",
                                "api/password-reset",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .requestMatchers("/api/user/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/api/login")
                        .defaultSuccessUrl("http://localhost:3000/customer", true)
                        .permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/api/login")
                        .defaultSuccessUrl("/api/user", true)
                        .successHandler((req, res, auth) -> {
                            if (auth != null) {
                                res.sendRedirect("/api/user");
                            } else {
                                res.sendRedirect("/api/login");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                                    log.error("Authentication failed: {}", exception.getMessage());
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
                                }
                        )
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret") // ключ шифрования для cookies
                        .tokenValiditySeconds(7 * 24 * 60 * 60) // одна неделя
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll());
        return http.build();
    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {
        return (HttpServletRequest req, HttpServletResponse res, Authentication authentication) -> {
            res.setStatus(OK.value());
            res.getWriter().flush();
        };
    }

    @Bean
    public UserDetailsService userDetailsService(UserServiceImpl userServiceImpl) {
        return email -> {

            Boolean isVerified = userServiceImpl.isUserVerified(email);
            if (isVerified == null || !isVerified) {
                log.warn("Юзер не подтвержден: {}", email);
                throw new BadCredentialsException("Почта не подтверждена!.");
            }

            User user = userServiceImpl.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));


            log.warn("Имейл: {}", email);
            log.warn("Зашифрованный пароль из базы данных: {}", user.getPassword());

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}