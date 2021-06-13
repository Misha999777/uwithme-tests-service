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

    @Named("getTestId")
    public String getTestId(Long testSessionId) {
        return testSessionRepository.findById(testSessionId)
                .orElseThrow(() -> new NotFoundException("TestSession not found"))
                .getTestId();
    }

    public void saveTestSession(TestSession testSession, TestSessionApi userSession) {
        Map<Long, List<Answer>> answersByQuestionId = testSession.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, Question::getAnswers));

        Set<UserAnswerApi> userAnswers = userSession.getUserAnswers();

        long correctAnswers = userAnswers.stream()
                .filter(userAnswer -> compareAnswers(answersByQuestionId, userAnswer))
                .count();

        Test test = testRepository.findById(testSession.getTestId()).orElseThrow();

        Float score = (float) correctAnswers / test.getQuestionsNumber() * 100;

        TestSession testSessionToSave = testSession.toBuilder()
                .elapsedTime(elapsedTime(userSession.getStartTime(), test.getDurationMinutes()))
                .score(score)
                .userAnswers(userSession.getUserAnswers().stream()
                        .map(userAnswerMapper::toUserAnswer)
                        .collect(Collectors.toList()))
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

    private boolean compareAnswers(Map<Long, List<Answer>> answersByQuestionId, UserAnswerApi userAnswer) {
        List<String> userAnswers = userAnswer.getAnswerTexts();
        List<Answer> answers = answersByQuestionId.get(userAnswer.getQuestionId());
        List<String> stringAnswers = answers.stream().map(Answer::getText).collect(Collectors.toList());

        return stringAnswers.containsAll(userAnswer.getAnswerTexts()) && stringAnswers.size() == userAnswers.size();
    }
}
