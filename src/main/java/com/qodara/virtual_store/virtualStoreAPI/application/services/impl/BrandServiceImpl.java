package com.qodara.virtual_store.virtualStoreAPI.application.services.impl;

import com.qodara.virtual_store.shared.exception.ValidationException;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.BrandRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.BrandResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.CategoryResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.dto.response.PageResponseDTO;
import com.qodara.virtual_store.virtualStoreAPI.application.services.BrandService;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Brand;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Category;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    public ApiResponse<BrandResponseDTO> getBrandById(int id){
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            BrandResponseDTO brandResponseDTO = modelMapper.map(brand, BrandResponseDTO.class);
            return new ApiResponse<>("Brand found", Estatus.SUCCESS, brandResponseDTO);
        } else {
            return new ApiResponse<>("Brand not found", Estatus.ERROR, null);
        }
    }

    public ApiResponse<BrandResponseDTO> getBrandByName(String name){
        Optional<Brand> brandOptional = brandRepository.findBrandByName(name);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            BrandResponseDTO brandResponseDTO = modelMapper.map(brand, BrandResponseDTO.class);
            return new ApiResponse<>("Brand found", Estatus.SUCCESS, brandResponseDTO);
        } else {
            return new ApiResponse<>("Brand not found", Estatus.ERROR, null);
        }
    }

    public ApiResponse<List<BrandResponseDTO>> getAllBrands(){
        List<Brand> bandList = (List<Brand>) brandRepository.findAll();
        List<BrandResponseDTO> brandResponseDTOList = bandList.stream()
                .map(brand -> modelMapper.map(brand, BrandResponseDTO.class))
                .toList();
        return new ApiResponse<>("Brands retrieved", Estatus.SUCCESS, brandResponseDTOList);
    }

    public ApiResponse<BrandResponseDTO> createBrand(BrandRequestDTO brandRequestDTO){
        var brand = modelMapper.map(brandRequestDTO, Brand.class);
        validateBrand(brand);
        brandRepository.save(brand);

        var brandResponseDTO = modelMapper.map(brand, BrandResponseDTO.class);
        return new ApiResponse<>("Brand created successfully", Estatus.SUCCESS, brandResponseDTO);
    }

    public ApiResponse<BrandResponseDTO> updateBrand(int id, BrandRequestDTO brandRequestDTO){
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            return new ApiResponse<>("Brand not found", Estatus.ERROR, null);
        }else {
            Brand brand = brandOptional.get();
            modelMapper.map(brandRequestDTO, brand);
            validateUpdateBrand(id, brandRequestDTO);
            brandRepository.save(brand);
            BrandResponseDTO response = modelMapper.map(brand, BrandResponseDTO.class);
            return new ApiResponse<>("Brand updated successfully", Estatus.SUCCESS, response);
        }
    }

    public ApiResponse<Void> deleteBrand(int id){
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            return new ApiResponse<>("Brand not found", Estatus.ERROR, null);
        } else {
            brandRepository.deleteById(id);
            return new ApiResponse<>("Brand deleted successfully", Estatus.SUCCESS, null);
        }
    }

    public ApiResponse<PageResponseDTO<BrandResponseDTO>> getBrandPaged(int page, int size, String sortBy, String sortDir, String q){
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(safePage, safeSize, Sort.by(direction, sortBy));

        Page<Brand> result = (q == null || q.isBlank())
                ? brandRepository.findAll(pageable)
                : brandRepository.search(q.trim(), pageable);

        List<BrandResponseDTO> content = result.getContent().stream()
                .map(p -> modelMapper.map(p, BrandResponseDTO.class))
                .toList();

        PageResponseDTO<BrandResponseDTO> pageDto = new PageResponseDTO<>(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );

        return new ApiResponse<>("Categories found", Estatus.SUCCESS, pageDto);
    }

    private void validateBrand(Brand brand) {
        if (existBrandByName(brand.getName())) {
            throw new ValidationException("Brand with the same name already exists");
        }
    }

    private void validateUpdateBrand(int id, BrandRequestDTO brandRequestDTO) {
        if (existsBrandByNameAndIdNot(brandRequestDTO.getName(), id)) {
            throw new ValidationException("There is already a Brand with the same name");
        }
    }

    private boolean existBrandByName(String name) {
        return brandRepository.existsByName(name);
    }
    private boolean existsBrandByNameAndIdNot(String name, int id) {
        return brandRepository.existsBrandByNameAndIdNot(name, id);
    }
}
