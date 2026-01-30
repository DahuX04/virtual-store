package com.qodara.virtual_store.virtualStoreAPI.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryImageResponseDTO {
    private int id;
    private String name;
    private String url;
}
