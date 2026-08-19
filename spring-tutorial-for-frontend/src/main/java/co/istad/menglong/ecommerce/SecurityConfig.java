package co.istad.menglong.ecommerce;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Bean
    SecurityWebFilterChain apiGateway(ServerHttpSecurity http) {
        http.authorizeExchange(exchange -> exchange
                .pathMatchers("/admin/**").authenticated()
                .anyExchange().permitAll());
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);

        http.oauth2Login(Customizer.withDefaults());
        http.logout(logoutSpec -> logoutSpec.logoutSuccessHandler(oidcLogoutSuccessHandler()));

        return http.build();
    }

    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }
}
