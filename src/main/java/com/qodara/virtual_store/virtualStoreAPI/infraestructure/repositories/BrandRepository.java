package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Brand;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BrandRepository extends CrudRepository<Brand, Integer> {

    boolean existsByName(String name);

    Optional<Brand> findBrandByName(String name);

    boolean existsBrandByNameAndIdNot(String name, int id);
}
