package com.qodara.virtual_store.virtualStoreAPI.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleItemsRequestDTO {

    private int saleId;
    private int productId;
    private int quantity;

}
