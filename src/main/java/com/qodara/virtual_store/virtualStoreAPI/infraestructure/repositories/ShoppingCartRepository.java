package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    Optional<ShoppingCart> findShoppingCartById(int id);
    Optional<ShoppingCart> findByAccount(int accountId);
}
