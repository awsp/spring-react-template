package com.github.awsp.backend;

import com.github.awsp.repo.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(sampleRepository.findAll());
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> withRoleUser() {
        return ResponseEntity.ok().body("Hi, User");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> withRoleAdmin() {
        return ResponseEntity.ok().body("Hi, Admin");
    }

    @GetMapping("/any")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> withAnyRole() {
        return ResponseEntity.ok().body("Hi, Admin or User");
    }
}