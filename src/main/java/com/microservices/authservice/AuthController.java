package com.microservices.authservice;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        var user = authService.saveUser(userDto);
        rabbitTemplate.convertAndSend("email.exchange", "email", userDto.getEmail());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getToken(@RequestBody UserDto userDto) {
        var token = authService.authenticate(userDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> tokenValidate(@RequestParam("token") String token) {
        authService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/safe")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("W srodku");
    }
}
