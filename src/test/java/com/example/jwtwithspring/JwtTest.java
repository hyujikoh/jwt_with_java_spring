package com.example.jwtwithspring;

import com.example.jwtwithspring.utils.JwtUtils;
import jdk.jfr.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author : hyujikoh
 * CreatedAt : 2024-05-18
 * Desc :
 */
@SpringBootTest
public class JwtTest {
    @Autowired
    JwtUtils jwtUtils;

    @Test
    @DisplayName("jwt 토큰을 생성하는 테스트 코드 입니다.")
    public void createJwt(){
        String jwt = this.jwtUtils.createJwt();

        Assertions.assertNotEquals(jwt,null);
    }

    @Test
    @DisplayName("토큰을 읽는 테스트 코드 입니다.")
    public void readJwt(){
        String jwt = this.jwtUtils.createJwt();

        this.jwtUtils.isValidJwt(jwt);
    }
}
