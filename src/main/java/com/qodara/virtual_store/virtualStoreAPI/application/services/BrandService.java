package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.BrandResponseDTO;

import java.util.List;

public interface BrandService {

    ApiResponse<BrandResponseDTO> getBrandById(int id);
    ApiResponse<BrandResponseDTO> getBrandByName(String name);
    ApiResponse<List<BrandResponseDTO>> getAllBrands();
    ApiResponse<BrandResponseDTO> createBrand(BrandRequestDTO brandRequestDTO);
    ApiResponse<BrandResponseDTO> updateBrand(int id, BrandRequestDTO brandRequestDTO);
    ApiResponse<Void> deleteBrand(int id);

}
