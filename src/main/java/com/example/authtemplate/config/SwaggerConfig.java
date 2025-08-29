package com.example.authtemplate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI configuration class.
 *
 * <p>Defines the API documentation settings for the authentication template, including:</p>
 * <ul>
 *   <li>API title and version</li>
 *   <li>Description of the project</li>
 *   <li>License information</li>
 * </ul>
 */
@Configuration
public class SwaggerConfig {

    // Configure a custom OpenAPI specification for the project
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Auth Template API")
                        .version("1.0")
                        .description("API documentation for authentication template (JWT + GitHub OAuth2)")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                );
    }
}
