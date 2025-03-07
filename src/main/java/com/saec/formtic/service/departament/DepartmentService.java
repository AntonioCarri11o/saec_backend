package com.saec.formtic.service.departament;

import com.saec.formtic.controller.departament.departmetDTO.UpdateCreateDepartmentDTO;
import com.saec.formtic.repository.department.DepartmentRepository;
import com.saec.formtic.model.department.Department;
import com.saec.formtic.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departamentRepository;

    public ResponseEntity<CustomResponse<List<Department>>> getAll() {
        try {
            List<Department> departments = departamentRepository.findAll();

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, departments
            ), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Department>> getByID(String idDepartment) {
        try {
            Department department = departamentRepository.findById(UUID.fromString(idDepartment))
                    .orElseThrow(() -> new NoSuchElementException("Department not found"));

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, department
            ), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    404, e.getMessage(), true, null
            ), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<CustomResponse<Page<Department>>> getByPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

            Page<Department> departments = departamentRepository.findAll(pageable);
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, departments
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<List<Department>>> getAllByName(String name) {
        try {
            List<Department> departments;

            // Si name está vacío o es null, obtenemos todos los departamentos
            if (name == null || name.trim().isEmpty()) {
                departments = this.departamentRepository.findAll();
            } else {
                departments = this.departamentRepository.getAllByNameContainingIgnoreCase(name);
            }

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, departments
            ), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<CustomResponse<Department>> save(UpdateCreateDepartmentDTO departmentDTO) {
        try {
            boolean exist = this.departamentRepository.existsByName(departmentDTO.getName());
            if (exist) {
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "Department already exists", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            Department saveDepartment = new Department(null, departmentDTO.getName());
            Department savedDepartment = departamentRepository.save(saveDepartment);

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Department successfully created", false, savedDepartment
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error occurred while creating the department, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Department>> update(UpdateCreateDepartmentDTO departmentDTO, String idDepartment) {
        try {
            Department department = departamentRepository.findById(UUID.fromString(idDepartment))
                    .orElseThrow(() -> new NoSuchElementException("Department not found"));


            // Verificar si existe otro departamento con el mismo nombre
            Optional<Department> existingDepartment = departamentRepository.findByName(departmentDTO.getName());
            if (existingDepartment.isPresent() && !existingDepartment.get().getIdDepartment().equals(UUID.fromString(idDepartment))) {
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "A department with this name already exists", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            department.setName(departmentDTO.getName());
            departamentRepository.save(department);
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Department successfully updated", false, department
            ), HttpStatus.OK);


        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    404, e.getMessage(), true, null
            ), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error occurred while updating the department, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
