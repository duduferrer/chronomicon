package bh.app.chronomicon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chronomicon - API")
                        .version("v1.0")
                        .description("Documentação da API do Livro Eletrônico do APP-BH")
                        .contact(new Contact().name("Eduardo Ferrer").email("duduferrer7@gmail.com")));
    }
}