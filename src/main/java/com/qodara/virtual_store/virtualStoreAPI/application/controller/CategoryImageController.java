package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryImageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.CategoryImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "CategoryImage", description = "Category Image Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/category-images")
public class CategoryImageController {
    private final CategoryImageService categoryImageService;
    public CategoryImageController(CategoryImageService categoryImageService) {
        this.categoryImageService = categoryImageService;
    }
    @SecurityRequirements
    @Operation(summary = "Get category image by category id")
    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<CategoryImageResponseDTO>> getCategoryImageByCategoryId(@PathVariable int id) {
        var res = categoryImageService.getCategoryImageByCategoryId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @SecurityRequirements
    @Operation(summary = "Get all category images")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryImageResponseDTO>>> getAllCategoryImages() {
        var res = categoryImageService.getAllCategoryImages();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create category image")
    @PostMapping(value = "/category-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryImageResponseDTO>> saveCategoryImage(@RequestPart("file") MultipartFile file,
                                                                                   @RequestParam("name") String name,
                                                                                   @RequestParam("categoryId") int categoryId) {
        try {
            var res = categoryImageService.saveCategoryImage(file, name, categoryId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            var res = new ApiResponse<CategoryImageResponseDTO>("Error saving category image: " + e.getMessage(), com.qodara.virtual_store.shared.model.enums.Estatus.ERROR, null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update category image")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryImageResponseDTO>> updateCategoryImage(@PathVariable int id,
                                                                                     @RequestPart("file") MultipartFile file,
                                                                                     @RequestParam("name") String name,
                                                                                     @RequestParam("categoryId") int categoryId) {
        try {
            var res = categoryImageService.updateCategoryImage(id, file, name, categoryId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            var res = new ApiResponse<CategoryImageResponseDTO>("Error updating category image: " + e.getMessage(), com.qodara.virtual_store.shared.model.enums.Estatus.ERROR, null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update only category image file")
    @PutMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryImageResponseDTO>> updateOnlyCategoryImage(@PathVariable int id, MultipartFile file) {
        try {
            var res = categoryImageService.updateOnlyCategoryImage(id, file);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            var res = new ApiResponse<CategoryImageResponseDTO>("Error updating category image file: " + e.getMessage(), com.qodara.virtual_store.shared.model.enums.Estatus.ERROR, null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update category image data")
    @PutMapping("/{id}/data")
    public ResponseEntity<ApiResponse<CategoryImageResponseDTO>> updateCategoryImageData(@PathVariable int id, String name, int categoryId) {
        var res = categoryImageService.updateCategoryImageData(id, name, categoryId);
        return new ResponseEntity<>(res, res.getStatus() == com.qodara.virtual_store.shared.model.enums.Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete category image")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryImage(@PathVariable int id) {
        var res = categoryImageService.deleteCategoryImage(id);
        return new ResponseEntity<>(res, res.getStatus() == com.qodara.virtual_store.shared.model.enums.Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete category image by category id")
    @DeleteMapping("/delete/category/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryImageByCategoryId(@PathVariable int categoryId) {
        var res = categoryImageService.deleteCategoryImageByCategoryId(categoryId);
        return new ResponseEntity<>(res, res.getStatus() == com.qodara.virtual_store.shared.model.enums.Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
