package tk.tcomad.testsystem.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

public class SecurityContextUtils {

    public static String getUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .orElseThrow(() -> new RuntimeException("No authenticated user"));
    }
}
