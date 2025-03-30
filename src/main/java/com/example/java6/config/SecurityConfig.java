package com.example.java6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Tắt CSRF để kiểm tra (nên bật lại trong môi trường thực
                                                              // tế)
                                .cors(cors -> {
                                }) // Bật CORS nếu API được gọi từ frontend
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Cho
                                                                                                                // phép
                                                                                                                // truy
                                                                                                                // cập
                                                                                                                // file
                                                                                                                // tĩnh
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/user/**").hasRole("USER")
                                                .requestMatchers("/login", "/auth/**", "/home/**", "/new-products/**",
                                                                "/services/**", "/products")
                                                .permitAll()
                                                .anyRequest().authenticated())

                                .formLogin(login -> login
                                                .loginPage("/auth/login") // Trang đăng nhập tùy chỉnh
                                                .loginProcessingUrl("/auth/login") // Spring xử lý form login
                                                .defaultSuccessUrl("/home", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                                                .logoutSuccessUrl("/home")
                                                .invalidateHttpSession(true) // Xóa session khi đăng xuất
                                                .clearAuthentication(true)
                                                .permitAll())

                                .sessionManagement(session -> session
                                                .maximumSessions(1) // Chỉ cho phép 1 phiên hoạt động trên mỗi tài khoản
                                                .expiredUrl("/auth/login?expired")) // Chuyển hướng nếu session hết hạn
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/auth/access-denied")); // Trang lỗi khi không đủ
                                                                                           // quyền

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }
}
