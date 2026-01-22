package com.qodara.virtual_store.virtualStoreAPI.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageResponseDTO {
    private int id;
    private String name;
    private String url;
}
