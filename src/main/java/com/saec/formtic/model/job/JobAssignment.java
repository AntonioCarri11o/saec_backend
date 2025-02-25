package com.saec.formtic.model.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.user.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_department")
public class JobAssignment {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_assignment")
    private UUID idAssignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_job")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_department")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_employee")
    private Employee employee;

}
