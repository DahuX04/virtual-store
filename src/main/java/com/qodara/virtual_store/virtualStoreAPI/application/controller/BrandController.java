package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.BrandResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.PageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Brand", description = "Brand Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "Get brand by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> getBrandById(@PathVariable int id) {
        ApiResponse<BrandResponseDTO> response = brandService.getBrandById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get brand by name")
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> getBrandByName(@PathVariable String name) {
        ApiResponse<BrandResponseDTO> response = brandService.getBrandByName(name);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get all brands")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BrandResponseDTO>>> getAllBrands() {
        var res = brandService.getAllBrands();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create  brand")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> createBrand(@RequestBody BrandRequestDTO brandRequestDTO) {
        var res = brandService.createBrand(brandRequestDTO);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update brand")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> updateBrand(@PathVariable int id, @RequestBody BrandRequestDTO brandRequestDTO) {
        var res = brandService.updateBrand(id, brandRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete brand")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable int id) {
        var res = brandService.deleteBrand(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get all Brand paged")
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PageResponseDTO<BrandResponseDTO>>> getAllBrandsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String q
    ) {
        var res = brandService.getBrandPaged(page, size, sortBy, sortDir, q);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
