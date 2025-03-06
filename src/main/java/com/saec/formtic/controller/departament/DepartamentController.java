package com.saec.formtic.controller.departament;

import com.saec.formtic.controller.departament.departmetDTO.UpdateCreateDepartmentDTO;
import com.saec.formtic.model.department.Department;
import com.saec.formtic.service.departament.DepartmentService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/api/department")
@CrossOrigin(origins = "*")
public class DepartamentController {
    @Autowired
    private DepartmentService departamentService;

    // Endpoint para obtener todos los departamentos
    //http://localhost:8080/api/department/all
    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<Department>>> getAllDepartaments() {
        return departamentService.getAll();
    }
    // Endpoint para obtener un departamento mediante su UUID
    //http://localhost:8080/api/department/{id}
    @GetMapping("{id}")
    public ResponseEntity<CustomResponse<Optional<Department>>> getDepartamentById(@PathVariable UUID id) {
        return departamentService.getByID(id);
    }

    // Endpoint para obtener cursos con paginación
    //http://localhost:8080/api/course/list?page=1&size=5
    //http://localhost:8080/api/course/list?page=1
    @GetMapping("/list")
    public ResponseEntity<CustomResponse<Page<Department>>> getDepartamentsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return departamentService.getByPage(page, size);
    }

    //para crear un nuevo departamento
    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Department>> createDepartament(@Valid @RequestBody UpdateCreateDepartmentDTO departmentDTO) {
        return departamentService.save(departmentDTO);
    }


    //para actualizar el departamento
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse<Department>> updateDepartament(@Valid @RequestBody UpdateCreateDepartmentDTO departmentDTO, @PathVariable UUID id) {
        return departamentService.update(departmentDTO, id);
    }





}
