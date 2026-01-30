package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.StorageClient;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.ProductImageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.ProductImageService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Product;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.ProductImage;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ProductImageRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ModelMapper modelMapper;
    public ProductImageServiceImpl(ProductRepository productRepository, ProductImageRepository productImageRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.modelMapper = modelMapper;
    }

    @Value("${BUCKET_NAME}")
    private String bucketName;

    @Override
    public ApiResponse<List<ProductImageResponseDTO>> getProductImagesByProductId(int id) {
        List<ProductImage> productImageList = productImageRepository.findByProduct_Id(id);
        List<ProductImageResponseDTO> productImageListResponse = productImageList.stream()
                .map(entity -> modelMapper.map(entity, ProductImageResponseDTO.class))
                .toList();
        return new ApiResponse<>("Product Images fetched successfully", Estatus.SUCCESS, productImageListResponse);
    }

    @Override
    public ApiResponse<ProductImageResponseDTO> getProductImageById(int id) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isPresent()) {
            ProductImage productImage = productImageOptional.get();
            ProductImageResponseDTO productImageResponse = modelMapper.map(productImage, ProductImageResponseDTO.class);
            return new ApiResponse<>("Product Image fetched successfully", Estatus.SUCCESS, productImageResponse);
        } else {
            return new ApiResponse<>("Product Image not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<List<ProductImageResponseDTO>> getAllProductImages() {
        List<ProductImage> productImageList = (List<ProductImage>) productImageRepository.findAll();
        List<ProductImageResponseDTO> productImageListResponse = productImageList.stream()
                .map(entity -> modelMapper.map(entity, ProductImageResponseDTO.class))
                .toList();
        return new ApiResponse<>("Product Images fetched successfully", Estatus.SUCCESS, productImageListResponse);
    }

    @Override
    public ApiResponse<ProductImageResponseDTO> saveProductImage (MultipartFile file, String name, int product_id) throws Exception {
        Optional<Product> ProductOptional = productRepository.findById(product_id);
        if (ProductOptional.isEmpty()){
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        }
        String publicUrl = generatePublicUrl(file);

        ProductImage productImage = ProductImage.builder()
                .name(name)
                .url(publicUrl)
                .product(ProductOptional.get())
                .build();

        productImageRepository.save(productImage);
        var productImageResponse = modelMapper.map(productImage, ProductImageResponseDTO.class);
        return new ApiResponse<>("Product Image saved successfully", Estatus.SUCCESS, productImageResponse);
    }

    @Override
    public ApiResponse<List<ProductImageResponseDTO>> saveProductImages (List<MultipartFile> files, String name, int product_id) throws Exception {
        Optional<Product> ProductOptional = productRepository.findById(product_id);
        if (ProductOptional.isEmpty()){
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        }
        Product product = ProductOptional.get();
        List<ProductImage> productImageList = new ArrayList<>();
        int i = 1;
        for (MultipartFile file : files) {
            String publicUrl = generatePublicUrl(file);
            String imageName = name + " " + i++;
            ProductImage productImage = ProductImage.builder()
                    .name(imageName)
                    .url(publicUrl)
                    .product(product)
                    .build();

            productImageList.add(productImage);
        }
        productImageRepository.saveAll(productImageList);
        List<ProductImageResponseDTO> response = productImageList.stream()
                .map(image -> modelMapper.map(image, ProductImageResponseDTO.class))
                .toList();
        return new ApiResponse<>("Product Images saved successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<ProductImageResponseDTO> updateProductImage(int id, MultipartFile file, String name, int product_id) throws Exception {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isEmpty()) {
            return new ApiResponse<>("Product Image not found", Estatus.ERROR, null);
        }

        Optional<Product> productOptional = productRepository.findById(product_id);
        if (productOptional.isEmpty()) {
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        }

        ProductImage productImage = productImageOptional.get();
        String publicUrl = generatePublicUrl(file);

        productImage.setName(name);
        productImage.setUrl(publicUrl);
        productImage.setProduct(productOptional.get());

        productImageRepository.save(productImage);
        var productImageResponse = modelMapper.map(productImage, ProductImageResponseDTO.class);
        return new ApiResponse<>("Product Image updated successfully", Estatus.SUCCESS, productImageResponse);
    }

    @Override
    public ApiResponse<ProductImageResponseDTO> updateOnlyProductImage(int id, MultipartFile file) throws Exception {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isEmpty()) {
            return new ApiResponse<>("Product Image not found", Estatus.ERROR, null);
        }

        ProductImage productImage = productImageOptional.get();
        String publicUrl = generatePublicUrl(file);

        productImage.setUrl(publicUrl);

        productImageRepository.save(productImage);
        var productImageResponse = modelMapper.map(productImage, ProductImageResponseDTO.class);
        return new ApiResponse<>("Product Image updated successfully", Estatus.SUCCESS, productImageResponse);
    }

    @Override
    public ApiResponse<ProductImageResponseDTO> updateProductImageData(int id, String name, int product_id) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isEmpty()) {
            return new ApiResponse<>("Product Image not found", Estatus.ERROR, null);
        }

        Optional<Product> productOptional = productRepository.findById(product_id);
        if (productOptional.isEmpty()) {
            return new ApiResponse<>("Product not found", Estatus.ERROR, null);
        }

        ProductImage productImage = productImageOptional.get();

        productImage.setName(name);
        productImage.setProduct(productOptional.get());

        productImageRepository.save(productImage);
        var productImageResponse = modelMapper.map(productImage, ProductImageResponseDTO.class);
        return new ApiResponse<>("Product Image data updated successfully", Estatus.SUCCESS, productImageResponse);
    }

    @Override
    public ApiResponse<Void> deleteProductImage(int id) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isPresent()) {
            deleteFromFirebaseByUrl(productImageOptional.get().getUrl());
            productImageRepository.deleteById(id);
            return new ApiResponse<>("Product Image deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Product Image not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<Void> deleteAllProductImagesByProductId(int id) {
        List<ProductImage> productImageList = productImageRepository.findByProduct_Id(id);
        if (!productImageList.isEmpty()) {
            for(ProductImage p : productImageList){
                deleteFromFirebaseByUrl(p.getUrl());
            }
            productImageRepository.deleteAll(productImageList);
            return new ApiResponse<>("Product Images deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("No Product Images found for the given Product ID", Estatus.ERROR, null);
        }
    }


    private String extractObjectNameFromFirebaseUrl(String url) {
        // Busca el segmento "/o/" y corta hasta el "?"
        int oIndex = url.indexOf("/o/");
        if (oIndex == -1) {
            throw new IllegalArgumentException("URL no válida de Firebase Storage: falta '/o/'");
        }

        int start = oIndex + 3; // después de "/o/"
        int end = url.indexOf("?", start);
        if (end == -1) end = url.length();

        String encodedObjectName = url.substring(start, end);
        return URLDecoder.decode(encodedObjectName, StandardCharsets.UTF_8);
    }

    private void deleteFromFirebaseByUrl(String publicUrl) {
        String objectName = extractObjectNameFromFirebaseUrl(publicUrl);

        Blob blob = StorageClient.getInstance().bucket().get(objectName);

        if (blob == null) {
            // El archivo no existe en el bucket (o el nombre no coincide)
            throw new IllegalStateException("No se encontró el archivo en Firebase Storage: " + objectName);
        }

        boolean deleted = blob.delete();
        if (!deleted) {
            throw new IllegalStateException("No se pudo eliminar el archivo en Firebase Storage: " + objectName);
        }
    }

    private String generatePublicUrl(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        StorageClient.getInstance().bucket().create(fileName, file.getInputStream(), file.getContentType());

        Blob blob = StorageClient.getInstance().bucket().get(fileName);

        return "https://firebasestorage.googleapis.com/v0/b/"+ bucketName + "/o/"
                + URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                + "?alt=media&token=" + blob.getGeneration();
    }

}
