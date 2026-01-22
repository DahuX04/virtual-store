package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductImageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "ProductImage", description = "Product Image Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/product-images")
public class ProductImageController {
    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Operation(summary = "Get all product images by product id")
    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<List<ProductImageResponseDTO>>> getProductImagesByProductId(int id) {
        var res = productImageService.getProductImagesByProductId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get all product images")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductImageResponseDTO>>> getAllProductImages() {
        var res = productImageService.getAllProductImages();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get product image by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductImageResponseDTO>> getProductImageById(int id) {
        ApiResponse<ProductImageResponseDTO> response = productImageService.getProductImageById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create product image")
    @PostMapping(value = "/product-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductImageResponseDTO>> saveProductImage(MultipartFile file, String name, int product_id) {
        try {
            var res = productImageService.saveProductImage(file, name, product_id);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            var res = new ApiResponse<ProductImageResponseDTO>("Error saving product image: " + e.getMessage(), Estatus.ERROR, null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update product image")
    @PutMapping(value = "/product-image/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductImageResponseDTO>> updateProductImage(int id, MultipartFile file, String name, int product_id) {
        try {
            var res = productImageService.updateProductImage(id, file, name, product_id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            var res = new ApiResponse<ProductImageResponseDTO>("Error updating product image: " + e.getMessage(), Estatus.ERROR, null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update only product image file")
    @PutMapping(value = "/product-image/update/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductImageResponseDTO>> updateOnlyProductImage(int id, MultipartFile file) {
        try {
            var res = productImageService.updateOnlyProductImage(id, file);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            var res = new ApiResponse<ProductImageResponseDTO>("Error updating product image file: " + e.getMessage(), Estatus.ERROR, null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update product image data")
    @PutMapping("/product-image/update/data")
    public ResponseEntity<ApiResponse<ProductImageResponseDTO>> updateProductImageData(int id, String name, int product_id) {
        var res = productImageService.updateProductImageData(id, name, product_id);
        return new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete product image by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductImage(@PathVariable int id) {
        var res = productImageService.deleteProductImage(id);
        return new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete all product images by product id")
    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAllProductImagesByProductId(@PathVariable int id) {
        var res = productImageService.deleteAllProductImagesByProductId(id);
        return new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
