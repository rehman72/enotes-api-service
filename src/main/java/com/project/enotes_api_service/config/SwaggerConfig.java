package com.project.enotes_api_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        OpenAPI openAPI = new OpenAPI();
//        Information Configuration
        Info info=new   Info();
        info.title("Enotes API");
        info.version("1.0");
        info.description("Enotes API Service");
        info.contact(new Contact().email("abdulrahmanpatni@gmail.com").name("AbdulRahman Patni").url("https://github.com/abdulrahmanpatni"));
        info.setLicense(new License().name("Enotes 1.0").url("https://github.com/abdulrahmanpatni/Enotes_API_Service"));
        ExternalDocumentation externalDocumentation=new ExternalDocumentation();
        externalDocumentation.description("Enotes Documentation");
//        ----------------------------------------------------------------------------------------------
//      Server Configuration
        externalDocumentation.url("https://github.com/abdulrahmanpatni/Enotes_API_Service/blob/main/ENotes-doc/Enotes-doc.md");
        List<Server> servers = List.of(new Server().description("Dev").url("http://localhost:8080"),
                new Server().description("test").url("http://localhost:8081"),
                new Server().description("prod").url("http://localhost:8082"));
//        ------------------------------------------------------------------------------------------------------
//        Security Requirements
        SecurityScheme scheme=new SecurityScheme()
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER);
//        ----------------------------------------------------------------------------------------------
        openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));
        Components components=new Components()
                .addSecuritySchemes("Token",scheme);
        openAPI.info(info);
        openAPI.components(components);
        openAPI.servers(servers);
        openAPI.externalDocs(externalDocumentation);
        return openAPI;
    }


}
