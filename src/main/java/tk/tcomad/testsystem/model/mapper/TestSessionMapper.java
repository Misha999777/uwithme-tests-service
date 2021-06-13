package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.api.TestSessionApi;
import tk.tcomad.testsystem.model.persistence.TestSession;
import tk.tcomad.testsystem.service.TestService;
import tk.tcomad.testsystem.service.TestSessionService;

@Mapper(config = MapperConfiguration.class,
        uses = {UserAnswerMapper.class,
                QuestionMapper.class,
                TestService.class,
        TestSessionService.class})
public interface TestSessionMapper {

    @Mapping(source = "questions", target = "questions", qualifiedByName = "toStudentQuestionApi")
    @Mapping(source = "testId", target = "durationMinutes", qualifiedByName = "getTestDuration")
    TestSessionApi toStudentTestSessionApi(TestSession testSession);

    @Named("toTestSessionApi")
    @Mapping(source = "testId", target = "durationMinutes", qualifiedByName = "getTestDuration")
    TestSessionApi toTestSessionApi(TestSession testSession);

    @Mapping(source = "id", target = "testId", qualifiedByName = "getTestId")
    TestSession toTestSession(TestSessionApi testSessionApi);
}
