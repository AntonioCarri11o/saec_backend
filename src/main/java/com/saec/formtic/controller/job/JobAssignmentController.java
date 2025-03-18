package com.saec.formtic.controller.job;

import com.saec.formtic.controller.job.jobDTO.UpdateCreateJobAssignmentDTO;
import com.saec.formtic.model.job.JobAssignment;
import com.saec.formtic.service.job.JobAssignmentService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobAssigment")
@CrossOrigin({"*"})
public class JobAssignmentController {

    @Autowired
    private JobAssignmentService jobAssignmentService;


    @PostMapping("/create")
    public ResponseEntity<CustomResponse<JobAssignment>> save(@Valid @RequestBody UpdateCreateJobAssignmentDTO updateCreateJobAssignmentDTO){
        return  jobAssignmentService.saveJobAssignment(updateCreateJobAssignmentDTO);
    }
    //http://localhost:8080/api/jobAssigment/update/{id}
    //pero en put xd
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse<JobAssignment>>update(@Valid @RequestBody UpdateCreateJobAssignmentDTO updateCreateJobAssignmentDTO, @PathVariable String id){
        return jobAssignmentService.updateJobAssignment(id, updateCreateJobAssignmentDTO);
    }
}
