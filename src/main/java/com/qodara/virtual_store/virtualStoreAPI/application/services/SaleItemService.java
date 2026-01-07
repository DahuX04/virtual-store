package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.SaleItemsRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.SaleItemResponseDTO;

import java.util.List;

public interface SaleItemService {

    ApiResponse<List<SaleItemResponseDTO>> getAllSaleItems(int saleId);
    ApiResponse<List<SaleItemResponseDTO>> createSaleItems(List<SaleItemsRequestDTO> saleItemRequestDTOs);
    ApiResponse<List<SaleItemResponseDTO>> updateSaleItems(int id, List<SaleItemsRequestDTO> saleItemRequestDTOs);

}
