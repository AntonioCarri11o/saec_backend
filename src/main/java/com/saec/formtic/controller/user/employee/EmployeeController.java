package com.saec.formtic.controller.user.employee;

import com.saec.formtic.controller.user.employee.employeeDTO.EmployeNameDTO;
import com.saec.formtic.controller.user.employee.employeeDTO.EmployeeDTO;
import com.saec.formtic.controller.user.employee.employeeDTO.UpdateEmployeeDTO;
import com.saec.formtic.model.job.JobAssignment;
import com.saec.formtic.model.user.Employee;
import com.saec.formtic.service.user.EmployeeService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/user/employee")
@CrossOrigin({"*"})
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<Employee>>> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/list/status")
    public ResponseEntity<CustomResponse<List<Employee>>> getAllEmployeeByStatus(String statusName) {
        return employeeService.findAllByStatus(statusName);
    }

    //Controlador para filtrar a los empleados por departamento y status
    @GetMapping("/list/statusAndDepartment")
    public ResponseEntity<CustomResponse<List<JobAssignment>>> getEmployeesByStatusAndDepartment(
            @RequestParam(required = false, defaultValue = "ENABLED") String status,
            @RequestParam(required = false) String department
            ) {
        return employeeService.findAllByDepartmentAndEmployeeStatus(department, status);
    }

    //Controlador para filtrar a los empleados por nombre
    @PostMapping("/list/name")
    public ResponseEntity<CustomResponse<List<Employee>>> getEmployeesByName(@Valid @RequestBody EmployeNameDTO employeNameDTO) {
        return employeeService.getEmployeesByName(employeNameDTO);
    }

    //Controlador para crear un nuevo usuario de tipo empleado
    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Employee>> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    //Controlador para actualizar la información del usuario (Nombre, apellidos, fecha de contratación)
    @PutMapping("/update/{employeeNumber}")
    public ResponseEntity<CustomResponse<Employee>> updateEmployee(@PathVariable String employeeNumber, @Valid @RequestBody UpdateEmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(employeeDTO, employeeNumber);
    }

}
