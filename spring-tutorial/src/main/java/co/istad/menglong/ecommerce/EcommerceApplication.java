package co.istad.menglong.ecommerce;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

// Structure project
// 1. Structure by layer
// 2. Structure by features
@SpringBootApplication
@EnableConfigurationProperties
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SecurityScheme(
        name = "keycloak",
        openIdConnectUrl = "http://localhost:9088/realms/istad-oauth2/.well-known/openid-configuration",
        scheme = "bearer",
        type = SecuritySchemeType.OPENIDCONNECT,
        in = SecuritySchemeIn.HEADER
)
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

}
