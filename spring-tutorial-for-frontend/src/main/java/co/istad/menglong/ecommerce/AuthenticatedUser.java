package co.istad.menglong.ecommerce;

import lombok.Builder;

@Builder
public record AuthenticatedUser(
        String username,
        Boolean isAuthenticated
) {
}
