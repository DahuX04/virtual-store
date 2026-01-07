package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.exception.ValidationException;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ProductRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.BrandResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ProductService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Brand;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Product;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.BrandRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
    }

    public ApiResponse<ProductResponseDTO> getProductById(int id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
            return new ApiResponse<>("Product found", Estatus.SUCCESS, productResponseDTO);
        } else {
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        }
    }

    public ApiResponse<ProductResponseDTO> getProductByName(String name){
        Optional<Product> productOptional = productRepository.findBrandByName(name);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
            return new ApiResponse<>("Product found", Estatus.SUCCESS, productResponseDTO);
        } else {
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        }
    }

    public ApiResponse<List<ProductResponseDTO>> getProductsByBrand(int brandId){
        List<Product> products = productRepository.findProductsByBrandId(brandId);
        List<ProductResponseDTO> productResponseDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList();
        return new ApiResponse<>("Products found", Estatus.SUCCESS, productResponseDTOs);
    }

    public ApiResponse<List<ProductResponseDTO>> getAllProducts(){
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList();
        return new ApiResponse<>("Products found", Estatus.SUCCESS, productResponseDTOs);
    }

    public ApiResponse<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO){
        var product = modelMapper.map(productRequestDTO, Product.class);
        validateProduct(productRequestDTO);
        Optional<Brand> brandOptional = brandRepository.findById(productRequestDTO.getBrandId());
        if (brandOptional.isEmpty()) {
            throw new ValidationException("The brand associated with the product does not exist");
        } else {
            product.setBrand(brandOptional.get());
        }
        System.out.println("ID product antes de save = " + product.getId());
        productRepository.save(product);

        var productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
        return new ApiResponse<>("Product created successfully", Estatus.SUCCESS, productResponseDTO);
    }

    public ApiResponse<ProductResponseDTO> updateProduct(int id, ProductRequestDTO productRequestDTO){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        }else {
            Product product = productOptional.get();
            modelMapper.map(productRequestDTO, product);
            validateUpdateProduct(id, productRequestDTO);

            Optional<Brand> brandOptional = brandRepository.findById(productRequestDTO.getBrandId());
            if (brandOptional.isEmpty()) {
                throw new ValidationException("The brand associated with the product does not exist");
            } else {
                product.setBrand(brandOptional.get());
            }
            productRepository.save(product);

            productRepository.save(product);
            ProductResponseDTO response = modelMapper.map(product, ProductResponseDTO.class);
            return new ApiResponse<>("Product updated successfully", Estatus.SUCCESS, response);
        }
    }

    public ApiResponse<Void> deleteProduct(int id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        } else {
            productRepository.deleteById(id);
            return new ApiResponse<>("Product deleted successfully", Estatus.SUCCESS, null);
        }
    }

    private void validateProduct(ProductRequestDTO product) {
        if (existProductByName(product.getName())) {
            throw new ValidationException("Brand with the same name already exists");
        }
        if (!brandRepository.existsById(product.getBrandId())) {
            throw new ValidationException("The brand associated with the product does not exist");
        }
    }

    private void validateUpdateProduct(int id, ProductRequestDTO productRequestDTO) {
        if (existsProductByNameAndIdNot(productRequestDTO.getName(), id)) {
            throw new ValidationException("There is already a Job with the same name");
        }
        if (!brandRepository.existsById(productRequestDTO.getBrandId())) {
            throw new ValidationException("The brand associated with the product does not exist");
        }
    }

    private boolean existProductByName(String name) {
        return productRepository.existsByName(name);
    }
    private boolean existsProductByNameAndIdNot(String name, int id) {
        return productRepository.existsBrandByNameAndIdNot(name, id);
    }

}
