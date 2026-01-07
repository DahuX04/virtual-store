package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.SaleItemsRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.SaleItemResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.SaleItemService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Product;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Sale;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.SaleItem;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ProductRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.SaleItemRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepository saleItemRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public SaleItemServiceImpl(SaleItemRepository saleItemRepository, SaleRepository saleRepository, ModelMapper modelMapper, ProductRepository productRepository) {
        this.saleItemRepository = saleItemRepository;
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    public ApiResponse<List<SaleItemResponseDTO>> createSaleItems(List<SaleItemsRequestDTO> saleItemRequestDTOs){
        List<SaleItemResponseDTO> saleItemResponseDTOList = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        Product product;
        Sale sale = null;
        if (saleItemRequestDTOs.isEmpty()){
            return new ApiResponse<>("No sale items to create", Estatus.ERROR,null);
        }
        for(SaleItemsRequestDTO items : saleItemRequestDTOs){
            SaleItem saleItem = new SaleItem();
            Optional<Product> optionalProduct = productRepository.findById(items.getProductId());
            if (optionalProduct.isEmpty()){
                return new ApiResponse<>("Product with id " + items.getProductId() + " not found", Estatus.ERROR,null);
            }
            Optional<Sale> optionalSale = saleRepository.findById(items.getSaleId());
            if (optionalSale.isEmpty()){
                return new ApiResponse<>("Sale with id " + items.getSaleId() + " not found", Estatus.ERROR,null);
            }
            sale = optionalSale.get();
            product = optionalProduct.get();

            saleItem.setProduct(product);
            saleItem.setQuantity(items.getQuantity());
            saleItem.setUnitPrice(product.getPrice());
            saleItem.setLineTotal(product.getPrice().multiply(BigDecimal.valueOf(items.getQuantity())));
            saleItem.setSale(sale);

            SaleItem saleItemSave = saleItemRepository.save(saleItem);

            SaleItemResponseDTO saleItemResponseDTO = modelMapper.map(saleItemSave, SaleItemResponseDTO.class);
            saleItemResponseDTOList.add(saleItemResponseDTO);
            
            total = total.add(saleItemSave.getLineTotal());

            product.setStock(product.getStock() - items.getQuantity());
            productRepository.save(product);
        }
        sale.setTotalAmount(total);
        saleRepository.save(sale);
        return new ApiResponse<>("Sale items created successfully", Estatus.SUCCESS,saleItemResponseDTOList);
    }

    public ApiResponse<List<SaleItemResponseDTO>> updateSaleItems(int id, List<SaleItemsRequestDTO> saleItemRequestDTOs){
        List<SaleItem> saleItemList = saleItemRepository.findSaleItemBySale_Id(id);
        if (saleItemList.isEmpty()){
            return new ApiResponse<>("No sale items found for sale id " + id, Estatus.SUCCESS,null);
        }
        saleItemRepository.deleteAll(saleItemList);
        return createSaleItems(saleItemRequestDTOs);
    }

    public ApiResponse<List<SaleItemResponseDTO>> getAllSaleItems(int saleId){
        List<SaleItem> saleItemList = saleItemRepository.findSaleItemBySale_Id(saleId);
        if (saleItemList.isEmpty()){
            return new ApiResponse<>("No sale items found for sale id " + saleId, Estatus.SUCCESS,null);
        }
        List<SaleItemResponseDTO> saleItemResponseDTOList = new ArrayList<>();
        for (SaleItem saleItem : saleItemList){
            SaleItemResponseDTO saleItemResponseDTO = modelMapper.map(saleItem, SaleItemResponseDTO.class);
            saleItemResponseDTOList.add(saleItemResponseDTO);
        }
        return new ApiResponse<>("Sale items retrieved successfully", Estatus.SUCCESS,saleItemResponseDTOList);
    }

}
