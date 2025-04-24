package com.saec.formtic.model.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.user.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_application")
public class TestApplication {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_test_application")
    private UUID idTestApplication;

    @Column(name = "start_course_date", nullable = false)
    private Date startCourseDate;

    @Column(name = "deadline_date", nullable = false)
    private Date deadlineDate;

    @Column(name = "due_date", nullable = true)
    private Date dueDate;

    @Column(name = "evaluation_date", nullable = true)
    private Date evaluationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_master_test")
    private MasterTest masterTest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_status")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_employee")
    private Employee employee;

    public TestApplication(Date startCourseDate, Date deadlineDate, MasterTest masterTest, Status status, Employee employee) {
        this.startCourseDate = startCourseDate;
        this.deadlineDate = deadlineDate;
        this.masterTest = masterTest;
        this.status = status;
        this.employee = employee;
    }

    @PrePersist
    private void generateUUID() {
        if(this.idTestApplication == null) {
            this.idTestApplication = UUID.randomUUID();
        }
    }
}
