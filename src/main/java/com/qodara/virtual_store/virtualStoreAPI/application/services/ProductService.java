package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ProductRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ApiResponse<ProductResponseDTO> getProductById(int id);
    ApiResponse<ProductResponseDTO> getProductByName(String name);
    ApiResponse<List<ProductResponseDTO>> getProductsByBrand(int brandId);
    ApiResponse<List<ProductResponseDTO>> getAllProducts();
    ApiResponse<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO);
    ApiResponse<ProductResponseDTO> updateProduct(int id, ProductRequestDTO productRequestDTO);
    ApiResponse<Void> deleteProduct(int id);
}
