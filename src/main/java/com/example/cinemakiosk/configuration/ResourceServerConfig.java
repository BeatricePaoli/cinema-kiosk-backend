package com.example.cinemakiosk.configuration;

import com.example.cinemakiosk.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
public class ResourceServerConfig {

    @Value("${cross-origins}")
    String crossOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(getCorsConfig()))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("api/movies", "api/context-broker/**")
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("api/movies", "api/movies/**").permitAll()

                        .requestMatchers("api/shows", "api/shows/**").permitAll()

                        .requestMatchers("api/theaters").hasRole(Role.ADMIN.toString())
                        .requestMatchers("api/theaters/filter").permitAll()
                        .requestMatchers("api/theaters/{id}").hasRole(Role.ADMIN.toString())
                        .requestMatchers("api/theaters/{id}/tickets").permitAll()

                        .requestMatchers("api/devices/**").hasRole(Role.ADMIN.toString())

                        .requestMatchers(HttpMethod.PATCH,"api/bookings/**").hasRole(Role.CASHIER.toString())
                        .requestMatchers("api/bookings", "api/bookings/**").authenticated()

                        .requestMatchers("api/context-broker/**").permitAll()
                        .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .httpBasic(withDefaults());
        return http.build();
    }

    private CorsConfigurationSource getCorsConfig() {
        log.info("Cross Origins setted to: {}", this.crossOrigins);
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(crossOrigins));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return jwtConverter;
    }
}