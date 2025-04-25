package com.saec.formtic.controller.test;

import com.saec.formtic.controller.course.courseDTO.UpdateCreateCourseDTO;
import com.saec.formtic.controller.test.testDTO.TestApplicationDTO;
import com.saec.formtic.controller.test.testDTO.TestApplicationEmployeeAnswersDTO;
import com.saec.formtic.controller.test.testDTO.TestDTO;
import com.saec.formtic.controller.test.testDTO.TestQuestionsDTO;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.model.test.MasterTestQuestions;
import com.saec.formtic.model.test.TestApplication;
import com.saec.formtic.model.test.TestApplicationEmployeeAnswers;
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

    //Listar exámenes por curso
    @GetMapping("/course/{id}")
    public ResponseEntity<CustomResponse<List<MasterTest>>> getTestById(@PathVariable String id) {
        return testService.getByCourse(id);
    }

    //Consultar preguntas por id de examen
    @GetMapping("/findTestQuestiosById/{id}")
    public ResponseEntity<CustomResponse<MasterTestQuestions>> getByTestId(@PathVariable String id) {
        return testService.getByMasterTest(id);
    }

    //Listar asignaciones por id de test
    @GetMapping("/testApplication/{testId}")
    public ResponseEntity<CustomResponse<List<TestApplication>>> getTestApplicationsByTestId(@PathVariable String testId) {
        return testService.getApplicationsByMasterTest(testId);
    }

    //Crear una nueva versión de examen para un curso
    @PostMapping("/create")
    public ResponseEntity<CustomResponse<MasterTest>> createTestVersion(@Valid @RequestBody TestDTO testDTO) {
        return testService.createTestVersion(testDTO);
    }

    //Crear las preguntas de un examen
    @PostMapping("/create/question")
    public ResponseEntity<CustomResponse<MasterTestQuestions>> createTestQuestions(@RequestBody @Valid TestQuestionsDTO testQuestionsDTO) {
        return testService.createTestQuestions(testQuestionsDTO);
    }

    //Asignar un examen a una lista de empleados
    @PostMapping("/create/testApplication")
    public ResponseEntity<CustomResponse<List<TestApplication>>> createTestApplication(@RequestBody @Valid TestApplicationDTO testApplicationDTO) {
        return testService.createTestApplication(testApplicationDTO);
    }

    //Registrar respuestas de empleados a una asigncación
    @PostMapping("/answer")
    public ResponseEntity<CustomResponse<TestApplication>> answerTestApplication(@RequestBody @Valid TestApplicationEmployeeAnswersDTO testApplicationEmployeeAnswersDTO) {
        return testService.answerTestApplication(testApplicationEmployeeAnswersDTO);
    }

    //Registrar respuestas evaluadas por el profesor
    @PostMapping("/evaluate")
    public ResponseEntity<CustomResponse<TestApplication>> evaluateTestApplicactionByTeacher(@RequestBody @Valid TestApplicationEmployeeAnswersDTO testApplicationEmployeeAnswersDTO) {
        return testService.evaluateTestApplicactionByTeacher(testApplicationEmployeeAnswersDTO);
    }
}

