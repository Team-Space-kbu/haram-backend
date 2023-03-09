package com.space.haramspring.jwt;

import com.space.haramspring.user.dto.UserJwtDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${space.biblemon.secret}")
    String SECRET_KEY;

    // 1초 * 60 -> 1분 * 30
    // 즉, 30분
    public final static long TOKEN_VALIDATION_SECOND = (1000L * 60) * 30;

    // 1초 * 60 -> 1분 * 60 -> 1시간 * 24 -> 하루 * 7
    // 즉, 7일
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = (((1000L * 60) * 60) * 24) * 7;

    /**
     * getSigningKey - Method ㅣ
     * 받아온 secretKey 를 byte 로 변경한다.
     * byte 로 변경된 키를 통해 hmacShaKeyFor 메서드를 이용하여 SecretKey 인스턴스를 받아온다.
     * 받아온 SecretKey 인스턴스는 지정된 키 바이트 배열을 기반으로 하는 HMAC-SHA 알고리즘과 함께 사용한다.
     * @param secretKey 를 받아온다.
     * @return hmacShaKeyFor 메서드를 통해 받아온 SecretKey 인스턴스를 반환한다.
     */
    private Key getSigningKey(String secretKey) {

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * getUserId - Method ㅣ
     * extractAllClaims 메서드를 통해 받아온 Payload 에서 사용자의 id를 가져온다.
     * @param token 사용자가 요청함과 같이 보낸 token 을 받아온다.
     * @return 사용자의 id를 반환한다.
     */
    public String getUserId(String token) {

        return extractAllClaims(token).get("userId", String.class);
    }

    /**
     * isTokenExpired - Method ㅣ
     * @param token 사용자가 요청함과 같이 보낸 token 을 받아온다.
     * @return true or false 값을 반환한다.
     * 현재 시간이 추출한 유효시간 보다 크다면 true 를 반환하고,
     * 현재 시간이 추출한 유효시간 보다 작다면 false 를 반환한다.
     */
    public Boolean isTokenExpired(String token) {

        final Date expiration = extractAllClaims(token).getExpiration();

        return expiration.before(new Date());
    }

    /**
     * extractAllClaims - Method ㅣ
     * 사용자에게 받아온 토큰을 SECRET_KEY 를 통해 인증하여 Body 내용을 Claims 타입으로 반환한다.
     * @param token 사용자가 요청할 때 같이 보낸 token 을 받아온다.
     * @return Jwt 의 Body 내용을 반환한다(Payload).
     * @throws ExpiredJwtException
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException {

        return Jwts.parser()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * generateToken - Method ㅣ
     * UserJwtDto 객체를 통해 사용자의 아이디를 불러와
     * doGenerateToken 메서드를 호출한다.
     * @param userJwtDto - Jwt 를 만들기 위한 정보가 들어 있는 DTO 이다.
     * @return 생성한 토큰을 반환한다.
     */
    public String generateToken(UserJwtDto userJwtDto) {

        return doGenerateToken(userJwtDto.getUserId(), TOKEN_VALIDATION_SECOND);
    }

    /**
     * generateRefreshToken - Method ㅣ
     * UserJwtDto 객체를 통해 사용자의 아이디를 불러와
     * doGenerateToken 메서드를 호출한다.
     * @param userJwtDto UserJwtDto 객체
     * @return 생성한 토큰을 반환한다.
     */
    public String generateRefreshToken(UserJwtDto userJwtDto) {

        return doGenerateToken(userJwtDto.getUserId(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    /**
     * doGenerateToken - Method ㅣ
     * generateToken 에서 유저 아이디와 유효 시간을 받아온다.
     * claims 단위에 유저 아이디, 현재 시간, 현재시간 + 유효시간, 대칭 키와 해쉬 알고리즘을 통한 암호화 를 진행한다.
     * 즉, jwt 의 Payload 에는 사용자 아이디(Claims), 유효 기간이 들어간다.
     * accessToken 과 refreshToken 은 해당 doGenerateToken 을 통해 jwt 를 생성한다.
     * @param userId 유저 아이디이다.
     * @param expireTime 토큰이 유효한 시간의 범위이다.
     * @return String 타입의 Jwt 를 반환한다.
     */
    public String doGenerateToken(String userId, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("userId", userId); // 비공개 클레임

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT") // header -> typ: jwt
                .setIssuer("team-space") // iss - 보낸 사람
                .setSubject("AuthenticationToken") // sub - 주제
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // iat
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)) // exp
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    /**
     * validateJwtToken - Method ㅣ
     * JwtUtils 에서 가장 핵심 메서드이다. 먼저, 토큰을 받아온다.
     * id 값이 존재하는지 & 토큰의 사용 기간이 유효한지 판단 후 boolean 값을 반환해준다.
     * 내부에서 사용되는 getUserId, isTokenExpired 메서드는 해당 클래스에 선언되어 있다.
     * UserDetails 의 getUsername 은 보통 PK 값을 받아온다.
     * 따라서, userId 는 Long 타입의 userSeqNum 이 될 수 있다.
     * 하지만, 이렇게 되면 DB에 직접 접근할 수 있으므로 id 값을 담는게 좋다.
     * @param token 사용자가 요청할 때 함께 보낸 토큰을 받아온다.
     * @return 토큰과 유저의 정보가 존재하고, 유효시간을 지나지 않았다면 true 를 반환한다.
     */
    public boolean validateJwtToken(String token) {
        try {

            String userId = getUserId(token);
            return userId != null & !isTokenExpired(token);

        } catch (SignatureException e) {
            log.error("JWT 의 기존 서명을 확인하지 못했습니다: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT 가 올바르게 구성되지 않았습니다: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT 를 생성할 때 지정한 유효기간이 초과되었습니다: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("예상하는 형식과 다른 형식입니다: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("적합하지 않거나 적절하지 못한 인자를 메소드에 넘겨주었습니다: {}", e.getMessage());
        }
        return false;
    }
}
