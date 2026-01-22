package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ProductImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct_Id(int id);
}
