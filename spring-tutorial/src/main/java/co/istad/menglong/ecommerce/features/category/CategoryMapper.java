package co.istad.menglong.ecommerce.features.category;

import co.istad.menglong.ecommerce.features.category.dto.CategoryRequest;
import co.istad.menglong.ecommerce.features.category.dto.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category mapCategoryRequestToCategory(CategoryRequest categoryRequest);

    CategoryResponse mapCategoryToCategoryResponse(Category category);

    void updateCategoryFromRequest(@MappingTarget Category category, CategoryRequest categoryRequest);
}
