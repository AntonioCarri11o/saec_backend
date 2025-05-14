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
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLTransientException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmployeeService {
    EmployeeRepository employeeRepository;
    RoleRepository roleRepository;
    StatusRepository statusRepository;
    DepartmentRepository departmentRepository;
    JobAssignmentRepository jobAssignmentRepository;
    private PasswordEncoder encoder;

    @Autowired
    EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository, StatusRepository statusRepository, DepartmentRepository departmentRepository, JobAssignmentRepository jobAssignmentRepository, PasswordEncoder encoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.departmentRepository = departmentRepository;
        this.jobAssignmentRepository = jobAssignmentRepository;
        this.encoder = encoder;
    }


    //Servicio para listar todos los empleados
    public ResponseEntity<CustomResponse<List<Employee>>> findAll() {
        List<Employee> employees;
        try {
            employees = employeeRepository.findAll();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "Database error", true, null));
        }
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Any employees founded", true, null));
        }
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
            UUID departmentUUID = UUID.fromString(departmentId);
            departmentOptional = departmentRepository.findById(UUID.fromString(departmentUUID.toString()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "The department id is not valid", true, null));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "Database error", true, null));
        }

        if(statusOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Status not found", true, null));
        if(departmentOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Department not found", true, null));

        Department department = departmentOptional.get();
        Status status = statusOptional.get();

        try {
            List<JobAssignment> jobAssignments = jobAssignmentRepository.findJobAssignmentByDepartmentAndEmployeeStatus(department, status);
            if(jobAssignments.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Any employee founded", true, null));
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(200, "Employees list", false, jobAssignments));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "Database error", true, null));
        }
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

        try {
            Optional<Status> statusOptional = statusRepository.findByNameAndCategory(statusNameEnum, StatusCategory.USER);
            if(statusOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "Status not found", true, null));
            }
            List<Employee> employeeList = employeeRepository.findByStatus(statusOptional.get());
            if(employeeList.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Any employee founded", true, null));
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(200, "Employees list", false, employeeList));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "Database error", true, null));
        }

    }

    //Servicio para filtrar usuarios por nombre
    public ResponseEntity<CustomResponse<List<Employee>>> getEmployeesByName(EmployeNameDTO employeNameDTO) {
        try {
            List<Employee> employeeList = employeeRepository.findByFullnameContainingIgnoreCase(employeNameDTO.getName());
            if(employeeList.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse(404, "Any employee founded", true, null));
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(200, "Employees list", false, employeeList));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "Database error", true, null));
        }

    }

    // Servicio para registrar un nuevo usuario de tipo empleado
    @Transactional
    public ResponseEntity<CustomResponse<Employee>> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
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
            String hashedPassword = encoder.encode(employeeDTO.getPassword());
            employeeDTO.setPassword(hashedPassword);
            Employee employee = employeeDTO.createEmployee(employeeRole);
            Status status = statusRepository.findByNameAndCategory(StatusName.CURRENT, StatusCategory.USER).orElseThrow(() -> new NoSuchElementException("Status not found"));
            employee.setStatus(status);
            Employee savedEmployee = employeeRepository.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(201, "The employee was created", false, savedEmployee));
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new CustomResponse<>(500, "Database error", true, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Servicio para actualizar la información de un usuario empleado
    @Transactional
    public ResponseEntity<CustomResponse<Employee>> updateEmployee(UpdateEmployeeDTO employeeDTO, String employeeNumber) {
        try {
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
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "Database error", true, null));
        }

    }
}
