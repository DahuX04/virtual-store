package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ProductRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.PageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ApiResponse<ProductResponseDTO> getProductById(int id);
    ApiResponse<ProductResponseDTO> getProductByName(String name);
    ApiResponse<List<ProductResponseDTO>> getProductsByBrand(int brandId);
    ApiResponse<Page<ProductResponseDTO>> getProductsByCategory(int categoryId, Pageable pageable);
    ApiResponse<List<ProductResponseDTO>> getAllProducts();
    ApiResponse<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO);
    ApiResponse<ProductResponseDTO> updateProduct(int id, ProductRequestDTO productRequestDTO);
    ApiResponse<Void> deleteProduct(int id);
    ApiResponse<PageResponseDTO<ProductResponseDTO>> getProductsPaged(int page, int size, String sortBy, String sortDir, String q);

}
