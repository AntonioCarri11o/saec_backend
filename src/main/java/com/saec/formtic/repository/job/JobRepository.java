package com.saec.formtic.repository.job;

import com.saec.formtic.model.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    boolean existsByName(String name);

    Optional<Job> findByName(String name);
}
