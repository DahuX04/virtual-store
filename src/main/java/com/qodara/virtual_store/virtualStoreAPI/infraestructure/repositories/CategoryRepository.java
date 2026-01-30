package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Category;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends CrudRepository<Category, Integer>, JpaRepository<Category, Integer> {

    boolean existsByName(String name);

    boolean existsBrandByNameAndIdNot(String name, int id);

    @Query("""
    SELECT c FROM Category c
    WHERE
      (:q IS NULL OR :q = '' OR
       LOWER(c.name) LIKE LOWER(CONCAT('%', :q, '%'))
      )
  """)
    Page<Category> search(@Param("q") String q, Pageable pageable);

}
