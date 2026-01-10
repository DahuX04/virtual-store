package com.qodara.virtual_store.virtualStoreAPI.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "The product name is required")
    private String name;

    @NotBlank(message = "The product description is required")
    private String description;

    @NotBlank(message = "The product price is required")
    private BigDecimal price;

    @NotBlank(message = "The product stock is required")
    private int stock;

    @NotBlank(message = "The brand ID is required")
    private int brandId;

    @NotBlank(message = "The category ID is required")
    private int categoryId;

}
