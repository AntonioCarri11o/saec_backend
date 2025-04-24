package com.saec.formtic.service.user;

import com.saec.formtic.controller.user.teacher.TeacherDTO.TeacherDTO;
import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.role.RoleName;
import com.saec.formtic.model.user.Teacher;
import com.saec.formtic.repository.role.RoleRepository;
import com.saec.formtic.repository.user.TeacherRepository;
import com.saec.formtic.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    TeacherRepository teacherRepository;
    RoleRepository roleRepository;
    private PasswordEncoder encoder;

    @Autowired
    TeacherService(TeacherRepository teacherRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<CustomResponse<List<Teacher>>> getAllTeachers() {
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new CustomResponse<>(200, "Teachers list", false, teachers)
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CustomResponse<>(500, "Internal Server Error", true, null)
            );
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse<Teacher>> createTeacher(TeacherDTO teacherDTO) {
        try {
            Optional<Role> roleOptional = roleRepository.findFirstByName(RoleName.TEACHER);
            if(roleOptional.isEmpty())
                return new ResponseEntity<>(new CustomResponse<>(404, "Role not found", false, null), HttpStatus.NOT_FOUND);
            Role role = roleOptional.get();
            Optional<Teacher> teacherOptional = teacherRepository.findFirstByUsername(teacherDTO.getUsername());
            if(teacherOptional.isPresent())
                return new ResponseEntity<>(new CustomResponse<>(409, "The teacher username is already in use", false, null), HttpStatus.CONFLICT);
            String hashedPassword = encoder.encode(teacherDTO.getPassword());
            teacherDTO.setPassword(hashedPassword);
            Teacher teacher = teacherDTO.createTeacher(role);
            Teacher savedTeacher = teacherRepository.save(teacher);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(201, "The teacher has been created succesfully", false, savedTeacher));
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new CustomResponse<>(500, "Database error", false, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
