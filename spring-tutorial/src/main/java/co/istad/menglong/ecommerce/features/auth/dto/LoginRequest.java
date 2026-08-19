package co.istad.menglong.ecommerce.features.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 255)
        String username,
        @NotBlank(message = "Password is required")
        @Size(max = 255)
        String password
) {
}
