package com.saec.formtic.service.course;

import com.saec.formtic.controller.course.courseDTO.UpdateCreateCourseDTO;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.course.CourseRepository;
import com.saec.formtic.model.user.Teacher;
import com.saec.formtic.model.user.TeacherRepository;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
                    500, "Ha ocurrido un error, favor de intentarlo mas tarde", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Optional<Course>>> getCourseById(UUID courseId) {
        try {
            Optional<Course> courses = courseRepository.findById(courseId);
            if (courses.isEmpty()) {
                return new ResponseEntity<>(new CustomResponse<>(
                        404, "Curso no encontrado", true, null
                ), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, courses
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "Ha ocurrido un error, favor de intentarlo mas tarde", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Page<Course>>> getCoursesByName(String name, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

            //si el nombre esta vacio te devuelve todos, si no ets vacio aplica el filtro por nombre
            //SIEMPRE SE APLICA EL PAGINADOR
            Page<Course> courses = (name == null || name.trim().isEmpty()) ?
                    courseRepository.findAll(pageable) :
                    courseRepository.findByNameContainingIgnoreCase(name, pageable);

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, courses
            ), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "Ha ocurrido un error, favor de intentarlo más tarde", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<CustomResponse<Course>> createCourse(@Valid UpdateCreateCourseDTO courseDTO) {
        try {


            // Verifica si ya existe un curso con el mismo nombre
            if (courseRepository.existsByName(courseDTO.getName())) {
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "El curso ya existe", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            // Crea el objeto del curso
            Course course = new Course();
            course.setName(courseDTO.getName());
            course.setDescription(courseDTO.getDescription());

            // Asigna el profesor si existe
            if (courseDTO.getTeacherId() != null && !courseDTO.getTeacherId().toString().isEmpty()) {
                UUID teacherId = courseDTO.getTeacherId();  // No es necesario convertir de nuevo
                if (teacherRepository.existsById(teacherId)) {
                    Teacher teacher = new Teacher();
                    teacher.setIdUserInfo(teacherId);
                    course.setTeacher(teacher);
                }
            }

            // Guarda el curso
            Course savedCourse = courseRepository.save(course);

            return new ResponseEntity<>(new CustomResponse<>(
                    201, "Curso creado exitosamente", false, savedCourse
                    ), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "Ha ocurrido un error al crear el curso, favor de intentarlo más tarde", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<CustomResponse<Course>> updateCourse(@Valid UUID courseId, UpdateCreateCourseDTO dto) {
        try {


            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (optionalCourse.isEmpty()) {
                return new ResponseEntity<>(new CustomResponse<>(
                        404, "Curso no encontrado", true, null
                ), HttpStatus.NOT_FOUND);
            }

            Course course = optionalCourse.get();
            course.setName(dto.getName());
            course.setDescription(dto.getDescription());

            // Asigna el profesor si existe
            if (dto.getTeacherId() != null && !dto.getTeacherId().toString().isEmpty()) {
                UUID teacherId = dto.getTeacherId();  // No es necesario convertir de nuevo
                if (teacherRepository.existsById(teacherId)) {
                    Teacher teacher = new Teacher();
                    teacher.setIdUserInfo(teacherId);
                    course.setTeacher(teacher);
                }
            }else {
                course.setTeacher(null);
            }

            // Guardar los cambios
            Course updatedCourse = courseRepository.save(course);
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Curso actualizado exitosamente", false, updatedCourse
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "Error al actualizar el curso, favor de intentarlo más tarde", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
