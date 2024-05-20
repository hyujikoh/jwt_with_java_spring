package com.example.jwtwithspring.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Author : hyujikoh
 * CreatedAt : 2024-05-18
 * Desc : JwtUtils 클래스
 */
@Component
public class JwtUtils {

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
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60))) // 토큰의 만료시간 현재는 테스트이기 때문에 1초 로 설정
                .notBefore(new Date(System.currentTimeMillis() + (1000))) // 해당 시간 이전에는 토큰이 처리되서는 안되는걸 설정하기 위한 명시, 역시 필요가 없으면 사용 안해도 무방
                .issuedAt(new Date()) // 토큰이 발행한 시각 명시
                .id(UUID.randomUUID().toString()) // JWT id 명시 역시 필수는 아니다.
                // 여기까지 기본 클레임을 설정하고 이후부터 커스텀 클레임을 지정하는 구간이다.
                .add("custom-claim-key1","custom-claim-value-1") // 커스텀 클레임을 적용이 가능하다. 운영자가 사용자 토큰을 보다 명시적으로 구분하기 위해 사용해도 좋다.
                .add("custom-claim-key2","custom-claim-value-2")
                .and();
        return builder.compact();
    }
}
