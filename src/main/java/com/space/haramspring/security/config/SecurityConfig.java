package com.space.haramspring.security.config;

import com.space.haramspring.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig - Class(Config) ㅣ
 * SpringSecurity 에 필요한 빈 등록(return JwtAuthenticationFilter, return PasswordEncoder,
 * return AuthenticationManager, return DaoAuthenticationProvider, return SecurityFilterChain),
 * , 의존성 주입 (AuthenticationEntryPoint <- JwtAuthenticationEntryPoint, UserDetailsService <- UserDetailsServiceImpl
 * , JwtAuthenticationFilter)
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * securityFilterChain - Bean(FilterChain) ㅣ
     * @param http HttpSecurity 를 받아와 Spring Security 에 대한 보안 설정을 한다.
     *             security 는 session 을 사용하기 때문에, Jwt 를 사용하기 위해서는 session 정책을 무상태로 설정하고,
     *             authenticationProvider 에 AuthenticationProvider 의 구현체인
     *             DaoAuthenticationProvider 에 custom 한 UserDetailsServiceImpl 을 설정한다.
     *             마지막으로 UsernamePasswordAuthenticationFilter 가 동작하기 전에, JWT 토큰으로 인증하기 위한
     *             JwtAuthenticationFilter 를 먼저 지나도록 설정한다.
     * @return 위에서 설정한 http 를 build 하여 반환한다.
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v1/signup", "/v1/login").permitAll()
                        .requestMatchers("/v1/users", "/v1/test").hasRole("USER")
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtAuthenticationFilter
                , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * getPasswordEncoder - Bean(Encoder) ㅣ
     * 패스워드를 인코딩 시키기 위해 등록하는 Bean 이다.
     * @return BCryptPasswordEncoder 의 인스턴스를 반환한다.
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {

        log.debug("Bean - password[getPasswordEncoder]");

        return new BCryptPasswordEncoder();
    }

//    /**
//     * authenticationManager - Bean(AuthenticationManager) ㅣ
//     * Authentication 객체에 접근하여 유효한지를 확인한다. 사용되는 구현체는 ProviderManager 이고,
//     * ProviderManager 는 AuthenticationProvider 의 구현체 중, 하나를 골라 인증에 사용한다.
//     * @param authConfig
//     * @return 처리할 수 있는 AuthenticationManager 를 반환한다.
//     * @throws Exception
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//
//        log.debug("Bean - authenticationManager");
//
//        return authConfig.getAuthenticationManager();
//    }

    /**
     * authenticationProvider - Bean(AuthenticationProvider) ㅣ
     * AuthenticationProvider 의 구현체 중 하나인 DaoAuthenticationProvider 를 빈으로 등록한다.
     * 이때, 인증에 사용될 UserDetailsService 구현체와, 패스워드 인코딩할 때 사용할 함수를 설정한 후, 반환한다.
     * @return UserDetailsService, PasswordEncoder 설정이 완료된 DaoAuthenticationProvider 의 인스턴스를 반환한다.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        log.debug("Bean - authenticationProvider");

        return authProvider;
    }
}
