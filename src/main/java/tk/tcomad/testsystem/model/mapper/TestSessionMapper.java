package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.persistence.TestSession;
import tk.tcomad.testsystem.model.api.TestSessionApi;

@Mapper(config = MapperConfiguration.class,
        uses = {UserAnswerMapper.class,
                QuestionMapper.class})
public interface TestSessionMapper {

    //TODO: fix
//    @Mapping(source = "test.durationMinutes", target = "durationMinutes")
//    TestSessionApi toTestSessionApi(TestSession testSession);

    //TODO: fix
//    @Mapping(source = "durationMinutes", target = "test.durationMinutes")
//    @Mapping(target = "checkSum", ignore = true)
//    TestSession toTestSession(TestSessionApi testSessionApi);
}
