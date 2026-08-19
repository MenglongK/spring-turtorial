package co.istad.menglong.ecommerce.features.category;

import co.istad.menglong.ecommerce.features.category.dto.CategoryRequest;
import co.istad.menglong.ecommerce.features.category.dto.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Page<CategoryResponse> findAll(int pageNumber, int pageSize);

    CategoryResponse findById(Integer categoryId);

    CategoryResponse createNew(CategoryRequest categoryRequest);

    CategoryResponse update(Integer code, CategoryRequest categoryRequest);

    CategoryResponse delete(Integer categoryId);
}
