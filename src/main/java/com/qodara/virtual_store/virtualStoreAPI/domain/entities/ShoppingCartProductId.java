package com.qodara.virtual_store.virtualStoreAPI.domain.entities;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartProductId implements Serializable {
    private int shoppingCart;
    private int product;
}
