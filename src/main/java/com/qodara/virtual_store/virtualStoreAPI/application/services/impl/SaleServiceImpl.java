package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.SaleRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.SaleResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.SaleService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Sale;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.SaleItemRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final SaleItemRepository saleItemRepository;

    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper modelMapper, SaleItemRepository saleItemRepository) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.saleItemRepository = saleItemRepository;
    }

    public ApiResponse<SaleResponseDTO> createSale(SaleRequestDTO saleRequestDTO){
        Sale sale = new Sale();
        if(saleRequestDTO.getDate() != null){
            sale.setDate(saleRequestDTO.getDate());
        } else {
            sale.setDate(LocalDate.now());
        }
        sale.setTotalAmount(BigDecimal.valueOf(0));

        Sale savedSale = saleRepository.save(sale);
        savedSale.setSaleNumber("SALE-" + String.format("%07d", savedSale.getId()));
        saleRepository.save(savedSale);

        var saleResponseDTO = modelMapper.map(savedSale, SaleResponseDTO.class);

        return new ApiResponse<>("Sale created successfully", Estatus.SUCCESS,saleResponseDTO);

    }

    public ApiResponse<Void> deleteSale(int id){

        Optional<Sale> optionalSale = saleRepository.findById(id);
        if(optionalSale.isPresent()){
            saleItemRepository.deleteBySale_Id(id);
            saleRepository.deleteById(id);
            return new ApiResponse<>("Sale deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Sale not found", Estatus.ERROR, null);
        }

    }

    public ApiResponse<List<SaleResponseDTO>> getAllSales(){
        List<Sale> sales = (List<Sale>) saleRepository.findAll();
        List<SaleResponseDTO> saleResponseDTOs = sales.stream()
                .map(sale -> modelMapper.map(sale, SaleResponseDTO.class))
                .toList();
        return new ApiResponse<>("Sales retrieved successfully", Estatus.SUCCESS, saleResponseDTOs);
    }

    public ApiResponse<SaleResponseDTO> getSaleById(int id){
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if(optionalSale.isPresent()){
            SaleResponseDTO saleResponseDTO = modelMapper.map(optionalSale.get(), SaleResponseDTO.class);
            return new ApiResponse<>("Sale retrieved successfully", Estatus.SUCCESS, saleResponseDTO);
        } else {
            return new ApiResponse<>("Sale not found", Estatus.ERROR, null);
        }
    }

}
