package com.example.backend.config;

import com.example.backend.config.filter.JwtFilter;
import com.example.backend.config.filter.LoginFilter;
import com.example.backend.user.service.UserService;
import com.example.backend.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOriginPatterns(List.of("http://localhost:5173"));
//        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        corsConfig.setAllowedHeaders(List.of("*"));
//        corsConfig.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return source;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            Cookie cookie = new Cookie("Authorization", null);
                            cookie.setHttpOnly(true);
                            cookie.setSecure(true);
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/user/signup/{role}", "/logout").permitAll()  // 로그인, 회원가입 허용
                        .requestMatchers("/v3/**", "/v3/api-docs/**", "/swagger-ui/**",
                                "/swagger-ui.html", "/swagger-resources/**", "/favicon.ico").permitAll()
                        .requestMatchers("/board/**").hasAnyRole("STUDENT", "INSTRUCTOR", "MANAGER") // 게시판은 로그인한 회원이라면 모두 허용
                        .requestMatchers("/course/**").hasAnyRole("STUDENT", "INSTRUCTOR", "MANAGER") // 수업은 로그인한 회원이라면 모두 허용
                        .requestMatchers("/student/**").hasAnyRole("STUDENT", "INSTRUCTOR", "MANAGER") // 학생 기능은 학생에게만 허용
                        .requestMatchers("/instructor/**").hasAnyRole("INSTRUCTOR", "STUDENT", "MANAGER") // 강사 기능은 강사에게만 허용
                        .requestMatchers("/manager/**").hasAnyRole("INSTRUCTOR", "STUDENT", "MANAGER") // 매니저 기능은 매니저에게만 허용
                        .anyRequest().authenticated()
                )
                .addFilterAt(new LoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)  // 로그인 필터 추가
                .addFilterBefore(new JwtFilter(userService), UsernamePasswordAuthenticationFilter.class);  // JWT 필터 추가

        return http.build();
    }

}
