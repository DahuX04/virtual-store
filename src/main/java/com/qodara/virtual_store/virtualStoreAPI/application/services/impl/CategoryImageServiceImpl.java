package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.firebase.cloud.StorageClient;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryImageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.CategoryImageService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Category;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.CategoryImage;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.CategoryImageRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryImageServiceImpl implements CategoryImageService {
    private final CategoryImageRepository categoryImageRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    public CategoryImageServiceImpl(CategoryImageRepository categoryImageRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
            this.categoryImageRepository = categoryImageRepository;
            this.categoryRepository = categoryRepository;
            this.modelMapper = modelMapper;
    }

    @Value("${BUCKET_NAME}")
    private String bucketName;

    @Override
    public ApiResponse<CategoryImageResponseDTO> getCategoryImageByCategoryId (int categoryId) {
        Optional<CategoryImage> categoryImageOptional = categoryImageRepository.findByCategory_Id(categoryId);
        if (categoryImageOptional.isPresent()) {
            CategoryImage categoryImage = categoryImageOptional.get();
            CategoryImageResponseDTO responseDTO = modelMapper.map(categoryImage, CategoryImageResponseDTO.class);
            return new ApiResponse<>("Product Image retrieved successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Product Image not found", Estatus.SUCCESS, null);
        }
    }

    @Override
    public ApiResponse<List<CategoryImageResponseDTO>> getAllCategoryImages() {
        List<CategoryImage> categoryImages = (List<CategoryImage>) categoryImageRepository.findAll();
        List<CategoryImageResponseDTO> responseDTOs = categoryImages.stream()
                .map(categoryImage -> modelMapper.map(categoryImage, CategoryImageResponseDTO.class))
                .toList();
        return new ApiResponse<>("Category Images retrieved successfully", Estatus.SUCCESS, responseDTOs);
    }

    @Override
    public ApiResponse<CategoryImageResponseDTO> saveCategoryImage(MultipartFile file, String name, int category_id) throws Exception {
        Optional<Category> CategoryOptional = categoryRepository.findById(category_id);
        if (CategoryOptional.isEmpty()) {
            return new ApiResponse<>("Category not found", Estatus.ERROR, null);
        }
        String publicUrl = generatePublicUrl(file);
        CategoryImage categoryImage = CategoryImage.builder()
                .name(name)
                .url(publicUrl)
                .category(CategoryOptional.get())
                .build();
        categoryImageRepository.save(categoryImage);
        CategoryImageResponseDTO responseDTO = modelMapper.map(categoryImage, CategoryImageResponseDTO.class);
        return new ApiResponse<>("Category Image saved successfully", Estatus.SUCCESS, responseDTO);
    }

    @Override
    public ApiResponse<CategoryImageResponseDTO> updateCategoryImage(int id, MultipartFile file, String name, int product_id) throws Exception {
        Optional<CategoryImage> categoryImageOptional = categoryImageRepository.findById(id);
        if (categoryImageOptional.isEmpty()) {
            return new ApiResponse<>("Category Image not found", Estatus.ERROR, null);
        }
        CategoryImage categoryImage = categoryImageOptional.get();
        deleteFromFirebaseByUrl(categoryImage.getUrl());
        String publicUrl = generatePublicUrl(file);
        categoryImage.setName(name);
        categoryImage.setUrl(publicUrl);

        categoryImageRepository.save(categoryImage);
        CategoryImageResponseDTO responseDTO = modelMapper.map(categoryImage, CategoryImageResponseDTO.class);
        return new ApiResponse<>("Category Image updated successfully", Estatus.SUCCESS, responseDTO);
    }

    @Override
    public ApiResponse<CategoryImageResponseDTO> updateOnlyCategoryImage(int id, MultipartFile file) throws Exception {
        Optional<CategoryImage> categoryImageOptional = categoryImageRepository.findById(id);
        if (categoryImageOptional.isEmpty()) {
            return new ApiResponse<>("Category Image not found", Estatus.ERROR, null);
        }
        CategoryImage categoryImage = categoryImageOptional.get();
        deleteFromFirebaseByUrl(categoryImage.getUrl());
        String publicUrl = generatePublicUrl(file);
        categoryImage.setUrl(publicUrl);

        categoryImageRepository.save(categoryImage);
        CategoryImageResponseDTO responseDTO = modelMapper.map(categoryImage, CategoryImageResponseDTO.class);
        return new ApiResponse<>("Category Image updated successfully", Estatus.SUCCESS, responseDTO);
    }

    @Override
    public ApiResponse<CategoryImageResponseDTO> updateCategoryImageData(int id, String name, int category_id) {
        Optional<CategoryImage> categoryImageOptional = categoryImageRepository.findById(id);
        if (categoryImageOptional.isEmpty()) {
            return new ApiResponse<>("Category Image not found", Estatus.ERROR, null);
        }
        CategoryImage categoryImage = categoryImageOptional.get();
        categoryImage.setName(name);

        categoryImageRepository.save(categoryImage);
        CategoryImageResponseDTO responseDTO = modelMapper.map(categoryImage, CategoryImageResponseDTO.class);
        return new ApiResponse<>("Category Image data updated successfully", Estatus.SUCCESS, responseDTO);
    }

    @Override
    public ApiResponse<Void> deleteCategoryImage(int id) {
        Optional<CategoryImage> categoryImageOptional = categoryImageRepository.findById(id);
        if (categoryImageOptional.isEmpty()) {
            return new ApiResponse<>("Category Image not found", Estatus.ERROR, null);
        }
        deleteFromFirebaseByUrl(categoryImageOptional.get().getUrl());
        categoryImageRepository.deleteById(id);
        return new ApiResponse<>("Category Image deleted successfully", Estatus.SUCCESS, null);
    }

    @Override
    public ApiResponse<Void> deleteCategoryImageByCategoryId (int categoryId) {
        Optional<CategoryImage> categoryImageOptional = categoryImageRepository.findByCategory_Id(categoryId);
        if (categoryImageOptional.isEmpty()) {
            return new ApiResponse<>("Category Image not found", Estatus.SUCCESS, null);
        }
        deleteFromFirebaseByUrl(categoryImageOptional.get().getUrl());
        categoryImageRepository.delete(categoryImageOptional.get());
        return new ApiResponse<>("Category Image deleted successfully", Estatus.SUCCESS, null);
    }


    private String extractObjectNameFromFirebaseUrl(String url) {
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
            throw new IllegalStateException("No se encontró el archivo en Firebase Storage: " + objectName);
        }

        boolean deleted = blob.delete();
        if (!deleted) {
            throw new IllegalStateException("No se pudo eliminar el archivo en Firebase Storage: " + objectName);
        }
    }

    private String generatePublicUrl(MultipartFile file) throws Exception {
        String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "image";
        String safeName = sanitizeFilename(original);

        String objectName = "categories/" + UUID.randomUUID() + "-" + safeName;
        String token = UUID.randomUUID().toString();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName)
                .setContentType(file.getContentType())
                .setMetadata(Map.of("firebaseStorageDownloadTokens", token))
                .build();

        // Subida
        StorageClient.getInstance()
                .bucket()
                .getStorage()
                .create(blobInfo, file.getBytes());

        return "https://firebasestorage.googleapis.com/v0/b/"+ bucketName + "/o/"
                + URLEncoder.encode(objectName, StandardCharsets.UTF_8.toString())
                + "?alt=media&token=" + token;
    }

    private String sanitizeFilename(String name) {
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }


}
