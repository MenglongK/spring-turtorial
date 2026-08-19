package co.istad.menglong.ecommerce.features.order.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record OrderLineDto(
        @NotBlank(message = "Product is required")
        @Size(max = 255)
        String productCode,
        @Positive
        Integer qty,
        @Min(0)
        @Max(100)
        Float discount
) {
}
