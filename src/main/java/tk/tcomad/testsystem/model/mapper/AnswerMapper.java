package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.persistence.Answer;
import tk.tcomad.testsystem.model.api.AnswerApi;

@Mapper(config = MapperConfiguration.class)
public interface AnswerMapper {

        AnswerApi toAnswerApi(Answer answer);

        Answer toAnswer(AnswerApi answerApi);
}
