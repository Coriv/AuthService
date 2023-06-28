package com.microservices.authservice.auth;

import com.microservices.authservice.exception.AuthIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthenticationDto authDto) {
        var auth = authMapper.toAuth(authDto);
        authService.saveAuth(auth);
        return ResponseEntity.ok(authService.authenticate(authDto));
    }

    @GetMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getToken(@RequestBody AuthenticationDto authDto) {
        var token = authService.authenticate(authDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> tokenValidate(@RequestParam("token") String token) {
        authService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block")
    public ResponseEntity<Void> blockUser(@RequestParam Long userId) throws AuthIdNotFoundException {
        authService.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/unblock")
    public ResponseEntity<Void> unblockUser(@RequestParam Long userId) throws AuthIdNotFoundException {
        authService.unblockUser(userId);
        return ResponseEntity.ok().build();
    }
}
