package com.visenze.happyfamily.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.visenze.happyfamily.controller"))
				.paths(PathSelectors.any()).build()
				.apiInfo(new ApiInfo(
						"Visenze Happy Family Application", "Use to keep your family happy :)", "v1", "", new Contact("Çağlar Ersin",
								"https://github.com/caglarersin-visenze", "caglar.ersin@visenze.com"),
						"", "", Collections.emptyList()));
	}

}
