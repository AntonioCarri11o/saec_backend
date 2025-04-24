package com.saec.formtic.repository.test;

import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.test.MasterTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<MasterTest, UUID> {
    List<MasterTest> findByCourse(Course course);
    Optional<MasterTest> findFirstByStatus(Status status);
}
