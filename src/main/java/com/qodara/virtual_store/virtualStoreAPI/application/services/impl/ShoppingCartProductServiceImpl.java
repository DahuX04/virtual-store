package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ShoppingCartProductResponseDto;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ShoppingCartResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ShoppingCartProductService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ShoppingCartProduct;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ProductRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ShoppingCartProductRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartProductServiceImpl implements ShoppingCartProductService {
    private final ShoppingCartProductRepository shoppingCartProductRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    public ShoppingCartProductServiceImpl(ShoppingCartProductRepository shoppingCartProductRepository,
                                          ShoppingCartRepository shoppingCartRepository,
                                          ProductRepository productRepository,
                                          ModelMapper modelMapper) {
        this.shoppingCartProductRepository = shoppingCartProductRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ApiResponse<List<ShoppingCartProductResponseDto>> getProductsByShoppingCartId(int shoppingCartId) {
        List<ShoppingCartProduct> shoppingCartProducts = shoppingCartProductRepository.findAllByShoppingCartId(shoppingCartId);
        List<ShoppingCartProductResponseDto> ShoppingCartProductResponseDTOs = shoppingCartProducts.stream()
                .map(shoppingCartProduct -> modelMapper.map(shoppingCartProduct, ShoppingCartProductResponseDto.class))
                .toList();
        return new ApiResponse<>("Brands retrieved", Estatus.SUCCESS, ShoppingCartProductResponseDTOs);
    }

    public ApiResponse<List<ShoppingCartProductResponseDto>> getProductsByAccountId(int accountId){
        List<ShoppingCartProduct> shoppingCartProducts = shoppingCartProductRepository.findAllByShoppingCart_Account(accountId);
        List<ShoppingCartProductResponseDto> ShoppingCartProductResponseDTOs = shoppingCartProducts.stream()
                .map(shoppingCartProduct -> modelMapper.map(shoppingCartProduct, ShoppingCartProductResponseDto.class))
                .toList();
        return new ApiResponse<>("Brands retrieved", Estatus.SUCCESS, ShoppingCartProductResponseDTOs);
    }

    public ApiResponse<ShoppingCartProductResponseDto> createProductsInShoppingCart(int shoppingCartId, int productId, int quantity){
        var shoppingCartOpt = shoppingCartRepository.findById(shoppingCartId);
        var productOpt = productRepository.findById(productId);
        if(shoppingCartOpt.isEmpty() || productOpt.isEmpty()){
            return new ApiResponse<>("Shopping cart or product not found", Estatus.ERROR, null);
        }
        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
        shoppingCartProduct.setShoppingCart(shoppingCartOpt.get());
        shoppingCartProduct.setProduct(productOpt.get());
        shoppingCartProduct.setQuantity(quantity);
        ShoppingCartProduct savedShoppingCartProduct = shoppingCartProductRepository.save(shoppingCartProduct);
        ShoppingCartProductResponseDto responseDto = modelMapper.map(savedShoppingCartProduct, ShoppingCartProductResponseDto.class);
        return new ApiResponse<>("Product added to shopping cart", Estatus.SUCCESS, responseDto);
    }

    public ApiResponse<ShoppingCartProductResponseDto> updateProductQuantityInShoppingCart(int shoppingCartId, int productId, int quantity){
        Optional<ShoppingCartProduct> shoppingCartProductOpt =
                shoppingCartProductRepository.findByShoppingCartIdAndProductId(shoppingCartId, productId);
        if(shoppingCartProductOpt.isPresent()){
            ShoppingCartProduct shoppingCartProduct = shoppingCartProductOpt.get();
            shoppingCartProduct.setQuantity(quantity);
            ShoppingCartProduct updatedShoppingCartProduct = shoppingCartProductRepository.save(shoppingCartProduct);
            ShoppingCartProductResponseDto responseDto = modelMapper.map(updatedShoppingCartProduct, ShoppingCartProductResponseDto.class);
            return new ApiResponse<>("Product quantity updated", Estatus.SUCCESS, responseDto);
        } else {
            return new ApiResponse<>("Product not found in shopping cart", Estatus.ERROR, null);
        }
    }

    public ApiResponse<Void> deleteProductsFromShoppingCart(int shoppingCartId, int productId){
        Optional<ShoppingCartProduct> shoppingCartProductOpt =
                shoppingCartProductRepository.findByShoppingCartIdAndProductId(shoppingCartId, productId);
        if(shoppingCartProductOpt.isPresent()){
            shoppingCartProductRepository.delete(shoppingCartProductOpt.get());
            return new ApiResponse<>("Product removed from shopping cart", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Product not found in shopping cart", Estatus.ERROR, null);
        }
    }

    public ApiResponse<Void> deleteAllProductsFromShoppingCart(int shoppingCartId){
        List<ShoppingCartProduct> shoppingCartProducts = shoppingCartProductRepository.findAllByShoppingCartId(shoppingCartId);
        if (!shoppingCartProducts.isEmpty()){
            shoppingCartProductRepository.deleteAll(shoppingCartProducts);
            return new ApiResponse<>("All products removed from shopping cart", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("No products found in shopping cart", Estatus.ERROR, null);
        }
    }

}
