package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.CategoryImage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryImageRepository extends CrudRepository<CategoryImage, Integer> {
    Optional<CategoryImage> findByCategory_Id(int id);
}
