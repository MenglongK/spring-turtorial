package co.istad.menglong.ecommerce.features.auth.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        String keycloakUserId,
        String username,
        String email,
        String firstName,
        String lastName,
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
}
