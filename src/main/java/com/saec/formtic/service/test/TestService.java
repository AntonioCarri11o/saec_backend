package com.saec.formtic.service.test;

import com.saec.formtic.controller.test.testDTO.TestApplicationDTO;
import com.saec.formtic.controller.test.testDTO.TestApplicationEmployeeAnswersDTO;
import com.saec.formtic.controller.test.testDTO.TestDTO;
import com.saec.formtic.controller.test.testDTO.TestQuestionsDTO;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.status.StatusCategory;
import com.saec.formtic.model.status.StatusName;
import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.model.test.MasterTestQuestions;
import com.saec.formtic.model.test.TestApplication;
import com.saec.formtic.model.test.TestApplicationEmployeeAnswers;
import com.saec.formtic.model.test.answer.Answer;
import com.saec.formtic.model.test.question.Question;
import com.saec.formtic.model.user.Employee;
import com.saec.formtic.repository.test.TestApplicationEmployeeAnswerRepository;
import com.saec.formtic.repository.test.TestApplicationRepository;
import com.saec.formtic.repository.test.TestQuestionsRepository;
import com.saec.formtic.repository.test.TestRepository;
import com.saec.formtic.repository.course.CourseRepository;
import com.saec.formtic.repository.status.StatusRepository;
import com.saec.formtic.repository.user.EmployeeRepository;
import com.saec.formtic.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.IllegalChannelGroupException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestService {
    private final TestApplicationRepository testApplicationRepository;
    private final CourseRepository courseRepository;
    private final TestRepository testRepository;
    private final StatusRepository statusRepository;
    private final TestQuestionsRepository testQuestionsRepository;
    private final EmployeeRepository employeeRepository;
    private final TestApplicationEmployeeAnswerRepository testApplicationEmployeeAnswerRepository;

    @Autowired
    public TestService(CourseRepository courseRepository, TestRepository testRepository, StatusRepository statusRepository, TestQuestionsRepository testQuestionsRepository, EmployeeRepository employeeRepository, TestApplicationRepository testApplicationRepository, TestApplicationEmployeeAnswerRepository testApplicationEmployeeAnswerRepository) {
        this.courseRepository = courseRepository;
        this.testRepository = testRepository;
        this.statusRepository = statusRepository;
        this.testQuestionsRepository = testQuestionsRepository;
        this.employeeRepository = employeeRepository;
        this.testApplicationRepository = testApplicationRepository;
        this.testApplicationEmployeeAnswerRepository = testApplicationEmployeeAnswerRepository;
    }
    public ResponseEntity<CustomResponse<List<MasterTest>>> getByCourse(String courseId) {
        try {
            Course course = courseRepository.
                    findById(UUID.fromString(courseId)).orElseThrow(()
                    -> new NoSuchElementException("Course not found"));
            List<MasterTest> tests = testRepository.findByCourse(course);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new CustomResponse<>(200, "Tests list", false, tests));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Course id invalid", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, "Course not found", true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    public ResponseEntity<CustomResponse<MasterTestQuestions>> getByMasterTest(String id) {
        try {
            MasterTest masterTest = testRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NoSuchElementException("Master test not found"));
            MasterTestQuestions masterTestQuestions = testQuestionsRepository.findByMasterTest(masterTest).orElseThrow(() -> new NoSuchElementException("Test questions not found"));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new CustomResponse<>(200, "Tests list", false, masterTestQuestions)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Master test not found", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, "Master test not found", true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    public ResponseEntity<CustomResponse<List<TestApplication>>> getApplicationsByMasterTest(String id) {
        try {
            MasterTest masterTest = testRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NoSuchElementException("MasterTest not found"));
            List<TestApplication> testApplications = testApplicationRepository.findByMasterTest(masterTest);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new CustomResponse<>(200, "Tests list", false, testApplications)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Master id invalid", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, "Master test not found", true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse<MasterTest>> createTestVersion(TestDTO testDTO) {
        try {
            Status status = statusRepository.findByNameAndCategory(StatusName.CURRENT, StatusCategory.TEST).orElseThrow(() -> new NoSuchElementException("Status not found"));
            Course course = courseRepository.findById(UUID.fromString(testDTO.getCourseId())).orElseThrow(() -> new NoSuchElementException("Course not found"));
            Optional<MasterTest> depreciatedTestOptional = testRepository.findFirstByStatus(status);

            MasterTest masterTest = testDTO.createMasterTest(status, course);
            MasterTest savedTest = testRepository.save(masterTest);

            if(depreciatedTestOptional.isPresent()) {
                Status depreciatedStatus = statusRepository.findByNameAndCategory(StatusName.DEPRECIATED, StatusCategory.TEST).orElseThrow(() -> new NoSuchElementException("Status not found"));
                MasterTest depreciatedTest = depreciatedTestOptional.get();
                depreciatedTest.setStatus(depreciatedStatus);
                testRepository.save(depreciatedTest);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new CustomResponse<>(201, "Test created", true, savedTest)
            );
        } catch (IllegalChannelGroupException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Id not valid", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, e.getMessage(), true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse<MasterTestQuestions>> createTestQuestions(TestQuestionsDTO testQuestionsDTO) {
        try {
            MasterTest masterTest = testRepository.findById(UUID.fromString(testQuestionsDTO.getMasterTestId())).orElseThrow(() -> new NoSuchElementException("Master test not found"));
            MasterTestQuestions masterTestQuestions = testQuestionsDTO.createMasterTestQuestions(masterTest);
            MasterTestQuestions savedMasterTestQuestions = testQuestionsRepository.save(masterTestQuestions);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new CustomResponse<>(201, "Test created", true, savedMasterTestQuestions)
            );
        } catch (IllegalChannelGroupException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Id not valid", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, e.getMessage(), true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse<List<TestApplication>>> createTestApplication(TestApplicationDTO testApplicationDTO) {
        try {
            MasterTest masterTest = testRepository.findById(UUID.fromString(testApplicationDTO.getMasterTestId())).orElseThrow(() -> new NoSuchElementException("Master test not found"));
            List<UUID> uuids = testApplicationDTO.getEmployeesIDs().stream()
                    .map(UUID::fromString).toList();

            Iterable<Employee> employees = employeeRepository.findAllById(uuids);

            List<TestApplication> testApplications = new ArrayList<>();

            Status status = statusRepository.findByNameAndCategory(StatusName.ASSIGNED, StatusCategory.TEST_APPLICATION).orElseThrow(() -> new NoSuchElementException("Status not found"));

            employees.forEach(employee -> {
                testApplications.add(
                        new TestApplication(
                                testApplicationDTO.getStartCourseDate(),
                                testApplicationDTO.getDeadlineDate(),
                                masterTest,
                                status,
                                employee
                        )
                );
            });
            List<TestApplication> savedApplications = testApplicationRepository.saveAll(testApplications);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new CustomResponse<>(201, "Test created", true, savedApplications)
            );
        } catch (IllegalChannelGroupException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Id not valid", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, e.getMessage(), true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse<TestApplication>> answerTestApplication(TestApplicationEmployeeAnswersDTO testApplicationEmployeeAnswersDTO) {
        try {
            //Obtener la aplicación asignada
            TestApplication testApplication = testApplicationRepository
                    .findById(UUID.fromString(testApplicationEmployeeAnswersDTO
                            .getTestApplicationId()))
                    .orElseThrow(() -> new NoSuchElementException("Test application not found"));

            //Obtener las preguntas del examen
            MasterTestQuestions testQuestions = testQuestionsRepository
                    .findByMasterTest(testApplication.getMasterTest()).orElseThrow(
                            () -> new NoSuchElementException("Master test not found")
                    );

            //Cambiar el status de la asignación a completada
            Status status = statusRepository.findByNameAndCategory(
                    StatusName.COMPLETED, StatusCategory.TEST_APPLICATION
            ).orElseThrow(() -> new NoSuchElementException("Status not found"));
            testApplication.setStatus(status);

            TestApplicationEmployeeAnswers answers =
                    testApplicationEmployeeAnswersDTO
                            .createTestApplicationEmployeeAnswers(testApplication);

            List<Answer> evaluatedAnswers = new ArrayList<>();

            //Evaluar las respuestas automáticamente
            for(int i = 0; i < answers.getAnswers().size(); i++) {
                Answer answer = answers.getAnswers().get(i);
                Question question = testQuestions.getQuestions().get(i);
                Answer evaluatedAnswer = answer;
                evaluatedAnswer = question.evaluate(evaluatedAnswer);
                evaluatedAnswers.add(evaluatedAnswer);
            }

            //Guardar las preguntas como evaluadas y marcar el examen como completado
            answers.setAnswers(evaluatedAnswers);
            testApplicationEmployeeAnswerRepository.save(answers);
            TestApplication testApplicationSaved = testApplicationRepository.save(testApplication);

            //Enviar una respuesta de operación exitosa
            return ResponseEntity.status(HttpStatus.OK).body(
                    new CustomResponse<>(201, "The answers was saved", false, testApplicationSaved)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Master id invalid", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, e.getMessage(), true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse<TestApplication>> evaluateTestApplicactionByTeacher(TestApplicationEmployeeAnswersDTO testApplicationEmployeeAnswersDTO) {
        try {
            TestApplication testApplication = testApplicationRepository
                    .findById(UUID.fromString(
                            testApplicationEmployeeAnswersDTO.getTestApplicationId()))
                    .orElseThrow(() -> new NoSuchElementException("Test application not found"));

            Status status = statusRepository
                    .findByNameAndCategory(
                            StatusName.EVALUATED, StatusCategory.TEST_APPLICATION)
                    .orElseThrow(() -> new NoSuchElementException("Status not found"));
            testApplication.setStatus(status);
            TestApplicationEmployeeAnswers evaluatedAnswers = testApplicationEmployeeAnswersDTO
                    .createTestApplicationEmployeeAnswers(testApplication);
            testApplicationEmployeeAnswerRepository.save(evaluatedAnswers);
            testApplicationRepository.save(testApplication);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CustomResponse<>(201, "Test evaluated", true, testApplication)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomResponse<>(400, "Master id invalid", true, null)
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new CustomResponse<>(404, e.getMessage(), true, null)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }
}
