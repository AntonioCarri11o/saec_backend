package com.saec.formtic.controller.exam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin({"*"})
public class ExamController {
    @GetMapping("/master-exam")
    public ResponseEntity<String> getMasterExam() {
        return ResponseEntity.status(HttpStatus.OK).body("Hola desde master-exam");
    }
}
