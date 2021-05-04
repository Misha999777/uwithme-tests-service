package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.api.QuestionApi;

@Mapper(config = MapperConfiguration.class,
        uses = {AnswerMapper.class})
public interface QuestionMapper {

    QuestionApi toQuestionApi(Question question);

    Question toQuestion(QuestionApi questionApi);

}
