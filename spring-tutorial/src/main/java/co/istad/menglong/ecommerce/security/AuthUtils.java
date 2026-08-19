package co.istad.menglong.ecommerce.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class AuthUtils {
    private AuthUtils() {
    }

    public static String extractUserId() {
        Authentication auth = getAuth();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have been forbidden");
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) auth;

        return jwtAuthenticationToken.getToken().getSubject();
    }

    public static String extractJwt() {
        return ((Jwt) Objects.requireNonNull(getAuth().getPrincipal())).getTokenValue();
    }

    public static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
