package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryImageService {
    ApiResponse<CategoryImageResponseDTO> getCategoryImageByCategoryId(int categoryId);
    ApiResponse<List<CategoryImageResponseDTO>> getAllCategoryImages();
    ApiResponse<CategoryImageResponseDTO> saveCategoryImage(MultipartFile file, String name, int product_id) throws Exception;
    ApiResponse<CategoryImageResponseDTO> updateCategoryImage(int id, MultipartFile file, String name, int product_id) throws Exception;
    ApiResponse<CategoryImageResponseDTO> updateOnlyCategoryImage(int id, MultipartFile file) throws Exception;
    ApiResponse<CategoryImageResponseDTO> updateCategoryImageData(int id, String name, int product_id);
    ApiResponse<Void> deleteCategoryImage(int id);
    ApiResponse<Void> deleteCategoryImageByCategoryId(int categoryId);
}
