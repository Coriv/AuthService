package com.microservices.authservice.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationDto {

    private Integer id;
    private final Long userId;
    private String username;
    private String password;
}
