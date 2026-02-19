package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer>, JpaRepository<Product, Integer> {

    Optional<Product> findBrandByName(String name);

    List<Product> findProductsByBrandId(int brandId);

    boolean existsByName(String name);

    boolean existsBrandByNameAndIdNot(String name, int id);

    Page<Product> findByBrand_Id(int brandId, Pageable pageable);

    @Query("""
    SELECT p FROM Product p
    JOIN p.brand b
    JOIN p.category c
    WHERE
      (:q IS NULL OR :q = '' OR
       LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
       LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%')) OR
       LOWER(b.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
       LOWER(c.name) LIKE LOWER(CONCAT('%', :q, '%'))
      )
  """)
    Page<Product> search(@Param("q") String q, Pageable pageable);

    Page<Product> findByCategory_Id (int categoryId, Pageable pageable);
}
