package com.saec.formtic.service.job;

import com.saec.formtic.controller.job.jobDTO.UpdateCreateJobAssignmentDTO;
import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.job.Job;
import com.saec.formtic.model.job.JobAssignment;
import com.saec.formtic.model.user.Employee;
import com.saec.formtic.repository.department.DepartmentRepository;
import com.saec.formtic.repository.job.JobAssignmentRepository;
import com.saec.formtic.repository.job.JobRepository;
import com.saec.formtic.repository.user.EmployeeRepository;
import com.saec.formtic.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class JobAssignmentService {
    @Autowired
    private JobAssignmentRepository jobAssignmentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // Method to save a new job assignment
    public ResponseEntity<CustomResponse<JobAssignment>> saveJobAssignment(UpdateCreateJobAssignmentDTO updateCreateJobAssignmentDTO) {
        try{
            // Retrieve the Job, Department, and Employee entities by their IDs, throwing exceptions if not found
            Job job = jobRepository.findById(UUID.fromString(updateCreateJobAssignmentDTO.getJobID()))
                    .orElseThrow(() -> new NoSuchElementException("Job not found"));
            Department department = departmentRepository.findById(UUID.fromString(updateCreateJobAssignmentDTO.getDepartmentID()))
                    .orElseThrow(() -> new NoSuchElementException("Department not found"));
            Employee employee = employeeRepository.findById(UUID.fromString(updateCreateJobAssignmentDTO.getEmployeeID()))
                    .orElseThrow(() -> new NoSuchElementException("Employee not found"));

            // Check if a job assignment already exists with the same job, department, and employee
            boolean exists = jobAssignmentRepository.existsByJobAndDepartmentAndEmployee(job, department, employee);
            if(exists){
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "A user with that job in that department already exists", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            // Create a new JobAssignment object with the provided job, department, and employee
            JobAssignment jobAssignment = new JobAssignment(null, job, department, employee);
            JobAssignment jobAssignmentSave = jobAssignmentRepository.save(jobAssignment);
            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Job assignment successfully created", false, jobAssignmentSave
            ), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            // If an entity is not found, return a 404 Not Found response with the exception message
            return new ResponseEntity<>(new CustomResponse<>(
                    404, e.getMessage(), true, null
            ), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error occurred while creating the department, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to update an existing job assignment
    public ResponseEntity<CustomResponse<JobAssignment>> updateJobAssignment(String id, UpdateCreateJobAssignmentDTO updateCreateJobAssignmentDTO) {
        try {
            // Retrieve the existing JobAssignment by ID, if not found throw an exception
            JobAssignment existingJobAssignment = jobAssignmentRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new NoSuchElementException("Job assignment not found"));

            // Retrieve the Job, Department, and Employee entities by their IDs, throwing exceptions if not found
            Job job = jobRepository.findById(UUID.fromString(updateCreateJobAssignmentDTO.getJobID()))
                    .orElseThrow(() -> new NoSuchElementException("Job not found"));
            Department department = departmentRepository.findById(UUID.fromString(updateCreateJobAssignmentDTO.getDepartmentID()))
                    .orElseThrow(() -> new NoSuchElementException("Department not found"));
            Employee employee = employeeRepository.findById(UUID.fromString(updateCreateJobAssignmentDTO.getEmployeeID()))
                    .orElseThrow(() -> new NoSuchElementException("Employee not found"));

            // Check if the employee is already assigned to the same department, excluding the current assignment
            boolean employeeAssigned = jobAssignmentRepository.existsByDepartmentAndEmployee(department, employee);
            if (employeeAssigned && !existingJobAssignment.getIdAssignment().equals(UUID.fromString(id))) {
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "This employee is already assigned to this department", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            // Update the existing job assignment with the new job, department, and employee
            existingJobAssignment.setJob(job);
            existingJobAssignment.setDepartment(department);
            existingJobAssignment.setEmployee(employee);

            // Save the updated job assignment
            JobAssignment updatedJobAssignment = jobAssignmentRepository.save(existingJobAssignment);

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Job assignment successfully updated", false, updatedJobAssignment
            ), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    404, e.getMessage(), true, null
            ), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error occurred while updating the job assignment, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

