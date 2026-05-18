package com.grupo2.EduPerformance.EduPerformance.config;

import com.grupo2.EduPerformance.EduPerformance.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsServiceImpl userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso público a la documentación (Swagger y Scalar)
                .requestMatchers("/v3/api-docs/**", "/api-docs", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/scalar/**").permitAll()
                // Permitir crear usuarios (registro) sin estar logueado
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                // Cualquier otra petición requiere estar autenticado
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // Usar autenticación básica (Header Authorization: Basic base64)

        return http.build();
    }
}
