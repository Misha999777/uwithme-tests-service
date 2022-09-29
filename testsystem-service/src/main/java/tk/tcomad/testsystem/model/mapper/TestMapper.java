package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.persistence.TestDb;

@Mapper(config = MapperConfiguration.class,
        uses = {TestSessionMapper.class})
public interface TestMapper {

    Test toTestApi(TestDb test);

    TestDb toTestDb(Test testApi);
}
