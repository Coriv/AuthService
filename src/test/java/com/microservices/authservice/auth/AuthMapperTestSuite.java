package com.microservices.authservice.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthMapperTestSuite {
    @Autowired
    private AuthMapper authMapper;
    @Mock
    private AuthDao authDao;

    @Test
    void toAuthForNewUser() {
        // Given
        AuthenticationDto authDto = AuthenticationDto.builder()
                .id(1L)
                .userId(3L)
                .username("username")
                .password("password")
                .build();

        when(authDao.findByUsername(anyString())).thenReturn(Optional.empty());

        // When
        Authentication auth = authMapper.toAuth(authDto);

        // Then
        assertEquals(authDto.getUsername(), auth.getUsername());
        assertEquals(authDto.getPassword(), auth.getPassword());
        assertEquals(authDto.getUserId(), auth.getUserId());
        assertEquals(true, auth.isActive());
        assertEquals(Role.USER, auth.getAuthority());
    }

    @Test
    void toAuthForExistingUser() {
        // Given
        AuthenticationDto authDto = AuthenticationDto.builder()
                .id(1L)
                .userId(3L)
                .username("username")
                .password("password")
                .build();

        Authentication existingAuth = new Authentication();
        existingAuth.setUsername("username");
        existingAuth.setPassword("oldpassword");
        existingAuth.setUserId(1L);

        when(authDao.findByUsername(anyString())).thenReturn(Optional.of(existingAuth));

        // When
        Authentication auth = authMapper.toAuth(authDto);

        // Then
        assertEquals(authDto.getUsername(), auth.getUsername());
        assertEquals(authDto.getPassword(), auth.getPassword());
        assertEquals(authDto.getUserId(), auth.getUserId());
        assertEquals(true, auth.isActive());
        assertEquals(Role.USER, auth.getAuthority());
    }
}
