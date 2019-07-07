package com.paulsan.appbank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.paulpsan.appbank.controller"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET,
                                   newArrayList(new ResponseMessageBuilder()
                                                    .code(500)
                                                    .message("500 Server Error")
                                                    .responseModel(new ModelRef("Error"))
                                                    .build(),
                                                new ResponseMessageBuilder()
                                                    .code(403)
                                                    .message("403 Access Denied")
                                                    .build()
                                   )
            ).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "BANK APPLICATION REST API",
            "A bank application API using TDD.",
            "1.0",
            "Terms of service",
            new Contact("Victor Angel Chambi", "www.paulpsan.com", "paulpsan@gmail.com"),
            "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0"
        );
    }
}