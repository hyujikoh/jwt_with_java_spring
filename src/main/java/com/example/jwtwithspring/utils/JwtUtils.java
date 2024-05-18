package com.example.jwtwithspring.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        String jwt = builder.header()
                .keyId("key id") // 옵션, 해당 JWT의 서명하는데 키의 식별자 역할로 사용가능
                .type("jwt") // 토큰의 타입에 대해 명시
                .contentType("해석본 명시")// 옵션, JWT 가 중첩되어있는 경우 내부 JWT 를 어떻게 해석해야하는지에 대해 명시, 보통은 사용할 필요없음
                .critical() // 무조건적으로 검증해야하는 필드, 없을 경우 아예 검증조차 못하게 수행 가능, 여러개 추가 가능
                    .add("exp")
                .and()
                .and()
                .compact();
        return jwt;
    }
}
