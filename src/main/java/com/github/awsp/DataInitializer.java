package com.github.awsp;

import com.github.awsp.config.ApplicationConfiguration;
import com.github.awsp.model.Sample;
import com.github.awsp.repo.SampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final ApplicationConfiguration configuration;
    private final SampleRepository sampleRepository;

    @Autowired
    public DataInitializer(ApplicationConfiguration configuration, SampleRepository sampleRepository) {
        this.configuration = configuration;
        this.sampleRepository = sampleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (configuration.isInitializeDummyData()) {
            log.debug("Initialize dummy values");

            // Initialize dummy data
            sampleRepository.save(Sample.builder().title("item 1").build());
            sampleRepository.save(Sample.builder().title("item 2").build());
            sampleRepository.save(Sample.builder().title("item 3").build());
            sampleRepository.save(Sample.builder().title("item 4").build());
        }
    }
}