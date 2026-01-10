package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.exception.ValidationException;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.CategoryRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.CategoryService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Brand;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Category;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public ApiResponse<CategoryResponseDTO> getCategoryById(int id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            CategoryResponseDTO categoryResponseDTO = modelMapper.map(category, CategoryResponseDTO.class);
            return new ApiResponse<>("Category found", Estatus.SUCCESS,categoryResponseDTO);
        } else {
            return new ApiResponse<>("Category not found", Estatus.ERROR,null);
        }
    }

    public ApiResponse<List<CategoryResponseDTO>> getAllCategories(){
        List<Category> categoryList = (List<Category>) categoryRepository.findAll();
        List<CategoryResponseDTO> categoryResponseDTOList = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .toList();
        return new ApiResponse<>("Categories retrieved", Estatus.SUCCESS, categoryResponseDTOList);
    }

    public ApiResponse<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO){
        var category = modelMapper.map(categoryRequestDTO, Category.class);
        validateCategory(category);
        category = categoryRepository.save(category);
        var categoryResponseDTO = modelMapper.map(category, CategoryResponseDTO.class);
        return new ApiResponse<>("Category created", Estatus.SUCCESS, categoryResponseDTO);
    }

    public ApiResponse<CategoryResponseDTO> updateCategory(int id, CategoryRequestDTO categoryRequestDTO){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return new ApiResponse<>("Category not found", Estatus.ERROR, null);
        } else {
            Category category = optionalCategory.get();
            modelMapper.map(categoryRequestDTO, category);
            validateUpdateCategory(id, categoryRequestDTO);
            categoryRepository.save(category);
            CategoryResponseDTO categoryResponseDTO = modelMapper.map(category, CategoryResponseDTO.class);
            return new ApiResponse<>("Category updated", Estatus.SUCCESS, categoryResponseDTO);
        }
    }

    public ApiResponse<Void> deleteCategory(int id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            categoryRepository.deleteById(id);
            return new ApiResponse<>("Category deleted", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Category not found", Estatus.ERROR, null);
        }
    }

    private void validateCategory(Category category) {
        if (existCategoryByName(category.getName())) {
            throw new ValidationException("Category with the same name already exists");
        }
    }

    private void validateUpdateCategory(int id, CategoryRequestDTO categoryRequestDTO) {
        if (existsCategoryByNameAndIdNot(categoryRequestDTO.getName(), id)) {
            throw new ValidationException("There is already a Category with the same name");
        }
    }

    private boolean existCategoryByName(String name) {
        return categoryRepository.existsByName(name);
    }
    private boolean existsCategoryByNameAndIdNot(String name, int id) {
        return categoryRepository.existsBrandByNameAndIdNot(name, id);
    }

}

