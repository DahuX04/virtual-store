package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ShoppingCartRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ShoppingCartResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ShoppingCart", description = "Shopping Cart Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    @Operation(summary = "Get shopping cart by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShoppingCartResponseDTO>> getShoppingCartById(@PathVariable int id) {
        ApiResponse<ShoppingCartResponseDTO> response = shoppingCartService.getShoppingCartById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get shopping cart by account id")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<ShoppingCartResponseDTO>> getShoppingCartByAccountId(@PathVariable int accountId) {
        ApiResponse<ShoppingCartResponseDTO> response = shoppingCartService.getShoppingCartByAccountId(accountId);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create shopping cart")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ShoppingCartResponseDTO>> createShoppingCart(@RequestBody ShoppingCartRequestDTO ShoppingCartRequestDTO) {
        var res = shoppingCartService.createShoppingCart(ShoppingCartRequestDTO);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update shopping cart")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ShoppingCartResponseDTO>> updateShoppingCart(@PathVariable int id, @RequestBody ShoppingCartRequestDTO shoppingCartRequestDTO) {
        var res = shoppingCartService.updateShoppingCart(id, shoppingCartRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete shopping cart")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShoppingCart(@PathVariable int id) {
        var res = shoppingCartService.deleteShoppingCart(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
