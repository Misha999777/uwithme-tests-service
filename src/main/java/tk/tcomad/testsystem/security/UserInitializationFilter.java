package tk.tcomad.testsystem.security;

import lombok.NonNull;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserInitializationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
        KeycloakSecurityContext session =
                (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        if (session == null) {
            UserContextHolder.setUserId(null);
            filterChain.doFilter(request, response);
            return;
        }
        AccessToken token = session.getToken();
        UserContextHolder.setUserId(token);
        filterChain.doFilter(request, response);
    }
}
