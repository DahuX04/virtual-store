package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    boolean existsByName(String name);

    boolean existsBrandByNameAndIdNot(String name, int id);

}
