package com.phantom.japanese_dictionary_mvc.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
