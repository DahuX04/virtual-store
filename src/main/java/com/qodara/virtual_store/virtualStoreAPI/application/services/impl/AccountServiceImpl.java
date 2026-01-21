package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.AccountResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.AccountService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Account;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ApiResponse<AccountResponseDTO> getAccountById(int accountId) {
        var accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return new ApiResponse<>("Account not found", Estatus.ERROR, null);
        }
        Account account = accountOpt.get();
        var accountDTO = AccountResponseDTO.builder()
                .id(account.getId())
                .name(account.getName())
                .lastName(account.getLastname())
                .build();
        return new ApiResponse<>("Account retrieved successfully", Estatus.SUCCESS, accountDTO);
    }

}
