package com.microservices.authservice.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final AuthDao authDao;

    public Authentication toAuth(AuthenticationDto authenticationDto) {
        var auth = authDao.findByUsername(authenticationDto.getUsername())
                .orElse(new Authentication());
        auth.setUsername(authenticationDto.getUsername());
        auth.setPassword(authenticationDto.getPassword());
        auth.setUserId(authenticationDto.getUserId());
        auth.setActive(true);
        auth.setAuthority(Role.USER);
        return auth;
    }
}
