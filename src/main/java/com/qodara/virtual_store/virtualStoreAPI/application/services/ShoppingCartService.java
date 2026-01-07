package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ShoppingCartRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ShoppingCartResponseDTO;

public interface ShoppingCartService {
    ApiResponse<ShoppingCartResponseDTO> getShoppingCartById(int id);
    ApiResponse<ShoppingCartResponseDTO> getShoppingCartByAccountId(int accountId);
    ApiResponse<ShoppingCartResponseDTO> createShoppingCart(ShoppingCartRequestDTO shoppingCartRequestDTO);
    ApiResponse<ShoppingCartResponseDTO> updateShoppingCart(int id, ShoppingCartRequestDTO shoppingCartRequestDTO);
    ApiResponse<Void> deleteShoppingCart(int id);
}
