package com.saec.formtic.service.role;

import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.role.RoleName;
import com.saec.formtic.repository.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(RoleName roleName) throws Exception{
        Optional<Role> roleOptional = roleRepository.findFirstByName(roleName);
        if(roleOptional.isPresent()) {
            return roleOptional.get();
        }
        throw new Exception("Role not found");
    }
}
