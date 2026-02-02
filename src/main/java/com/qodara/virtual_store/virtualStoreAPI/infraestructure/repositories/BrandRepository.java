package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepository extends CrudRepository<Brand, Integer>, JpaRepository<Brand, Integer> {

    boolean existsByName(String name);

    Optional<Brand> findBrandByName(String name);

    boolean existsBrandByNameAndIdNot(String name, int id);

    @Query("""
    SELECT b FROM Brand b
    WHERE
      (:q IS NULL OR :q = '' OR
       LOWER(b.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
       LOWER(b.description) LIKE LOWER(CONCAT('%', :q, '%'))
      )
  """)
    Page<Brand> search(@Param("q") String q, Pageable pageable);

}
