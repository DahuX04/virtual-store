package com.qodara.virtual_store.virtualStoreAPI.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequestDTO {

    @NotBlank(message = "The brand name is required")
    private String name;

    @NotBlank(message = "The brand description is required")
    private String description;

}
