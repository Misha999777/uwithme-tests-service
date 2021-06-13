package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.api.QuestionApi;
import tk.tcomad.testsystem.model.persistence.Question;

@Mapper(config = MapperConfiguration.class,
        uses = {AnswerMapper.class})
public interface QuestionMapper {

    @Mapping(expression = "java(question.getAnswers().stream().filter(Answer::isCorrect).count() > 1)", target = "isMultipleChoice")
    QuestionApi toQuestionApi(Question question);

    @Named("toStudentQuestionApi")
    @Mapping(expression = "java(question.getAnswers().stream().filter(Answer::isCorrect).count() > 1)", target = "isMultipleChoice")
    @Mapping(source = "answers", target = "answers", qualifiedByName = "toStudentAnswerApis")
    QuestionApi toStudentQuestionApi(Question question);

    Question toQuestionDb(QuestionApi questionApi);

}
