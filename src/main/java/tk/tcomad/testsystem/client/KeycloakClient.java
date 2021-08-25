package tk.tcomad.testsystem.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.tcomad.testsystem.dto.KeycloakUserApi;

@FeignClient(name = "KeycloakClient",
        url = "${keycloak.auth-server-url}/admin/realms/${keycloak.realm}",
        configuration = FeignConfig.class)
public interface KeycloakClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    KeycloakUserApi getUser(@PathVariable("userId") String userId);
}
