package com.microservices.authservice.auth;

import com.microservices.authservice.exception.UserIdNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTestSuite {
    @InjectMocks
    private AuthService authService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AuthDao authDao;
    private Authentication auth;

    @BeforeEach
    void setUp() {
        auth = new Authentication();
        auth.setUsername("username");
        auth.setPassword("password");
        auth.setActive(true);
        auth.setAuthority(Role.USER);
        auth.setUserId(1L);
        auth.setId(5L);
    }

    @Test
    void saveAuthTest() {
        //Given
        when(authDao.save(any(Authentication.class))).thenReturn(auth);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        //when
        var savedAuth = authService.saveAuth(auth);
        //then
        assertEquals(auth, savedAuth);
        assertEquals(auth.getPassword(), "encodedPassword");
        verify(authDao, times(1)).save(eq(auth));
    }

    @Test
    void blockUserTest() throws UserIdNotFoundException {
        //given
        when(authDao.findByUserId(anyLong())).thenReturn(Optional.of(auth));
        when(authDao.save(any())).thenReturn(auth);
        //when
        authService.blockUser(auth.getId());
        //then
        assertFalse(auth.isActive());
    }

    @Test
    void unblockUser() throws UserIdNotFoundException {
        //Given
        auth.setActive(false);
        when(authDao.findByUserId(anyLong())).thenReturn(Optional.of(auth));
        //when
        authService.unblockUser(auth.getId());
        //then
        assertFalse(auth.isActive());
    }

    @Test
    void giveAdminPrivileges() throws UserIdNotFoundException {
        //given
        when(authDao.findByUserId(anyLong())).thenReturn(Optional.of(auth));
        //when
        authService.giveAdminPrivileges(auth.getId());
        //then
        assertEquals(auth.getAuthorities(), Arrays.asList(new SimpleGrantedAuthority(Role.ADMIN.name())));
    }
}