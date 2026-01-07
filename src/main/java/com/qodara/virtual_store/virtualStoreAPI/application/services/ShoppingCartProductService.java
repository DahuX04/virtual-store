package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ShoppingCartProductResponseDto;

import java.util.List;

public interface ShoppingCartProductService {
    ApiResponse<List<ShoppingCartProductResponseDto>> getProductsByShoppingCartId(int shoppingCartId);
    ApiResponse<List<ShoppingCartProductResponseDto>> getProductsByAccountId(int accountId);
    ApiResponse<ShoppingCartProductResponseDto> createProductsInShoppingCart(int shoppingCartId, int productId, int quantity);
    ApiResponse<ShoppingCartProductResponseDto> updateProductQuantityInShoppingCart(int shoppingCartId, int productId, int quantity);
    ApiResponse<Void> deleteProductsFromShoppingCart(int shoppingCartId, int productId);
    ApiResponse<Void> deleteAllProductsFromShoppingCart(int shoppingCartId);
}
