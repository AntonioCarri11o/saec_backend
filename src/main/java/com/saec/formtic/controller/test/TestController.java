package com.saec.formtic.controller.test;

import com.saec.formtic.controller.course.courseDTO.UpdateCreateCourseDTO;
import com.saec.formtic.controller.test.testDTO.TestDTO;
import com.saec.formtic.controller.test.testDTO.TestQuestionsDTO;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.model.test.MasterTestQuestions;
import com.saec.formtic.service.course.CourseService;
import com.saec.formtic.service.test.TestService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@CrossOrigin({"*"})
public class TestController {
    private TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<CustomResponse<List<MasterTest>>> getTestById(@PathVariable String id) {
        return testService.getByCourse(id);
    }

    @GetMapping("/findTestQuestiosById/{id}")
    public ResponseEntity<CustomResponse<MasterTestQuestions>> getByTestId(@PathVariable String id) {
        return testService.getByMasterTest(id);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<MasterTest>> createCourse(@Valid @RequestBody TestDTO testDTO) {
        return testService.createTestVersion(testDTO);
    }

    @PostMapping("/create/question")
    public ResponseEntity<CustomResponse<MasterTestQuestions>> createTestQuestions(@RequestBody @Valid TestQuestionsDTO testQuestionsDTO) {
        return testService.createTestQuestions(testQuestionsDTO);
    }

}

