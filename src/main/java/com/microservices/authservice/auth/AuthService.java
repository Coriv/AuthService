package com.microservices.authservice.auth;

import com.microservices.authservice.exception.UserIdNotFoundException;
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
        var auth = authDao.findByUsername(authenticationDto.getUsername())
                .orElseThrow();
        return jwtService.generateToken(auth);
    }

    public void validateToken(String token) {
        jwtService.isTokenValid(token);
    }

    public void blockUser(Long userId) throws UserIdNotFoundException {
        var auth = authDao.findByUserId(userId).orElseThrow(UserIdNotFoundException::new);
        auth.setActive(false);
        authDao.save(auth);
    }

    public void unblockUser(Long userId) throws UserIdNotFoundException {
        var auth = authDao.findByUserId(userId).orElseThrow(UserIdNotFoundException::new);
        auth.setActive(false);
        authDao.save(auth);
    }

    public void giveAdminPrivileges(Long userId) throws UserIdNotFoundException {
        var auth = authDao.findByUserId(userId).orElseThrow(UserIdNotFoundException::new);
        auth.setAuthority(Role.ADMIN);
        authDao.save(auth);
    }
}
