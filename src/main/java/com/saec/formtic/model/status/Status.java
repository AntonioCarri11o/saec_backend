package com.saec.formtic.model.status;

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
@Table(name = "status")
public class Status {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "status_id")
    private UUID idStatus;

    @Column(name = "status_name", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusName name;

    @Column(name = "status_category", length = 24, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCategory category;

    @Column(name = "status_description", length = 255, nullable = false)
    private String description;

    public Status(StatusName name, StatusCategory category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    @PrePersist
    private void generateUUID(){
        if(this.idStatus == null){
            this.idStatus = UUID.randomUUID();
        }
    }
}