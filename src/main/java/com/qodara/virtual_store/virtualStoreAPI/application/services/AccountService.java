package com.qodara.virtual_store.virtualStoreAPI.application.services;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.AccountResponseDTO;

public interface AccountService {

    ApiResponse<AccountResponseDTO> getAccountById(int accountId);

}
