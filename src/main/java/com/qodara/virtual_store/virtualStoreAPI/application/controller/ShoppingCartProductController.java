package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ShoppingCartProductResponseDto;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ShoppingCartProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ShoppingCartProduct", description = "Shopping Cart Product Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/shopping-cart-product")
public class ShoppingCartProductController {
    private final ShoppingCartProductService shoppingCartProductService;
    public ShoppingCartProductController(ShoppingCartProductService shoppingCartProductService) {
        this.shoppingCartProductService = shoppingCartProductService;
    }

    @Operation(summary = "Get products by shopping cart id")
    @GetMapping("/shopping-cart/{shoppingCartId}")
    public ResponseEntity<ApiResponse<List<ShoppingCartProductResponseDto>>> getProductsByShoppingCartId(@PathVariable int shoppingCartId) {
        var res = shoppingCartProductService.getProductsByShoppingCartId(shoppingCartId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get shopping carts by account id")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<ShoppingCartProductResponseDto>>> getShoppingCartsByAccountId(@PathVariable int accountId) {
        var res = shoppingCartProductService.getProductsByAccountId(accountId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create shopping cart product")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ShoppingCartProductResponseDto>> createShoppingCartProduct(@RequestParam int shoppingCartId, @RequestParam int productId, @RequestParam int quantity) {
        var res = shoppingCartProductService.createProductsInShoppingCart(shoppingCartId, productId, quantity);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update shopping cart product quantity")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<ShoppingCartProductResponseDto>> updateShoppingCartProduct(@RequestParam int shoppingCartId, @RequestParam int productId, @RequestParam int quantity) {
        var res = shoppingCartProductService.updateProductQuantityInShoppingCart(shoppingCartId, productId, quantity);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete shopping cart product")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteShoppingCartProduct(@RequestParam int shoppingCartId, @RequestParam int productId) {
        var res = shoppingCartProductService.deleteProductsFromShoppingCart(shoppingCartId, productId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete all products from shopping cart")
    @DeleteMapping("/delete-all/{shoppingCartId}")
    public ResponseEntity<ApiResponse<Void>> deleteAllProductsFromShoppingCart(@PathVariable int shoppingCartId) {
        var res = shoppingCartProductService.deleteAllProductsFromShoppingCart(shoppingCartId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
