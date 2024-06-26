package tk.tcomad.testsystem.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.model.domain.Answer;
import tk.tcomad.testsystem.model.domain.Question;
import tk.tcomad.testsystem.model.domain.TestSession;
import tk.tcomad.testsystem.model.mapper.TestSessionMapper;
import tk.tcomad.testsystem.repository.TestSessionRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestSessionService {

    @NonNull
    private final TestSessionRepository testSessionRepository;
    @NonNull
    private final TestSessionMapper testSessionMapper;

    public void removeCorrectAnswers(TestSession testSession) {
        testSession.getQuestionSnapshots()
                .stream()
                .map(Question::getAnswers)
                .flatMap(Collection::stream)
                .forEach(answer -> answer.setCorrect(false));
    }

    public void saveTestSession(TestSession testSession, TestSession userSession) {
        Set<Question> questions = testSession.getQuestionSnapshots();
        Map<Long, Set<String>> userAnswers = userSession.getUserAnswersByQuestionId();

        long correctAnswers = countCorrectAnswers(questions, userAnswers);
        Float score = (float) correctAnswers / questions.size() * 100;

        TestSession testSessionToSave = testSession.toBuilder()
                .elapsedTime(elapsedTime(userSession.getStartTime()))
                .score(score)
                .userAnswersByQuestionId(userAnswers)
                .build();

        testSessionRepository.save(testSessionMapper.toDb(testSessionToSave));
    }

    private Integer elapsedTime(Instant startedTime) {
        Instant now = Instant.now();
        Instant difference = now.minus(startedTime.toEpochMilli(), ChronoUnit.MILLIS);
        int elapsedMinutes = (int) difference.getEpochSecond() / 60;

        return elapsedMinutes > 0 ? elapsedMinutes : 1;
    }

    private long countCorrectAnswers(Set<Question> questions, Map<Long, Set<String>> userAnswersByQuestionId) {
        if (Objects.isNull(userAnswersByQuestionId)) {
            return 0;
        }

        return Optional.ofNullable(questions)
                .orElse(Set.of())
                .stream()
                .filter(question -> compareAnswers(question.getAnswers(),
                        userAnswersByQuestionId.get(question.getId())))
                .count();
    }

    private boolean compareAnswers(List<Answer> questionAnswers, Set<String> userAnswers) {
        Set<String> correctAnswers = Optional.ofNullable(questionAnswers)
                .orElse(List.of())
                .stream()
                .filter(Answer::isCorrect)
                .map(Answer::getText)
                .collect(Collectors.toSet());

        return Objects.equals(correctAnswers, userAnswers);
    }
}
