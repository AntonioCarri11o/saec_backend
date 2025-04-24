package com.saec.formtic.model.course;

import com.saec.formtic.model.user.Teacher;
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

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_course")
    private UUID idCourse;

    @Column(name = "course_name", length = 128, nullable = false)
    private String name;

    @Column(name = "course_description", length = 255, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_teacher")
    private Teacher teacher;

    @PrePersist
    private void generateUUID() {
        if(this.idCourse == null) {
            this.idCourse = UUID.randomUUID();
        }
    }
}
