package com.sam.virtual_camera_server.rest;

/**
 * @author Sameer Shaikh
 *
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableAutoConfiguration(exclude = {TransactionAutoConfiguration.class})
@ComponentScan("com.sam.virtual_camera_server.rest")
@SpringBootApplication
public class App
{

    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Docket appApi()
    {
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/").select()
            .apis(RequestHandlerSelectors.basePackage("com.sam.virtual_camera_server.rest"))
            .paths(PathSelectors.any()).build().apiInfo(apiInfo());

    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("Virtual Camera Server REST guide for developers")
            .description("RESTful APIs guide for Virtual Camera Server").version("v1").build();
    }
}