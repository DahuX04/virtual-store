package com.qodara.virtual_store.virtualStoreAPI.application.dto.response;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartProductResponseDto {
    private ProductResponseDTO product;
    private int quantity;
}
