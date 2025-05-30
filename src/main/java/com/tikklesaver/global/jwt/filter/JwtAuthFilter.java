package com.tikklesaver.global.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.member.service.CustomUserDetailsService;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.JwtHandler;
import com.tikklesaver.global.jwt.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    /**
     * JWT 검증 필터 수행
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException, JwtHandler {
        String authorizationHeader = request.getHeader("Authorization");

        //JWT 헤더가 있을 경우
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            //JWT 유효성 검증
            if (!jwtUtil.isValidToken(token)) {
                SecurityContextHolder.clearContext(); // 인증 정보 명시적으로 제거
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드
                response.setContentType("application/json;charset=UTF-8"); // JSON 응답 설정
                response.getWriter().write("{\"message\": \"유효하지 않은 토큰입니다.\"}"); // 에러 메시지 바디
                return; // 요청 종료 (다음 필터로 넘기지 않음)
            } else{
                String loginId = jwtUtil.getUserId(token);

                Optional<Member> member = memberRepository.findByLoginId(loginId);
                UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(member.get().getLoginId())
                        .password(member.get().getPassword())
//                        .roles(myMember.getRole().name())
                        .build();

                if (userDetails != null) {
                    //UserDetails, Password, Role -> 접근 권한 인증 Token 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    //현재 Request의 Security Context에 접근 권한 설정
                    SecurityContextHolder.getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response); //다음 필터로 넘김
    }
}