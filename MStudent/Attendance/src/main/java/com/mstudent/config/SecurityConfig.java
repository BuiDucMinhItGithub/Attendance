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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public WebMvcConfigurer configure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry reg) {
                reg.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedHeaders("*")
                    .allowedMethods("GET","POST","PUT","DELETE")
                    .allowCredentials(false)
                    .maxAge(86400);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
            .csrf()
            .disable();
//            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/teacher/**","/api/v1/student/**","/api/v1/attendance/**","/api/v1/cost/**")
//            .hasRole(RoleType.TEACHER.getRole())
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/admin/**")
//            .hasRole(RoleType.ADMIN.getRole())
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/room")
//            .permitAll()
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/auth/**")
//            .permitAll()
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/**").authenticated()
//            .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .exceptionHandling()
//            .authenticationEntryPoint(restAuthenticationEntryPoint())
//            .accessDeniedHandler(accessDeniedHandler())
//            .and()
//            .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
//            .logout()
//            .logoutUrl("/api/v1/auth/logout")
//            .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));

        return http.build();
    }

}
