package com.microservices.authservice.auth;

import com.microservices.authservice.exception.AuthIdNotFoundException;
import com.microservices.authservice.jwtAuthoritation.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthDao authDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Authentication saveAuth(Authentication auth) {
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));
        return authDao.save(auth);
    }

    public String authenticate(AuthenticationDto authenticationDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getUsername(),
                        authenticationDto.getPassword()
                )
        );
        var user = authDao.findByUsername(authenticationDto.getUsername())
                .orElseThrow();
        return jwtService.generateToken(user);
    }

    public void validateToken(String token) {
        jwtService.isTokenValid(token);
    }

    public void blockUser(Long userId) throws AuthIdNotFoundException {
        var user = authDao.findByUserId(userId).orElseThrow(AuthIdNotFoundException::new);
        user.setActive(false);
        authDao.save(user);
    }

    public void unblockUser(Long userId) throws AuthIdNotFoundException {
        var user = authDao.findByUserId(userId).orElseThrow(AuthIdNotFoundException::new);
        user.setActive(false);
        authDao.save(user);
    }
}
