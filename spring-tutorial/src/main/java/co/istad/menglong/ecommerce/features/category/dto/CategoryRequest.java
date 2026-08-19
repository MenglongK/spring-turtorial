package co.istad.menglong.ecommerce.features.category.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryRequest(
        @NotBlank(message = "Name is required")
        @Length(min = 2, max = 50)
        String name,
        String description,
        String icon
) {
}
