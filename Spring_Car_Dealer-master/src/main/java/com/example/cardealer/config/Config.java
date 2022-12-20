package com.example.cardealer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        // I don't understand why it won't work with lambda...
        modelMapper.addConverter(new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
                return LocalDateTime.parse(mappingContext.getSource(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            }
        });

        modelMapper.addConverter(new Converter<LocalDateTime, String>() {
            @Override
            public String convert(MappingContext<LocalDateTime, String> mappingContext) {
                return mappingContext.getSource().toString();
            }
        });

        return modelMapper;
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder().setPrettyPrinting().create();
    }
}