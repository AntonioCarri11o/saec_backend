package com.saec.formtic.repository.user;

import com.saec.formtic.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<UserInfo, UUID> {
}
