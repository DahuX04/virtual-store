package com.qodara.virtual_store.shared.config;

import com.qodara.virtual_store.virtualStoreAPI.application.dto.request.ProductRequestDTO;
import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();

        // Para que no haga "adivinanzas" raras
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Mapeo explícito: no tocar id ni brand
        mm.typeMap(ProductRequestDTO.class, Product.class).addMappings(m -> {
            m.skip(Product::setId);
        });

        return mm;
    }
}
