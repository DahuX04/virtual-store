package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> findBrandByName(String name);

    List<Product> findProductsByBrandId(int brandId);

    boolean existsByName(String name);

    boolean existsBrandByNameAndIdNot(String name, int id);
}
