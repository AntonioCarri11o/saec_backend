package com.saec.formtic.model.job;

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
@Table(name = "job")
public class Job {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_job")
    private UUID idJob;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "description", length = 256, nullable = true)
    private String description;

    @PrePersist
    private void generateUUID() {
        if (idJob == null) {
            idJob = UUID.randomUUID();
        }
    }
}
