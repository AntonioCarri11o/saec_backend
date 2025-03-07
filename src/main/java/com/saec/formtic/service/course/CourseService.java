package com.saec.formtic.service.course;

import com.saec.formtic.controller.course.courseDTO.UpdateCreateCourseDTO;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.repository.course.CourseRepository;
import com.saec.formtic.model.user.Teacher;
import com.saec.formtic.repository.user.TeacherRepository;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public ResponseEntity<CustomResponse<List<Course>>> getAllCourses() {
        try {
            List<Course> courses = courseRepository.findAll();
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, courses
            ), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Optional<Course>>> getCourseById(String courseId) {
        try {
            Optional<Course> courses = courseRepository.findById(UUID.fromString(courseId));
            if (courses.isEmpty()) {
                return new ResponseEntity<>(new CustomResponse<>(
                        404, "Course not found", true, null
                ), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, courses
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Page<Course>>> getCoursesByName(String name, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

            // If the name is empty, return all courses; otherwise, filter by name
            // Pagination is always applied
            Page<Course> courses = (name == null || name.trim().isEmpty()) ?
                    courseRepository.findAll(pageable) :
                    courseRepository.findByNameContainingIgnoreCase(name, pageable);

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, courses
            ), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Course>> createCourse(@Valid UpdateCreateCourseDTO courseDTO) {
        try {
            // Check if a course with the same name already exists
            if (courseRepository.existsByName(courseDTO.getName())) {
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "The course already exists", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            // Create the course object
            Course course = new Course();
            course.setName(courseDTO.getName());
            course.setDescription(courseDTO.getDescription());

            // Assign the teacher if they exist
            if (courseDTO.getTeacherId() != null && !courseDTO.getTeacherId().toString().isEmpty()) {
                UUID teacherId = UUID.fromString(courseDTO.getTeacherId());

                if (teacherRepository.existsById(teacherId)) {
                    Teacher teacher = new Teacher();
                    teacher.setIdUserInfo(teacherId);
                    course.setTeacher(teacher);
                }
            }

            // Save the course
            Course savedCourse = courseRepository.save(course);

            return new ResponseEntity<>(new CustomResponse<>(
                    201, "Course successfully created", false, savedCourse
            ), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error occurred while creating the course, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Course>> updateCourse(String courseId, UpdateCreateCourseDTO dto) {
        try {
            Optional<Course> optionalCourse = courseRepository.findById(UUID.fromString(courseId));
            if (optionalCourse.isEmpty()) {
                return new ResponseEntity<>(new CustomResponse<>(
                        404, "Course not found", true, null
                ), HttpStatus.NOT_FOUND);
            }

            Course course = optionalCourse.get();
            course.setName(dto.getName());
            course.setDescription(dto.getDescription());

            // Assign the teacher if they exist
            if (dto.getTeacherId() != null && !dto.getTeacherId().toString().isEmpty()) {
                UUID teacherId = UUID.fromString(dto.getTeacherId());
                if (teacherRepository.existsById(teacherId)) {
                    Teacher teacher = new Teacher();
                    teacher.setIdUserInfo(teacherId);
                    course.setTeacher(teacher);
                }
            } else {
                course.setTeacher(null);
            }

            // Save the changes
            Course updatedCourse = courseRepository.save(course);
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Course successfully updated", false, updatedCourse
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "Error updating the course, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
