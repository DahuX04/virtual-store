package com.qodara.virtual_store.virtualStoreAPI.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponseDTO {

    private int id;
    private LocalDate date;
    private String saleNumber;
    private BigDecimal totalAmount;

}
