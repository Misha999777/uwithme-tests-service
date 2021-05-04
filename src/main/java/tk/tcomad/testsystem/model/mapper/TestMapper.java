package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.persistence.Test;
import tk.tcomad.testsystem.model.api.TestApi;

@Mapper(config = MapperConfiguration.class,
        uses = {TestSessionMapper.class,
                QuestionMapper.class})
public interface TestMapper {

    TestApi toTestApi(Test test);

    Test toTest(TestApi testApi);
}
