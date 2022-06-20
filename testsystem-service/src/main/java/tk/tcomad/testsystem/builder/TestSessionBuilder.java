package tk.tcomad.testsystem.builder;

import static org.apache.logging.log4j.util.Chars.SPACE;

import java.time.Instant;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.persistence.TestSession;
import tk.tcomad.testsystem.provider.QuestionProvider;
import tk.tcomad.testsystem.security.UserContextHolder;

@Component
@RequiredArgsConstructor
public class TestSessionBuilder {

    @NonNull
    private final QuestionProvider questionProvider;
    @NonNull
    private final UsersResource usersResource;

    private String testId;

    public TestSessionBuilder withTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public TestSession build() {
        Set<Question> questions = questionProvider.getQuestionsByTestId(testId);
        UserRepresentation keycloakUser = usersResource.get(UserContextHolder.getUserId()).toRepresentation();
        String userName = keycloakUser.getLastName() + SPACE + keycloakUser.getFirstName();

        return TestSession.builder()
                          .testId(testId)
                          .userId(UserContextHolder.getUserId())
                          .userName(userName)
                          .startTime(Instant.now())
                          .questions(questions)
                          .build();
    }
}
