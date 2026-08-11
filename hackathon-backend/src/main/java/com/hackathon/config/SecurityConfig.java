package com.hackathon.config;

import com.hackathon.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // réutilise addCorsMappings() défini dans AppConfig
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.authorizeHttpRequests(auth -> auth
                //        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //        .requestMatchers("/api/auth/**").permitAll()
                //        .requestMatchers("/api/jury/**").hasAnyRole("JURY", "ADMIN")
                //        .requestMatchers("/api/teams/**", "/api/projects/**").hasAnyRole("PARTICIPANT", "ADMIN")
                //        .requestMatchers("/api/leaderboard/**").authenticated()
                //        .anyRequest().authenticated()
                //)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/teams/**", "/api/projects/**", "/api/leaderboard/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/teams", "/api/teams/*/join", "/api/projects").hasRole("PARTICIPANT")
                        .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasRole("PARTICIPANT")

                        .requestMatchers("/api/jury/**").hasRole("JURY")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}