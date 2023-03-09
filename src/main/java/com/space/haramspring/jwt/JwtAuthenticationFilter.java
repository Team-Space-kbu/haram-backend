package com.space.haramspring.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter - Class(Filter) ㅣ
 * Spring MVC 에서 FrontController 로 동작하는 DispatcherController 에 도착하기 전에,
 * 허가되지 않은 인증에 Spring Security 는 FilterChain 을 지나치도록 동작한다.
 * SpringSecurity 에서 유저의 정보를 확인하기 위해 UsernamePasswordAuthenticationFilter 가 동작한다.
 * SecurityConfig 에서 설정되어 있는 보안 레벨에 따라 동작하고, permitAll 명령어가 붙지 않은 모든 path 는
 * UsernamePasswordAuthenticationFilter 에서 접근 권한을 체크한다.
 * 이 프로젝트는 JWT 토큰으로 접근 권한을 허가하기 때문에,
 * UsernamePasswordAuthenticationFilter 전에 동작할 수 있는 JWT 전용 filter 를 Custom 한다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * doFilterInternal - Method ㅣ
     * 매 요청마다 실행되는 Jwt Filter 메서드이다.
     * JWT 토큰으로 접근 권한을 허가하기 위해 이 메서드를 오버라이딩한다.
     * UserServiceDetails 의 메서드인 loadUserByUsername 를 사용하여,
     * UserDetails 인스턴스를 받아와 Authentication 을 구현한 UsernamePasswordAuthenticationToken 객체를 생성한다.
     * 이를 SecurityContextHolder 의 context 를 꺼내와 UsernamePasswordAuthenticationToken 객체를 인증 컨텍스트에 저장한다.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain FilterChain
     * @throws ServletException 서블릿 오류
     * @throws IOException 입출력 오류
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.debug("[JwtAuthenticationFilter] - doFilterInternal");

        String accessToken = extractToken(request.getHeader("Authorization"));

        try {
            if (accessToken != null && jwtUtils.validateJwtToken(accessToken)) {

                String userId = jwtUtils.getUserId(accessToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {

            logger.error("유저의 인증 정보를 설정할 수 없습니다: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * extractToken - Method ㅣ
     * "Bearer "로 붙어서 오는 accessToken 을 추출하는 메서드이다.
     * @param token 사용자가 요청한 토큰
     * @return 추출한 accessToken 을 반환한다.
     */
    private String extractToken(String token) {

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }
}
