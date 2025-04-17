package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.auth.LoginRequest;
import dev.abeatriz.athena_os.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class AuthService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.cookie.name}")
    private String cookieName;

    @Value("${jwt.cookie.expiration}")
    private Long cookieExpiration;

    public AuthService(JwtEncoder jwtEncoder,
                       UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void login(LoginRequest loginRequest, HttpServletResponse response) {
        var user = userRepository.findByEmail(loginRequest.email());
        System.out.println(user);

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = this.cookieExpiration;
        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(this.cookieExpiration))
                .claim("userRole", "ROLE_" + user.get().getRole())
                .claim("email", user.get().getEmail())
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        Cookie jwtCookie = new Cookie(cookieName, jwtValue);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(Math.toIntExact(this.cookieExpiration));

        response.addCookie(jwtCookie);
        response.setHeader("Set-Cookie", jwtCookie.getName() + "=" + jwtCookie.getValue() +
                "; Path=" + jwtCookie.getPath() +
                "; Max-Age=" + jwtCookie.getMaxAge() +
                "; HttpOnly; SameSite=Lax");
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
