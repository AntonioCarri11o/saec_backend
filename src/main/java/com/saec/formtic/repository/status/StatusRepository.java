package com.saec.formtic.repository.status;

import com.saec.formtic.model.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {

}
