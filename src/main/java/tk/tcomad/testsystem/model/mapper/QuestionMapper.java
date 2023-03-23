package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.domain.Question;
import tk.tcomad.testsystem.model.persistence.QuestionDb;

@Mapper(config = MapperConfiguration.class)
public interface QuestionMapper {

    @Mapping(expression = "java(question.getAnswers().stream().filter(Answer::isCorrect).count() > 1)",
            target = "multipleChoice")
    Question toDomain(QuestionDb question);

    QuestionDb toDb(Question questionApi);
}
