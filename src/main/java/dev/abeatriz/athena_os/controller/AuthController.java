package dev.abeatriz.athena_os.controller;


import dev.abeatriz.athena_os.dto.auth.LoginRequest;
import dev.abeatriz.athena_os.dto.auth.LoginResponse;
import dev.abeatriz.athena_os.service.AuthService;
import dev.abeatriz.athena_os.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        authService.login(loginRequest, response);
        return ResponseEntity.ok(new LoginResponse("Login bem-sucedido"));
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok(new LoginResponse("Logout bem-sucedido"));
    }


}
