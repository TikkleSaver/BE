package com.tikklesaver.global.config;

import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.member.service.CustomUserDetailsService;
import com.tikklesaver.global.jwt.filter.JwtAuthFilter;
import com.tikklesaver.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private static final String[] AUTH_WHITELIST = {"/api/login", "/api/signup","/api/refresh-token","/api/check-id"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MemberRepository memberRepository) throws Exception {
        http
                // CORS 설정
                .cors(cors -> cors
                        .configurationSource(CorsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        // Swagger UI 관련 경로에 접근 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        // 기타 경로 설정
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/api/**").permitAll()

                        .requestMatchers("/api/check-id/**").permitAll()
                        .requestMatchers("api/users/onboarding/**").permitAll()
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                );

        //세션 관리 상태 없음으로 구성, Spring Security가 세션 생성 or 사용 x
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        //FormLogin, BasicHttp 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

//        //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가 // 임시로 주석
        http.addFilterBefore(
                new JwtAuthFilter(customUserDetailsService, jwtUtil, memberRepository),
                UsernamePasswordAuthenticationFilter.class
        );


        //예외 처리 핸들러 설정: 인증 실패 및 접근 거부 예외를 처리하는 핸들러 설정
        //        http.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(
//                authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }
}