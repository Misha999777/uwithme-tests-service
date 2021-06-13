package tk.tcomad.testsystem.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.repository.TestRepository;

@Service
@RequiredArgsConstructor
public class TestService {

    @NonNull
    private final TestRepository testRepository;

    @Named("getTestDuration")
    public Integer getTestDuration(String testId) {
        return testRepository.findById(testId).orElseThrow().getDurationMinutes();
    }
}
