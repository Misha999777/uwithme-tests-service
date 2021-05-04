package tk.tcomad.testsystem.model.api;

import lombok.Value;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.persistence.TestSession;

import java.util.Set;

@Value
public class TestApi {

    String id;
    String name;
    String authorId;
    Integer durationMinutes;
    Integer questionsNumber;
    Set<Question> questions;
    Set<TestSession> testSessions;
}
