package education.uwithme.testsystem.model.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import education.uwithme.testsystem.config.MapperConfiguration;
import education.uwithme.testsystem.model.domain.Test;
import education.uwithme.testsystem.model.persistence.QuestionDb;
import education.uwithme.testsystem.model.persistence.TestDb;
import education.uwithme.testsystem.model.persistence.TestSessionDb;
import education.uwithme.testsystem.repository.QuestionRepository;
import education.uwithme.testsystem.repository.TestSessionRepository;

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
        List<QuestionDb> questions = questionRepository.findAllByTestIdOrderById(domain.getId());
        List<TestSessionDb> results = testSessionRepository.findAllByTestIdOrderById(domain.getId());

        target.setQuestions(questions);
        target.setResults(results);
    }
}
