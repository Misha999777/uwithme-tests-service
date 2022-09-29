package tk.tcomad.testsystem.model.mapper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.Question;
import tk.tcomad.testsystem.model.domain.TestSession;
import tk.tcomad.testsystem.model.persistence.QuestionDb;
import tk.tcomad.testsystem.model.persistence.TestSessionDb;
import tk.tcomad.testsystem.repository.TestRepository;

@Mapper(config = MapperConfiguration.class)
public abstract class TestSessionMapper {

    @Autowired
    private TestRepository testRepository;

    @Mapping(target = "durationMinutes", ignore = true)
    public abstract TestSession toTestSessionApi(TestSessionDb testSession);

    public abstract TestSessionDb toTestSession(TestSession testSessionApi);

    @AfterMapping
    protected void map(@MappingTarget TestSession target, TestSessionDb domain) {
        final int durationMinutes = testRepository.findById(domain.getTestId())
                                                  .orElseThrow(() -> new NotFoundException("Not found"))
                                                  .getDurationMinutes();

        target.setDurationMinutes(durationMinutes);
    }

    @AfterMapping
    protected void map(@MappingTarget TestSessionDb target, TestSession domain) {
        final Map<Long, QuestionDb> testQuestions = testRepository.findById(domain.getTestId())
                                                                  .orElseThrow(() -> new NotFoundException("Not found"))
                                                                  .getQuestions()
                                                                  .stream()
                                                                  .collect(Collectors.toMap(QuestionDb::getId,
                                                                                            Function.identity()));

        final List<QuestionDb> sessionQuestions = domain.getQuestions()
                                                        .stream()
                                                        .map(Question::getId)
                                                        .map(testQuestions::get)
                                                        .collect(Collectors.toList());
        target.setQuestions(sessionQuestions);
    }
}
