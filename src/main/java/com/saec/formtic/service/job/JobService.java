package com.saec.formtic.service.job;

import com.saec.formtic.controller.job.jobDTO.UpdateCreateJobDTO;
import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.job.Job;
import com.saec.formtic.repository.job.JobRepository;
import com.saec.formtic.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public ResponseEntity<CustomResponse<List<Job>>> getAll() {
        try {
            List<Job> jobs = jobRepository.findAll();

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, jobs
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error has occurred, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<CustomResponse<Job>> getByID(String id) {
        try {
            Job job = this.jobRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new NoSuchElementException("Job not found"));

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "OK", false, job
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

    public ResponseEntity<CustomResponse<Job>> save(UpdateCreateJobDTO jobDTO) {
        try {
            boolean exist = this.jobRepository.existsByName(jobDTO.getName());
            if (exist) {
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "Job already exists", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            Job department = new Job(null, jobDTO.getName(), jobDTO.getDescription());
            Job savedJob = this.jobRepository.save(department);

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Job successfully created", false, savedJob
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error occurred while creating the department, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse<Job>> update(UpdateCreateJobDTO jobDTO, String id) {
        try {
            Job job = this.jobRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new NoSuchElementException("Job not found"));

            // Verificar si existe otro Job con el mismo nombre
            Optional<Job> existingJob = jobRepository.findByName(jobDTO.getName());
            if (existingJob.isPresent() && !existingJob.get().getIdJob().equals(UUID.fromString(id))) {
                return new ResponseEntity<>(new CustomResponse<>(
                        400, "A job with this name already exists", true, null
                ), HttpStatus.BAD_REQUEST);
            }

            job.setName(jobDTO.getName());
            job.setDescription(jobDTO.getDescription());
            jobRepository.save(job);

            return new ResponseEntity<>(new CustomResponse<>(
                    200, "Job successfully updated", false, job
            ), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    404, e.getMessage(), true, null
            ), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(
                    500, "An error occurred while updating the job, please try again later", true, null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
