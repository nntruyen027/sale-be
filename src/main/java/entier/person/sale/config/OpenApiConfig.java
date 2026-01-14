package entier.person.sale.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
        @Value("${swagger.server.url}")
        private String serverUrl;

        // ⭐ Chỉ quét controller trong package này
        @Bean
        public GroupedOpenApi apiControllers() {
                return GroupedOpenApi.builder()
                                .group("Sale-API")
                                .packagesToScan("entier.person.sale.controller")
                                .build();
        }

        // ⭐ Cấu hình OpenAPI + JWT
        @Bean
        public OpenAPI api() {

                return new OpenAPI()
                                .addServersItem(new Server().url(serverUrl))
                                .info(new Info()
                                                .title("Bán hàng API")
                                                .version("1.0.0")
                                                .description("Tài liệu API cho hệ thống Bán hàng")
                                                .contact(new Contact()
                                                                .name("Nguyễn Ngọc Truyện")
                                                                .email("nntruyen027@gmail.com"))
                                                .license(new License().name("Apache 2.0")))

                                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))

                                .components(new Components().addSecuritySchemes(
                                                "BearerAuth",
                                                new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .name("Authorization")
                                                                .in(SecurityScheme.In.HEADER)));
        }
}
