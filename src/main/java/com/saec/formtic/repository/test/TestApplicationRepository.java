package com.saec.formtic.repository.test;

import com.saec.formtic.model.test.TestApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestApplicationRepository extends JpaRepository<TestApplication, UUID> {
}
