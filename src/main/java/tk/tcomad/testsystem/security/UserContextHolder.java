package tk.tcomad.testsystem.security;

import org.keycloak.representations.AccessToken;

import java.util.Optional;

public class UserContextHolder {

    private static final InheritableThreadLocal<String> threadLocalScope = new InheritableThreadLocal<>();

    public static String getUserId() {
        return threadLocalScope.get();
    }

    public static void setUserId(AccessToken user) {
        String userId = Optional.ofNullable(user)
                .map(AccessToken::getSubject)
                .orElse(null);

        threadLocalScope.set(userId);
    }
}
