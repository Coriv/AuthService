package com.microservices.authservice;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
