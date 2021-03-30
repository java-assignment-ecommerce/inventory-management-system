package com.cybage.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2

public class InventoryConfig {

//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
//
//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("inventory-service").select()
				.apis(RequestHandlerSelectors.basePackage("com.cybage.inventory")).paths(PathSelectors.any()).build();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}