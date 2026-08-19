package co.istad.menglong.ecommerce.features.auth;

import co.istad.menglong.ecommerce.features.auth.dto.LoginRequest;
import co.istad.menglong.ecommerce.features.auth.dto.LoginResponse;
import co.istad.menglong.ecommerce.features.auth.dto.RegisterRequest;
import co.istad.menglong.ecommerce.features.auth.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
