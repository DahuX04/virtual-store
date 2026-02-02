package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.BrandResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.PageResponseDTO;

import java.util.List;

public interface BrandService {

    ApiResponse<BrandResponseDTO> getBrandById(int id);
    ApiResponse<BrandResponseDTO> getBrandByName(String name);
    ApiResponse<List<BrandResponseDTO>> getAllBrands();
    ApiResponse<BrandResponseDTO> createBrand(BrandRequestDTO brandRequestDTO);
    ApiResponse<BrandResponseDTO> updateBrand(int id, BrandRequestDTO brandRequestDTO);
    ApiResponse<PageResponseDTO<BrandResponseDTO>> getBrandPaged(int page, int size, String sortBy, String sortDir, String q);
    ApiResponse<Void> deleteBrand(int id);

}
