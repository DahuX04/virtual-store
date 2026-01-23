package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    ApiResponse<List<ProductImageResponseDTO>> getProductImagesByProductId (int id);
    ApiResponse<ProductImageResponseDTO> getProductImageById (int id);
    ApiResponse<List<ProductImageResponseDTO>> getAllProductImages ();
    ApiResponse<ProductImageResponseDTO> saveProductImage (MultipartFile file, String name, int product_id) throws Exception;
    ApiResponse<List<ProductImageResponseDTO>> saveProductImages (List<MultipartFile> files, String name, int product_id) throws Exception;
    ApiResponse<ProductImageResponseDTO> updateProductImage (int id, MultipartFile file, String name, int product_id) throws Exception;
    ApiResponse<ProductImageResponseDTO> updateOnlyProductImage (int id, MultipartFile file) throws Exception;
    ApiResponse<ProductImageResponseDTO> updateProductImageData (int id, String name, int product_id);
    ApiResponse<Void> deleteProductImage (int id);
    ApiResponse<Void> deleteAllProductImagesByProductId (int id);

}
