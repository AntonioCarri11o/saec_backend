package com.saec.formtic.repository.status;

import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.status.StatusCategory;
import com.saec.formtic.model.status.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByName(StatusName name);
    Optional<Status> findByNameAndCategory(StatusName name, StatusCategory category);
}
