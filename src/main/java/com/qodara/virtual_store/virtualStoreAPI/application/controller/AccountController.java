package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.AccountResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Account", description = "Account Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Get account data by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getAccountById(@PathVariable int id) {
        ApiResponse<AccountResponseDTO> response = accountService.getAccountById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
