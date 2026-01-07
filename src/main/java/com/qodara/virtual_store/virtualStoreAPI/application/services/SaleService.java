package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.SaleRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.SaleResponseDTO;

import java.util.List;

public interface SaleService {

    ApiResponse<SaleResponseDTO> createSale(SaleRequestDTO saleRequestDTO);
    ApiResponse<List<SaleResponseDTO>> getAllSales();
    ApiResponse<SaleResponseDTO> getSaleById(int id);
    ApiResponse<Void> deleteSale(int id);

}

