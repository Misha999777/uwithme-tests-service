package education.uwithme.testsystem.model.mapper;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import education.uwithme.testsystem.config.MapperConfiguration;
import education.uwithme.testsystem.exception.NotFoundException;
import education.uwithme.testsystem.model.domain.TestSession;
import education.uwithme.testsystem.model.persistence.TestSessionDb;
import education.uwithme.testsystem.repository.TestRepository;
import education.uwithme.testsystem.repository.TestSessionRepository;

import java.util.Objects;

import static org.apache.logging.log4j.util.Chars.SPACE;

@Mapper(config = MapperConfiguration.class, uses = QuestionMapper.class)
public abstract class TestSessionMapper {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestSessionRepository testSessionRepository;
    @Autowired
    private UsersResource usersResource;

    @Mapping(target = "durationMinutes", ignore = true)
    public abstract TestSession toDomain(TestSessionDb testSession);

    public abstract TestSessionDb toDb(TestSession testSessionApi);

    @AfterMapping
    protected void map(@MappingTarget TestSession.TestSessionBuilder target, TestSessionDb db) {
        final int durationMinutes = testRepository.findById(db.getTestId())
                .orElseThrow(() -> new NotFoundException("Test not found"))
                .getDurationMinutes();

        target.durationMinutes(durationMinutes);

        if (Objects.isNull(db.getUserName())) {
            UserRepresentation keycloakUser = usersResource.get(db.getUserId()).toRepresentation();
            String userName = keycloakUser.getLastName() + SPACE + keycloakUser.getFirstName();

            db.setUserName(userName);
            testSessionRepository.save(db);

            target.userName(userName);
        }
    }
}
