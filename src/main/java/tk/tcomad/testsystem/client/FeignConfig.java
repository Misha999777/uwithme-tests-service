package tk.tcomad.testsystem.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;

public class FeignConfig {

    @Bean
    public RequestInterceptor oauth2HttpRequestInterceptor(
            AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager) {
        return new AuthRequestInterceptor(authorizedClientManager);
    }
}
