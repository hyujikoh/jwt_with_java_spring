package com.example.jwtwithspring.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

/**
 * Author : hyujikoh
 * CreatedAt : 2024-05-18
 * Desc : JwtUtils 클래스
 */
@Component
public class JwtUtils {

    // sha 256 시크릿 키
    private static final String SECRET_KEY_32 = "RBWXHJlYXtmfL5j4+ObYL3L20wns5e/h4uYvT45UxPI=";

    // sha 512 시크릿 키
    private static final String SECRET_KEY_64 = "SntJsbqFMtFSC0GFXRpOb9OZR64V0Ztv/qRexuZkh4Dpp3TExTLVMBsu4WXkjZQb5UFdo9SL73z5ebYYmisb4w==";

    public String createJwt() {
        // 보다 명시적으로 분리하기 위해 builder 선언
        JwtBuilder builder = Jwts.builder();

        //header 부분,  alg, enc or zip 은 헤더에 추가 안해도 자동으로 생성된다.
        builder.header()
                .keyId("key id") // 옵션, 해당 JWT의 서명하는데 키의 식별자 역할로 사용가능
                .type("jwt") // 토큰의 타입에 대해 명시
                .add("custom-key","custom-value") // 커스텀하게 header 값을 부여할 수 있다. 역시 필요할때 말고는 사용안해도 된다. 또한 Map 형태로 필드 값 부여도 가능하다.
                .contentType("해석본 명시")// 옵션, JWT 가 중첩되어있는 경우 내부 JWT 를 어떻게 해석해야하는지에 대해 명시, 보통은 사용할 필요없음
                .critical() // 무조건적으로 검증해야하는 필드, 없을 경우 아예 검증조차 못하게 수행 가능, 여러개 추가 가능
                    .add("exp")
                    .add("sub")
                .and()
                .and();

        // payload 에 대한 내용
        builder.claims()
                // 여기 부터 기본 클레임을 설정하는 구간이다.
                .issuer("hyujikoh") // 토큰을 발행하는 발행자에 대해 명시,
                .subject("jwt Project ver 1") // 토큰의 주제에 대해 명시
                .audience()// 토큰에 수신자를 명시, 여러 정보들을 넣을수 있다.
                    .add("수신자 1")
                    .add("수신자 2")
                    .and()
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60))) // 토큰의 만료시간 현재는 테스트이기 때문에 1분 로 설정
                .notBefore(new Date(System.currentTimeMillis() + (1000)))
                .issuedAt(new Date()) // 토큰이 발행한 시각 명시
                .id(UUID.randomUUID().toString()) // JWT id 명시 역시 필수는 아니다.
                // 여기까지 기본 클레임을 설정하고 이후부터 커스텀 클레임을 지정하는 구간이다.
                .add("custom-claim-key1","custom-claim-value-1") // 커스텀 클레임을 적용이 가능하다. 운영자가 사용자 토큰을 보다 명시적으로 구분하기 위해 사용해도 좋다.
                .add("custom-claim-key2","custom-claim-value-2")
                .and();

        // 서명에 대한 내용
        builder.signWith(getSigningKey());
        return builder.compact();
    }

    /**
     * 시크릿 키 생성 메서드
     * @return
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY_64);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * 토큰값 파싱 테스트
     * @param token
     * @return
     */
    public boolean isValidJwt(String token) {
        if (token == null || token.isBlank() || token.isEmpty()) {
            throw new NullPointerException();
        }
        String jwt = token.replaceAll("^Bearer( )*", "");
        try {
            Jwt<?, ?> parse = Jwts.parser()
                    .setSigningKey(SECRET_KEY_64)
                    .build()
                    .parse(jwt);
        } catch (Exception e) {
            throw e;
        }

        return true;
    }
}
