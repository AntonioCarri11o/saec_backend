package com.saec.formtic.model.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "role")
public class Role {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_role")
    private UUID idRole;

    @Column(name = "name", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Column(name = "description", length = 48, nullable = false)
    private String description;

    public Role(RoleName name, String description) {
        this.name = name;
        this.description = description;
    }

    @PrePersist
    private void generateUUID(){
        if(this.idRole == null) {
            this.idRole = UUID.randomUUID();
        }
    }
}
