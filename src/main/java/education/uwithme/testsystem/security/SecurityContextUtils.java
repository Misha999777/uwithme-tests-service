package education.uwithme.testsystem.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

import static org.apache.logging.log4j.util.Chars.SPACE;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.FAMILY_NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.GIVEN_NAME;

@UtilityClass
public class SecurityContextUtils {

    public static String getUserId() {
        return getJwt().getSubject();
    }

    public static String getUserName() {
        Jwt jwt = getJwt();
        return jwt.getClaimAsString(FAMILY_NAME) + SPACE + jwt.getClaimAsString(GIVEN_NAME);
    }

    private static Jwt getJwt() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(Jwt.class::isInstance)
                .map(Jwt.class::cast)
                .orElseThrow(() -> new RuntimeException("No authenticated user"));
    }
}
