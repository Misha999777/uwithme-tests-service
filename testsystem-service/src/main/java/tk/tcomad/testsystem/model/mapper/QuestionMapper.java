package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.domain.Question;
import tk.tcomad.testsystem.model.persistence.QuestionDb;

@Mapper(config = MapperConfiguration.class)
public interface QuestionMapper {

    Question toQuestionApi(QuestionDb question);

    QuestionDb toQuestionDb(Question questionApi);
}
