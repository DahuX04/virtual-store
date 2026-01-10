package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.CategoryRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    ApiResponse<CategoryResponseDTO> getCategoryById(int id);
    ApiResponse<List<CategoryResponseDTO>> getAllCategories();
    ApiResponse<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO);
    ApiResponse<CategoryResponseDTO> updateCategory(int id, CategoryRequestDTO categoryRequestDTO);
    ApiResponse<Void> deleteCategory(int id);

}
