package com.microservices.authservice.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthDaoTestSuite {

    @Autowired
    private AuthDao authDao;

    @Test
    void saveAuthentication() {
        //given
        Authentication auth = new Authentication();
        auth.setUsername("username");
        auth.setPassword("password");
        auth.setAuthority(Role.ADMIN);
        auth.setActive(true);
        auth.setUserId(1L);
        //When
        var savedAuth = authDao.save(auth);
        //Then
        assertEquals(auth.getId(), savedAuth.getId());
        assertEquals(auth.getUsername(), savedAuth.getUsername());
        assertEquals(auth.getPassword(), savedAuth.getPassword());
        assertEquals(auth.getUserId(), savedAuth.getUserId());
        assertEquals(auth.isActive(), savedAuth.isActive());
        //cleanUp
        authDao.deleteById(savedAuth.getId());
    }
}
