package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.api.UserAnswerApi;
import tk.tcomad.testsystem.model.persistence.UserAnswer;

@SuppressWarnings("unused")
@Mapper(config = MapperConfiguration.class)
public interface UserAnswerMapper {

    UserAnswerApi toUserAnswerApi(UserAnswer userAnswer);

    UserAnswer toUserAnswer(UserAnswerApi userAnswerApi);
}