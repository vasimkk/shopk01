package com.shop.shop.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/shop/login").permitAll()
                .requestMatchers("/api/auth/shop/register/**").permitAll() // allow user & seller
                // admin is still protected by @PreAuthorize
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
