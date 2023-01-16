package es.mockserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration class.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerOpenAPI(@Value("${application.description}") String appDescription, @Value("${application.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title("Mock server API")
                .description(appDescription)
                .version(appVersion)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
