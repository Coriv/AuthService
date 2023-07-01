package com.microservices.authservice.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AuthenticationDto {

    private Long id;
    private Long userId;
    private String username;
    private String password;
}
