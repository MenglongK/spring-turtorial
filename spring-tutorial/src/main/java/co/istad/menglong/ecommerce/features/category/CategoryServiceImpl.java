package co.istad.menglong.ecommerce.features.category;

import co.istad.menglong.ecommerce.features.category.dto.CategoryRequest;
import co.istad.menglong.ecommerce.features.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> findAll(int pageNumber, int pageSize) {
        // TODO: Implement this method
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::mapCategoryToCategoryResponse);
    }

    @Override
    public CategoryResponse findById(Integer categoryId) {
        // TODO: Implement this method
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse createNew(CategoryRequest categoryRequest) {
        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest);
        category = categoryRepository.save(category);
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse update(Integer code, CategoryRequest categoryRequest) {
        // TODO: Implement this method
        Category category = categoryRepository.findById(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryMapper.updateCategoryFromRequest(category, categoryRequest);
        category = categoryRepository.save(category);
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse delete(Integer categoryId) {
        // TODO: Implement this method
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }
}