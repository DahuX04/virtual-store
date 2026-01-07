package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ShoppingCartRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ShoppingCartResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ShoppingCartService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ShoppingCart;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ShoppingCartProduct;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ShoppingCartProductRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartProductRepository shoppingCartProductRepository;
    private final ModelMapper modelMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   ShoppingCartProductRepository shoppingCartProductRepository,
                                   ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartProductRepository = shoppingCartProductRepository;
        this.modelMapper = modelMapper;
    }

    public ApiResponse<ShoppingCartResponseDTO> getShoppingCartById(int id) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findShoppingCartById(id);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            ShoppingCartResponseDTO shoppingCartResponseDTO = modelMapper.map(shoppingCart, ShoppingCartResponseDTO.class);
            return new ApiResponse<>("Shopping cart found", Estatus.SUCCESS, shoppingCartResponseDTO);
        } else {
            return new ApiResponse<>("Shopping cart not found", Estatus.ERROR, null);
        }
    }

    public ApiResponse<ShoppingCartResponseDTO> getShoppingCartByAccountId(int accountId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findByAccount(accountId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            ShoppingCartResponseDTO shoppingCartResponseDTO = modelMapper.map(shoppingCart, ShoppingCartResponseDTO.class);
            return new ApiResponse<>("Shopping cart found", Estatus.SUCCESS, shoppingCartResponseDTO);
        } else {
            return new ApiResponse<>("Shopping cart not found", Estatus.ERROR, null);
        }
    }

    public ApiResponse<ShoppingCartResponseDTO> createShoppingCart(ShoppingCartRequestDTO shoppingCartRequestDTO) {
        var shoppingCart = modelMapper.map(shoppingCartRequestDTO, ShoppingCart.class);
        shoppingCartRepository.save(shoppingCart);
        var response = modelMapper.map(shoppingCart, ShoppingCartResponseDTO.class);

        return new ApiResponse<>("Shopping cart created successfully", Estatus.SUCCESS, response);
    }

    public ApiResponse<ShoppingCartResponseDTO> updateShoppingCart(int id, ShoppingCartRequestDTO shoppingCartRequestDTO) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(id);
        if (shoppingCartOptional.isEmpty()) {
            return new ApiResponse<>("Shopping cart not found", Estatus.ERROR, null);
        } else {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            modelMapper.map(shoppingCartRequestDTO, shoppingCart);
            shoppingCart.setTotalPrice(calculatingTotalPrice(shoppingCart.getId()));
            shoppingCartRepository.save(shoppingCart);
            var response = modelMapper.map(shoppingCart, ShoppingCartResponseDTO.class);

            return new ApiResponse<>("Shopping cart updated successfully", Estatus.SUCCESS, response);
        }
    }

    public ApiResponse<Void> deleteShoppingCart(int id) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(id);
        if (shoppingCartOptional.isEmpty()) {
            return new ApiResponse<>("Shopping cart not found", Estatus.ERROR, null);
        } else {
            shoppingCartRepository.deleteById(id);
            return new ApiResponse<>("Shopping cart deleted successfully", Estatus.SUCCESS, null);
        }
    }

    private BigDecimal calculatingTotalPrice (int shoppingCartId) {
        List<ShoppingCartProduct> shoppingCartProducts = shoppingCartProductRepository.findAllByShoppingCartId(shoppingCartId);
        return shoppingCartProducts.stream()
                .map(scp -> scp.getProduct().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
