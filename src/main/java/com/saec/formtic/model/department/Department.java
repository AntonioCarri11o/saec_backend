package com.saec.formtic.model.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {
    @Id
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_department")
    private UUID idDepartment;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @PrePersist
    private void generateUUID() {
        if (idDepartment == null) {
            this.idDepartment = UUID.randomUUID();
        }
    }
}
