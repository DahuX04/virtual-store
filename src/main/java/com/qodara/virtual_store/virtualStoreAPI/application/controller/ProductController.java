package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ProductRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.BrandResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.PageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "Product Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable int id) {
        ApiResponse<ProductResponseDTO> response = productService.getProductById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get product by name")
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductByName(@PathVariable String name) {
        ApiResponse<ProductResponseDTO> response = productService.getProductByName(name);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get product by Brand")
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getProductByBrand(@PathVariable int brandId) {
        ApiResponse<List<ProductResponseDTO>> response = productService.getProductsByBrand(brandId);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get all products")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProduct() {
        var res = productService.getAllProducts();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create  product")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        var res = productService.createProduct(productRequestDTO);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update brand")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(@PathVariable int id, @RequestBody ProductRequestDTO productRequestDTO) {
        var res = productService.updateProduct(id, productRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete brand")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable int id) {
        var res = productService.deleteProduct(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get all products paged")
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PageResponseDTO<ProductResponseDTO>>> getAllProductsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String q
    ) {
        var res = productService.getProductsPaged(page, size, sortBy, sortDir, q);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
