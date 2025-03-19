package com.saec.formtic.repository.role;

import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findFirstByName(RoleName name);
}
