package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ProductRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.SaleItemsRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.SaleItemResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.SaleItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sale Item", description = "Sale Item Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/saleItems")
public class SaleItemController {

    private final SaleItemService saleItemService;

    public SaleItemController(SaleItemService saleItemService) {
        this.saleItemService = saleItemService;
    }

    @Operation(summary = "Get all sale items by sale id")
    @GetMapping("/all/{saleId}")
    public ResponseEntity<ApiResponse<List<SaleItemResponseDTO>>> getAllSaleItemsBySaleId(@PathVariable int saleId) {
        var res = saleItemService.getAllSaleItems(saleId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create  sale items")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<List<SaleItemResponseDTO>>> createSaleItems(@RequestBody List<SaleItemsRequestDTO> saleItemsRequestDTOS) {
        var res = saleItemService.createSaleItems(saleItemsRequestDTOS);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update sale items")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<List<SaleItemResponseDTO>>> updateSaleItems(@PathVariable int id, @RequestBody List<SaleItemsRequestDTO> saleItemsRequestDTOS) {
        var res = saleItemService.updateSaleItems(id, saleItemsRequestDTOS);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Exist sale items by product id")
    @GetMapping("/exist/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> existSaleItemsBySaleId(@PathVariable int productId) {
        var res = saleItemService.existSaleItemByProductId(productId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
