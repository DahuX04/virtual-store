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
public class CategoryRequestDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

}
