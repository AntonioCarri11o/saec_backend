package com.saec.formtic.config;

import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.role.RoleName;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.status.StatusCategory;
import com.saec.formtic.model.status.StatusName;
import com.saec.formtic.repository.role.RoleRepository;
import com.saec.formtic.repository.status.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BootConfig {

    private static final Logger logger = LoggerFactory.getLogger(BootConfig.class);

    @Bean
    public ApplicationRunner init(StatusRepository statusRepository, RoleRepository roleRepository) {
        return args -> {
            if(statusRepository.count() == 0) {
                loadStatus(statusRepository);
            }
            if(roleRepository.count() == 0) {
                loadRoles(roleRepository);
            }
        };
    }

    private void loadStatus(StatusRepository statusRepository) {
        statusRepository.saveAll(List.of(
                new Status(
                        StatusName.ENABLED,
                        StatusCategory.USER,
                        "Indicates that user can login and use the application."),
                new Status(
                        StatusName.DISABLED,
                        StatusCategory.USER,
                        "Indicates that the user can not login and use the application."),
                new Status(
                        StatusName.CURRENT,
                        StatusCategory.TEST,
                        "Indicates that the master test is the current version."),
                new Status(
                        StatusName.DEPRECIATED,
                        StatusCategory.TEST,
                        "Indicates that the master test is deprecated."),
                new Status(
                        StatusName.REQUESTED,
                        StatusCategory.USER_REQUEST,
                        "Indicates that some user requested some change in his personal information."),
                new Status(
                        StatusName.RESOLVED,
                        StatusCategory.USER_REQUEST,
                        "Indicates that the user request have been accepted and resolved."),
                new Status(
                        StatusName.DECLINED,
                        StatusCategory.USER_REQUEST,
                        "Indicates that the user request has been declined."),
                new Status(
                        StatusName.ASSIGNED,
                        StatusCategory.TEST_APPLICATION,
                        "Indicates that the test assignation has been assigned to some user, but not completed."),
                new Status(
                        StatusName.COMPLETED,
                        StatusCategory.TEST_APPLICATION,
                        "Indicates that the test application has been completed by the assigned user."),
                new Status(
                        StatusName.EVALUATED,
                        StatusCategory.TEST_APPLICATION,
                        "Indicates that the test application has been evaluated."
                )
        ));
        logger.info("Default status registered");
    }

    private void loadRoles(RoleRepository roleRepository) {
        roleRepository.saveAll(List.of(
                new Role(
                        RoleName.ADMIN,
                        "Administrator user."
                ),
                new Role(
                        RoleName.EMPLOYEE,
                        "Employee user only cans apply the tests."
                ),
                new Role(
                        RoleName.TEACHER,
                        "Teacher user only cans evaluate the tests."
                )
        ));
    }
}
