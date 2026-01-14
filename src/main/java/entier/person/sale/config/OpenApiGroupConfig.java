package entier.person.sale.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupConfig {

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Tài khoản - Công khai")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("Quản trị")
                .pathsToMatch("/quan-tri/**")
                .build();
    }

}
