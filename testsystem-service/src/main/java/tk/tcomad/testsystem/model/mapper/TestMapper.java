package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.persistence.TestDb;

@Mapper(config = MapperConfiguration.class,
        uses = {QuestionMapper.class, TestSessionMapper.class})
public interface TestMapper {

    Test toDomain(TestDb test);

    TestDb toDb(Test testApi);
}
