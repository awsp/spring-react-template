package com.areamode.project.backend;

import com.areamode.project.repo.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/sample")
@RestController
public class SampleApiController {

    private final SampleRepository sampleRepository;

    @Autowired
    public SampleApiController(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @GetMapping({"", "/"})
    public ResponseEntity index() {
        return ResponseEntity.ok().body(sampleRepository.findAll());
    }
}
