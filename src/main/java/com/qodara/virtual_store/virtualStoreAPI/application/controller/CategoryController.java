package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.CategoryRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.BrandResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.PageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Category Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable int id) {
        ApiResponse<CategoryResponseDTO> response = categoryService.getCategoryById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get all categories")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAllBrands() {
        var res = categoryService.getAllCategories();
        return new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create  category")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createBrand(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        var res = categoryService.createCategory(categoryRequestDTO);
        return  new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Update category")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateBrand(@PathVariable int id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        var res = categoryService.updateCategory(id, categoryRequestDTO);
        return new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Delete Category")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable int id) {
        var res = categoryService.deleteCategory(id);
        return new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @SecurityRequirements
    @Operation(summary = "Get all categories paged")
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PageResponseDTO<CategoryResponseDTO>>> getAllProductsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String q
    ) {
        var res = categoryService.getCategoriesPaged(page, size, sortBy, sortDir, q);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
