package co.istad.menglong.ecommerce.features.auth;

import co.istad.menglong.ecommerce.features.auth.dto.LoginRequest;
import co.istad.menglong.ecommerce.features.auth.dto.LoginResponse;
import co.istad.menglong.ecommerce.features.auth.dto.RegisterRequest;
import co.istad.menglong.ecommerce.features.auth.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public RegisterResponse register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        return authService.register(registerRequest);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }
}
