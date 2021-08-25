package tk.tcomad.testsystem.model.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.api.AnswerApi;
import tk.tcomad.testsystem.model.persistence.Answer;

@Mapper(config = MapperConfiguration.class)
public interface AnswerMapper {

        AnswerApi toAnswerApi(Answer answer);

        @Named("toStudentAnswerApis")
        @IterableMapping(qualifiedByName="toStudentAnswerApi")
        List<AnswerApi> toStudentAnswerApis(List<Answer> answers);

        @Named("toStudentAnswerApi")
        @Mapping(ignore = true, target = "correct")
        AnswerApi toStudentAnswerApi(Answer answer);

        Answer toAnswerDb(AnswerApi answerApi);
}
