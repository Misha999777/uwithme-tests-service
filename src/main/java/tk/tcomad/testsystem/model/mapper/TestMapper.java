package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.api.TestApi;
import tk.tcomad.testsystem.model.persistence.Test;

@Mapper(config = MapperConfiguration.class,
        uses = {TestSessionMapper.class,
                QuestionMapper.class})
public interface TestMapper {

    @Mapping(source = "testSessions", target = "testSessions", qualifiedByName = "toTestSessionApi")
    TestApi toTestApi(Test test);

    Test toTestDb(TestApi testApi);
}
