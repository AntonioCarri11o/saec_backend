package com.saec.formtic.controller.user.teacher;

import com.saec.formtic.controller.user.teacher.TeacherDTO.TeacherDTO;
import com.saec.formtic.model.user.Teacher;
import com.saec.formtic.service.user.TeacherService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/teacher")
@CrossOrigin({"*"})
public class TeacherController {
    TeacherService teacherService;

    @Autowired
    TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<Teacher>>> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Teacher>> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
        return teacherService.createTeacher(teacherDTO);
    }
}
