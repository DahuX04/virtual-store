package com.qodara.virtual_store.virtualStoreAPI.application.controller;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ProductRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.SaleRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.SaleResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sale", description = "Sale Controller")
@RestController
@RequestMapping("/api/v1/virtualStore/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @Operation(summary = "Get all sales")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SaleResponseDTO>>> getAllSales() {
        var res = saleService.getAllSales();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get sale by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleResponseDTO>> getSaleById(@PathVariable int id) {
        ApiResponse<SaleResponseDTO> response = saleService.getSaleById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create  sale")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<SaleResponseDTO>> createProduct(@RequestBody SaleRequestDTO saleRequestDTO) {
        var res = saleService.createSale(saleRequestDTO);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete sale")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSale(@PathVariable int id) {
        var res = saleService.deleteSale(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
