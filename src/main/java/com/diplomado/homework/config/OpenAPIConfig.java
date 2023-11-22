package com.diplomado.homework.config;

import java.util.List;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic")
@OpenAPIDefinition(
        security = @SecurityRequirement(name = "basicAuth") // references the name defined in the line 3
)
public class OpenAPIConfig {

    private static final String SCHEME_NAME = "basicAuth";
    private static final String SCHEME = "basic";

    @Value("${com.diplomado.homework.openapi.dev-url}")
    private String devUrl;

    @Value("${com.diplomado.homework.openapi.qa-url}")
    private String qaUrl;

    @Value("${com.diplomado.homework.openapi.staging-url}")
    private String stagingUrl;

    @Value("${com.diplomado.homework.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server qaServer = new Server();
        qaServer.setUrl(qaUrl);
        qaServer.setDescription("Server URL in QA environment");

        Server stagingServer = new Server();
        stagingServer.setUrl(stagingUrl);
        stagingServer.setDescription("Server URL in Staging environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("juan@genso.com.bo");
        contact.setName("J2");

        Info info = new Info()
                .title("Tarea 2 - Modulo3 - Diploma de Desarrollo de Aplicaciones Empresariales")
                .version("0.0.1")
                .contact(contact)
                .description("This API exposes endpoints to user/role management");

        return new OpenAPI()
                .info(info).servers(List.of(devServer, qaServer, stagingServer, prodServer))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, createSecurityScheme()))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList(SCHEME_NAME));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme(SCHEME);
    }


}
