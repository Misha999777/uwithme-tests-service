package education.uwithme.testsystem.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import education.uwithme.testsystem.config.MapperConfiguration;
import education.uwithme.testsystem.model.domain.Question;
import education.uwithme.testsystem.model.persistence.QuestionDb;

@Mapper(config = MapperConfiguration.class)
public interface QuestionMapper {

    @Mapping(expression = "java(question.getAnswers().stream().filter(Answer::isCorrect).count() > 1)",
            target = "multipleChoice")
    Question toDomain(QuestionDb question);

    QuestionDb toDb(Question questionApi);
}
