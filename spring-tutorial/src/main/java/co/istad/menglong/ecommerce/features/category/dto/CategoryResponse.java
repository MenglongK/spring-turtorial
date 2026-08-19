package co.istad.menglong.ecommerce.features.category.dto;

public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String icon
) {
}
