package com.example.bancopicpay.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String INFO_TITLE = "Banco PicPay - Rest API";
    private static final String INFO_DESCRIPTION = "Atividade de realização de uma API simulando um banco para PicPay";
    private static final String INFO_VERSION = "v1.0.0";
    private static final String INFO_LICENSE_NAME = "Apache License Version 2.0";
    private static final String INFO_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(INFO_TITLE)
                        .description(INFO_DESCRIPTION)
                        .version(INFO_VERSION)
                        .license(new License()
                                .name(INFO_LICENSE_NAME)
                                .url(INFO_LICENSE_URL)));
    }

}
