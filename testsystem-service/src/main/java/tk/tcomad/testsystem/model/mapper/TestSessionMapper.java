package tk.tcomad.testsystem.model.mapper;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import tk.tcomad.testsystem.config.MapperConfiguration;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.TestSession;
import tk.tcomad.testsystem.model.persistence.TestSessionDb;
import tk.tcomad.testsystem.repository.TestRepository;
import tk.tcomad.testsystem.security.UserContextHolder;

import static org.apache.logging.log4j.util.Chars.SPACE;

@Mapper(config = MapperConfiguration.class)
public abstract class TestSessionMapper {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UsersResource usersResource;

    @Mapping(target = "durationMinutes", ignore = true)
    @Mapping(target = "userName", ignore = true)
    public abstract TestSession toDomain(TestSessionDb testSession);

    public abstract TestSessionDb toDb(TestSession testSessionApi);

    @AfterMapping
    protected void map(@MappingTarget TestSession.TestSessionBuilder target, TestSessionDb db) {
        final int durationMinutes = testRepository.findById(db.getTestId())
                .orElseThrow(() -> new NotFoundException("Test not found"))
                .getDurationMinutes();

        UserRepresentation keycloakUser = usersResource.get(UserContextHolder.getUserId()).toRepresentation();
        String userName = keycloakUser.getLastName() + SPACE + keycloakUser.getFirstName();

        target.durationMinutes(durationMinutes);
        target.userName(userName);
    }
}
