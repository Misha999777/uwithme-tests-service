package tk.tcomad.testsystem.model.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.persistence.QuestionDb;
import tk.tcomad.testsystem.model.persistence.TestDb;
import tk.tcomad.testsystem.model.persistence.TestSessionDb;
import tk.tcomad.testsystem.repository.QuestionRepository;
import tk.tcomad.testsystem.repository.TestSessionRepository;

import java.util.List;

@Mapper(config = MapperConfiguration.class,
        uses = {QuestionMapper.class, TestSessionMapper.class})
public abstract class TestMapper {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestSessionRepository testSessionRepository;

    public abstract Test toDomain(TestDb test);

    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "results", ignore = true)
    public abstract TestDb toDb(Test testApi);

    @AfterMapping
    protected void map(@MappingTarget TestDb target, Test domain) {
        List<QuestionDb> questions = questionRepository.findAllByTestId(domain.getId());
        List<TestSessionDb> results = testSessionRepository.findAllByTestId(domain.getId());

        target.setQuestions(questions);
        target.setResults(results);
    }
}
