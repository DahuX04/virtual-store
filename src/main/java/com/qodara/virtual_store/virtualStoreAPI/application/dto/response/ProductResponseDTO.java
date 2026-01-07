package com.qodara.virtual_store.virtualStoreAPI.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private BrandResponseDTO brand;

}
