package tk.tcomad.testsystem.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.exception.BadRequestException;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.api.TestSessionApi;
import tk.tcomad.testsystem.model.api.UserAnswerApi;
import tk.tcomad.testsystem.model.mapper.UserAnswerMapper;
import tk.tcomad.testsystem.model.persistence.Answer;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.persistence.Test;
import tk.tcomad.testsystem.model.persistence.TestSession;
import tk.tcomad.testsystem.repository.TestRepository;
import tk.tcomad.testsystem.repository.TestSessionRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    private final TestRepository testRepository;
    @NonNull
    private final UserAnswerMapper userAnswerMapper;

    @SuppressWarnings("unused")
    @Named("getTestId")
    public String getTestId(Long testSessionId) {
        return testSessionRepository.findById(testSessionId)
                                    .orElseThrow(() -> new NotFoundException("TestSession not found"))
                                    .getTestId();
    }

    public void saveTestSession(TestSession testSession, TestSessionApi userSession) {
        Test test = testRepository.findById(testSession.getTestId())
                                  .orElseThrow();
        Map<Long, Set<String>> userAnswers = userSession.getUserAnswersByQuestionId();

        long correctAnswers = countCorrectAnswers(testSession.getQuestions(), userAnswers);
        Float score = (float) correctAnswers / test.getQuestionsNumber() * 100;

        TestSession testSessionToSave = testSession.toBuilder()
                                                   .elapsedTime(elapsedTime(userSession.getStartTime(),
                                                                            test.getDurationMinutes()))
                                                   .score(score)
                                                   .userAnswersByQuestionId(userAnswers)
                                                   .build();

        testSessionRepository.save(testSessionToSave);
    }

    private Integer elapsedTime(Instant startedTime, Integer duration) {
        Instant now = Instant.now();

        Instant difference = now.minus(startedTime.toEpochMilli(), ChronoUnit.MILLIS);

        Instant allowedTime = startedTime.plus(duration, ChronoUnit.MINUTES);
        if (allowedTime.isBefore(now)) {
            throw new BadRequestException("");
        }
        return (int) difference.getEpochSecond() / 60;
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
