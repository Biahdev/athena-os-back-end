package dev.abeatriz.athena_os.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtCookieFilter extends OncePerRequestFilter {

    @Value("${jwt.cookie.name}")
    private String cookieName;

    private final ApplicationContext applicationContext;

    public JwtCookieFilter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().contains("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Optional<String> jwt = extractJwtFromCookie(request);

            if (jwt.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            JwtDecoder jwtDecoder = applicationContext.getBean(JwtDecoder.class);
            Jwt decodedJwt = jwtDecoder.decode(jwt.get());

            var roles = decodedJwt.getClaimAsStringList("userRole");

            var authentication = new JwtAuthenticationToken(
                    decodedJwt,
                    roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado");
        }
    }

    private Optional<String> extractJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            System.out.println("Nenhum cookie encontrado na requisição"); // Adicionar ao log
            return Optional.empty();
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue);
    }
}