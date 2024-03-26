package com.trg.vehicles.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class ApplicationConfig {

    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vehicles API")
                        .version("1.0")
                        .description("API for Vehicles")
                        .contact(new Contact()
                                .email("trantruongac1@gmail.com")
                                .name("Tran Truong"))
                        .license(new License()
                                .name("License of API")));
    }
}
