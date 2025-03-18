package com.saec.formtic.controller.job;

import com.saec.formtic.controller.departament.departmetDTO.UpdateCreateDepartmentDTO;
import com.saec.formtic.controller.job.jobDTO.UpdateCreateJobDTO;
import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.job.Job;
import com.saec.formtic.service.job.JobService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
@CrossOrigin({"*"})
public class JobController {
    @Autowired
    private JobService jobService;

    // Endpoint para obtener todos los departamentos
    //http://localhost:8080/api/job/
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Job>>> getAllDepartaments() {
        return jobService.getAll();
    }

    //http://localhost:8080/api/job/{id}
    @GetMapping("{id}")
    public ResponseEntity<CustomResponse<Job>> getDepartamentById(@PathVariable String id) {
        return jobService.getByID(id);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Job>> createDepartament(@Valid @RequestBody UpdateCreateJobDTO jobDTO) {
        return jobService.save(jobDTO);
    }

    //http://localhost:8080/api/job/update/{id}
    //pero en put xd
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse<Job>> updateDepartament(@Valid @RequestBody UpdateCreateJobDTO jobDTO, @PathVariable String id) {
        return jobService.update(jobDTO, id);
    }



}
