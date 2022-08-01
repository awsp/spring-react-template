package com.github.awsp;

import com.github.awsp.config.ApplicationConfiguration;
import com.github.awsp.model.Sample;
import com.github.awsp.model.User;
import com.github.awsp.repo.SampleRepository;
import com.github.awsp.repo.UserRepository;
import com.github.awsp.security.exception.UserAlreadyExistException;
import com.github.awsp.security.payload.request.SignupRequest;
import com.github.awsp.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("dev")
@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final ApplicationConfiguration configuration;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final SampleRepository sampleRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeRootUser();
        if (configuration.isInitializeDummyData()) {
            log.debug("Initialize dummy values");

            // Initialize dummy data
            sampleRepository.save(Sample.builder().title("item 1").build());
            sampleRepository.save(Sample.builder().title("item 2").build());
            sampleRepository.save(Sample.builder().title("item 3").build());
            sampleRepository.save(Sample.builder().title("item 4").build());
        }
    }

    private void initializeRootUser() {
        Optional<User> byId = userRepository.findById(1L);
        if (!byId.isPresent()) {
            try {
                authenticationService.signupUser(SignupRequest.builder()
                        .username("root@localhost")
                        .password("password")
                        .roles(Stream.of("ADMIN").collect(Collectors.toCollection(HashSet::new)))
                        .build());
            } catch (UserAlreadyExistException e) {
                // Ignore
            }
        }
    }
}