package com.saec.formtic.controller.test;

import com.saec.formtic.controller.test.testDTO.CreateUpdateTestDTO;
import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.service.test.TestService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin({"*"})
public class TestController {
    TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/master-exam")
    public ResponseEntity<String> getMasterExam() {
        return ResponseEntity.status(HttpStatus.OK).body("Hola desde master-exam");
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<MasterTest>> create(@Valid @RequestBody CreateUpdateTestDTO testDTO) {
        return testService.save(testDTO);
    }

    @PutMapping("/create-version")
    public ResponseEntity<CustomResponse<MasterTest>> createVersion(@Valid @RequestBody CreateUpdateTestDTO testDTO) {

    }
}

