package com.mstudent.config;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.mstudent.enums.RoleType;
import com.mstudent.exception.CustomAccessDeniedHandler;
import com.mstudent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig{
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            log.warn("Authentication for '{} {}' failed with error: {}",
                request.getMethod(), request.getRequestURL(), authException.getMessage());
            response.sendError(UNAUTHORIZED.value(), authException.getMessage());
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/room/**")
//            .hasRole(RoleType.ADMIN.getRole())
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/teacher/**")
//            .permitAll()
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/**").authenticated()
                .authorizeHttpRequests()
                .anyRequest().permitAll()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
            .and()
            .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}