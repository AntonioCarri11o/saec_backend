package com.saec.formtic.model.job;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class JobAssignmentId implements Serializable {
    private UUID jobId;
    private UUID departmentId;
    private UUID employeeId;

}
