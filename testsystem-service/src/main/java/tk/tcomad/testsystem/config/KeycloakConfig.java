package tk.tcomad.testsystem.config;

import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public UsersResource keycloak(@Value("${keycloak.resource}") String clientId,
                                  @Value("${keycloak.credentials.secret}") String clientSecret,
                                  @Value("${keycloak.realm}") String realm,
                                  @Value("${keycloak.auth-server-url}") String serverUrl) {

        var keycloak = KeycloakBuilder.builder()
                                      .serverUrl(serverUrl)
                                      .realm(realm)
                                      .clientId(clientId)
                                      .clientSecret(clientSecret)
                                      .scope("openid")
                                      .grantType("client_credentials")
                                      .build();

        return keycloak.realm(realm).users();
    }
}
