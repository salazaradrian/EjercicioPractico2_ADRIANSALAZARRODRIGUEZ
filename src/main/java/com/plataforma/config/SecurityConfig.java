package com.plataforma.config;

import com.plataforma.service.impl.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    // BCrypt para encriptar contraseñas
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticación
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    // Redirección después del login según rol
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {

            String rol = authentication.getAuthorities().iterator().next().getAuthority();

            switch (rol) {
                case "ROLE_ADMIN":
                    response.sendRedirect("/usuarios");
                    break;

                case "ROLE_PROFESOR":
                    response.sendRedirect("/reportes");
                    break;

                case "ROLE_ESTUDIANTE":
                    response.sendRedirect("/perfil");
                    break;

                default:
                    response.sendRedirect("/");
            }
        };
    }

    // Filtro principal de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/error/**"
                        ).permitAll()

                        // Rutas ADMIN
                        .requestMatchers("/usuarios/**", "/roles/**").hasRole("ADMIN")

                        // Rutas PROFESOR
                        .requestMatchers("/reportes/**").hasRole("PROFESOR")

                        // Rutas ESTUDIANTE
                        .requestMatchers("/perfil/**").hasRole("ESTUDIANTE")

                        // cualquier otra ruta requiere login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")
                );

        return http.build();
    }
}
