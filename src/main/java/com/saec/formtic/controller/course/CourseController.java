package com.saec.formtic.controller.course;


import com.saec.formtic.controller.course.courseDTO.UpdateCreateCourseDTO;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.service.course.CourseService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@CrossOrigin({"*"})
public class CourseController {


    @Autowired
    private CourseService courseService;


    // Endpoint para obtener todos los cursos
    //http://localhost:8080/api/course/all
    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<Course>>> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Endpoint para obtener un corso mediante su UUID
    //http://localhost:8080/api/course/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Optional<Course>>> getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }


    // Endpoint para obtener cursos con paginación y filtrado por nombre
    //http://localhost:8080/api/course/list?name=Java&page=1&size=5     -----por si quieres un numero el elementos en especifico
    //http://localhost:8080/api/course/list?name=Java&page=1            ----por si quieres obtener 10 (esta por defecto que sean 10)
    @GetMapping("/list")
    public ResponseEntity<CustomResponse<Page<Course>>> getCourses(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return courseService.getCoursesByName(name, page, size);
    }

    //para crear el curso
    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Course>> createCourse(@Valid @RequestBody UpdateCreateCourseDTO courseDTO) {
        // Llamada al servicio pasando el DTO
        return courseService.createCourse(courseDTO);
    }

    //para actualizar el curso
    @PutMapping("/{courseId}")
    public ResponseEntity<CustomResponse<Course>> updateCourse(
            @PathVariable String courseId,
            @Valid @RequestBody UpdateCreateCourseDTO dto) {
        return courseService.updateCourse(courseId, dto);
    }

}
