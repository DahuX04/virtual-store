package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.SaleItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SaleItemRepository extends CrudRepository<SaleItem, Integer> {
    void deleteBySale_Id(int saleId);

    List<SaleItem> findSaleItemBySale_Id(int saleId);
}
