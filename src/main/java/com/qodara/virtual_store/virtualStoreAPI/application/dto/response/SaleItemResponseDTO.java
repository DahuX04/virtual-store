package com.qodara.virtual_store.virtualStoreAPI.application.dto.response;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.SaleItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleItemResponseDTO {

    private int id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;

}
