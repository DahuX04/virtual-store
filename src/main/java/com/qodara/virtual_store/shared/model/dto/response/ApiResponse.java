package com.qodara.virtual_store.shared.model.dto.response;

import com.qodara.virtual_store.shared.model.enums.Estatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private Estatus status;
    private T data;
}
