package co.istad.menglong.ecommerce.features.category;

import co.istad.menglong.ecommerce.features.category.dto.CategoryRequest;
import co.istad.menglong.ecommerce.features.category.dto.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createNew(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        log.debug("create new category: {}", categoryRequest);
        return categoryService.createNew(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse findById(@PathVariable Integer categoryId) {
        return categoryService.findById(categoryId);
    }

    @GetMapping
    public Page<CategoryResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize
    ) {
        return categoryService.findAll(pageNumber, pageSize);
    }

    @PutMapping("/{code}")
    public CategoryResponse update(
            @PathVariable Integer code,
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        return categoryService.update(code, categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public CategoryResponse delete(@PathVariable Integer categoryId) {
        return categoryService.delete(categoryId);
    }
}