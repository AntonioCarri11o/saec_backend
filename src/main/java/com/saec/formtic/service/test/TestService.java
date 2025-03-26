package com.saec.formtic.service.test;

import com.saec.formtic.controller.test.testDTO.CreateUpdateTestDTO;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.status.StatusCategory;
import com.saec.formtic.model.status.StatusName;
import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.repository.course.CourseRepository;
import com.saec.formtic.repository.status.StatusRepository;
import com.saec.formtic.repository.test.MasterTestRepository;
import com.saec.formtic.utils.CustomResponse;
import com.saec.formtic.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestService {
    MasterTestRepository masterTestRepository;
    StatusRepository statusRepository;
    CourseRepository courseRepository;

    @Autowired
    TestService(MasterTestRepository masterTestRepository, StatusRepository statusRepository, CourseRepository courseRepository) {
        this.masterTestRepository = masterTestRepository;
        this.statusRepository = statusRepository;
    }

    public ResponseEntity<CustomResponse<MasterTest>> save(CreateUpdateTestDTO testDTO) {
        Optional<Status> statusOptional = statusRepository.findByNameAndCategory(StatusName.NEW, StatusCategory.TEST);
        if (!statusOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "Something wrong with status", true, null));
        }
        Status status = statusOptional.get();

        UUID courseId = UUID.fromString(testDTO.getCourseId());
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Course not found", true, null));
        }
        Course course = courseOptional.get();
        MasterTest masterTest = testDTO.createMasterTest(status, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse<MasterTest>(201, "The test info was saved succesfully", false, masterTestRepository.save(masterTest)));
    }

    public ResponseEntity<CustomResponse<MasterTest>> createVersion(CreateUpdateTestDTO testDTO) {
        String testIdString = testDTO.getId();
        if(Utils.itsBlankString(testIdString)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "The test Id is mandatory", true, null));
        }
        UUID testId;
        try {
            testId = UUID.fromString(testIdString);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "The test Id is invalid", true, null));
        }
        Optional<MasterTest> masterTestOptional = masterTestRepository.findById(testId);
        if (!masterTestOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Test not found", true, null));
        }

        MasterTest masterTest = masterTestOptional.get();
    }
}
