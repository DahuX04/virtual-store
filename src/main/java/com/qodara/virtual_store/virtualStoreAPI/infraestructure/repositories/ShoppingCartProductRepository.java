package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ShoppingCartProduct;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ShoppingCartProductId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartProductRepository extends CrudRepository<ShoppingCartProduct, ShoppingCartProductId> {
    List<ShoppingCartProduct> findAllByShoppingCartId(int shoppingCartId);
    List<ShoppingCartProduct> findAllByShoppingCart_Account(int accountId);
    Optional<ShoppingCartProduct> findByShoppingCartIdAndProductId(int shoppingCartId, int productId);



}
