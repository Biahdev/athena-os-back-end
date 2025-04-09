package dev.abeatriz.athena_os.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
            .title("Athena OS")
            .version("1.0")
            .description("APIs para gerencias o Athena OS");

        return new OpenAPI().info(info);
    }

}
