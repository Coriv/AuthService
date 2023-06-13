package com.microservices.authservice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User saveUser(UserDto userDto) {
        var user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return authRepository.save(user);
    }

    public String authenticate(UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        var user = authRepository.findByUsername(userDto.getEmail())
                .orElseThrow();
        return jwtService.generateToken(user);
    }

    public void validateToken(String token) {
        jwtService.isTokenValid(token);
    }
}
