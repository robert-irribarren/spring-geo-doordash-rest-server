package com.robert.dd.doordashserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**")).build();
    }

    @Value("${info.app.name}")
    private String name;
    @Value("${info.app.description}")
    private String description;
    @Value("${info.app.version}")
    private String version;

    private ApiInfo getApiInfo(){
        ApiInfoBuilder builder = new ApiInfoBuilder();
        return builder.title(name)
                .description(description)
                .version(version)
                .build();
    }
}