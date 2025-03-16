package com.saec.formtic.service.user;

import com.saec.formtic.controller.user.employee.employeeDTO.EmployeNameDTO;
import com.saec.formtic.controller.user.employee.employeeDTO.EmployeeDTO;
import com.saec.formtic.controller.user.employee.employeeDTO.UpdateEmployeeDTO;
import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.job.JobAssignment;
import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.role.RoleName;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.status.StatusCategory;
import com.saec.formtic.model.status.StatusName;
import com.saec.formtic.model.user.Employee;
import com.saec.formtic.repository.department.DepartmentRepository;
import com.saec.formtic.repository.job.JobAssignmentRepository;
import com.saec.formtic.repository.role.RoleRepository;
import com.saec.formtic.repository.status.StatusRepository;
import com.saec.formtic.repository.user.EmployeeRepository;
import com.saec.formtic.utils.CustomResponse;
import com.saec.formtic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    JobAssignmentRepository jobAssignmentRepository;


    //Servicio para listar todos los empleados
    public ResponseEntity<CustomResponse<List<Employee>>> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new CustomResponse(200, "Employees list", false, employees));
    }

    //Servicio para listar empleados por departamento y status
    public ResponseEntity<CustomResponse<List<JobAssignment>>> findAllByDepartmentAndEmployeeStatus(String departmentId, String statusName) {
        if(Utils.itsBlankString(departmentId))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "The department id is mandatory", true, null));
        if(Utils.itsBlankString(statusName))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "The status is mandatory", true, null));
        statusName = statusName.trim().toUpperCase();
        try{
            StatusName.valueOf(statusName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "The status is not valid", true, null));
        }

        Optional<Status> statusOptional = statusRepository.findByNameAndCategory(StatusName.valueOf(statusName), StatusCategory.USER);
        Optional<Department> departmentOptional;

        try {
            departmentOptional = departmentRepository.findById(UUID.fromString(departmentId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "The department id is not valid", true, null));
        }

        if(statusOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "Status not found", true, null));
        if(departmentOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "Department not found", true, null));

        Department department = departmentOptional.get();
        Status status = statusOptional.get();
        List<JobAssignment> jobAssignments = jobAssignmentRepository.findJobAssignmentByDepartmentAndEmployeeStatus(department, status);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(200, "Employees list", false, jobAssignments));
    }


    //Servicio para listar empleados solo por status
    public ResponseEntity<CustomResponse<List<Employee>>> findAllByStatus(String statusName) {
        StatusName statusNameEnum;
        if (Utils.itsBlankString(statusName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse<>(400, "Status is mandatory", true, null));
        }
        try {
            statusNameEnum = StatusName.valueOf(statusName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "Status is not valid", true, null));
        }
        Optional<Status> statusOptional = statusRepository.findByNameAndCategory(statusNameEnum, StatusCategory.USER);
        if(statusOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "Status not found", true, null));
        }
        List<Employee> employeeList = employeeRepository.findByStatus(statusOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(200, "Employees list", false, employeeList));
    }

    //Servicio para filtrar usuarios por nombre
    public ResponseEntity<CustomResponse<List<Employee>>> getEmployeesByName(EmployeNameDTO employeNameDTO) {
        List<Employee> employeeList = employeeRepository.findByFullnameContainingIgnoreCase(employeNameDTO.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(200, "Employees list", false, employeeList));
    }

    // Servicio para registrar un nuevo usuario de tipo empleado
    @Transactional
    public ResponseEntity<CustomResponse<Employee>> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Optional<Role> roleOptional = roleRepository.findFirstByName(RoleName.EMPLOYEE);
        if(roleOptional.isEmpty())
            return new ResponseEntity<>(new CustomResponse<>(404, "Not role found", true, null), HttpStatus.NOT_FOUND);
        Role employeeRole = roleOptional.get();
        Optional<Employee> employeeOptionalNumber = employeeRepository.findFirstByEmployeeNumber(employeeDTO.getEmployeeNumber());
        if(employeeOptionalNumber.isPresent())
            return new ResponseEntity<>(new CustomResponse<>(409, "The employee number already exists", true, null), HttpStatus.CONFLICT);
        Optional<Employee> employeeOptional = employeeRepository.findFirstByUsername(employeeDTO.getUsername());
        if(employeeOptional.isPresent())
            return new ResponseEntity<>(new CustomResponse<>(409, "The employee username is already in use", true, null), HttpStatus.CONFLICT);
        Employee employee = employeeDTO.createEmployee(employeeRole);
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(201, "The employee was created", false, savedEmployee));
    }

    //Servicio para actualizar la información de un usuario empleado
    @Transactional
    public ResponseEntity<CustomResponse<Employee>> updateEmployee(UpdateEmployeeDTO employeeDTO, String employeeNumber) {
        if (employeeNumber == null || employeeNumber.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "Employee number is mandatory", true,null));
        Optional<Employee> employeeOptional = employeeRepository.findFirstByEmployeeNumber(employeeNumber);
        if (employeeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Employee not found", true, null));

        Employee employee = employeeOptional.get();

        employee.setUsername(employeeDTO.getUsername());
        employee.setFullname(employeeDTO.getName());
        employee.setHireDate(employeeDTO.getHireDate());

        Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(200, "The employee was updated succesfully", false, updatedEmployee));

    }
}
