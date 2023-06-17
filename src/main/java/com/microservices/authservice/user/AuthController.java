package com.microservices.authservice.user;

import com.microservices.authservice.exception.UserIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthenticationDto authenticationDto) {
        var user = authService.saveAuth(authenticationDto);
        return ResponseEntity.ok(authService.authenticate(authenticationDto));
        // todo fix method and controller (inject mapper to controller)
    }

    @GetMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getToken(@RequestBody AuthenticationDto authenticationDto) {
        var token = authService.authenticate(authenticationDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> tokenValidate(@RequestParam("token") String token) {
        authService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block")
    public ResponseEntity<Void> blockUser(@RequestParam Long userId) throws UserIdNotFoundException {
        authService.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/unblock")
    public ResponseEntity<Void> unblockUser(@RequestParam Long userId) throws UserIdNotFoundException {
        authService.unblockUser(userId);
        return ResponseEntity.ok().build();
    }
}
