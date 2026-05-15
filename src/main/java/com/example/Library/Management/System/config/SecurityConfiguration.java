package com.example.Library.Management.System.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuth;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/demo-controller/getadmin","/api/v1/demo-controller/adduser","/api/v1/admin/adduser","/api/books/returnbook","/api/books/borrowbookrecoard").hasAuthority("ADMIN")
                        .requestMatchers("/api/books/reserve").hasAuthority("USER")
                        .requestMatchers("/api/v1/admin/getallmembers","/api/v1/admin/getuseraccount","/api/v1/admin/getinformation","/api/v1/admin/addinformation","/api/books/reservationdetails","/api/books/issueBookdetails","/api/books/returnbookdetails").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/admin/getuseraccount","api/v1/admin/{id}").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/demo-controller/getuser").hasAuthority("USER")
                        .requestMatchers("/api/v1/auth/updateprofile").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/api/v1/auth/register", "/api/v1/auth/authenticate","/api/books/all","/api/books/add","/api/rag")
                        .permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuth, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
        ;

        return httpSecurity.build();
    }
}

