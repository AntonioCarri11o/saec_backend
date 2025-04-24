package com.saec.formtic.model.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.status.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "master_test")
public class MasterTest {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_master_test")
    private UUID idMasterTest;

    @Column(name = "version", length = 16, nullable = false)
    private String version;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_status")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_course")
    private Course course;

    @PrePersist
    private void generateUUID() {
        if(this.idMasterTest == null) {
            this.idMasterTest = UUID.randomUUID();
        }
    }

    public MasterTest(String version, String name, Date expirationDate, Status status, Course course) {
        this.version = version;
        this.name = name;
        this.expirationDate = expirationDate;
        this.status = status;
        this.course = course;
    }
}
