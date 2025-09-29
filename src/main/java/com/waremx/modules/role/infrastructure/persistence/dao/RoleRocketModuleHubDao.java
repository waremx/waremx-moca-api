package com.waremx.modules.role.infrastructure.persistence.dao;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.application.repositories.RoleRocketModuleHubRepository;
import com.waremx.modules.role.domain.objects.RocketModule;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.domain.objects.RoleRocketModuleHub;
import com.waremx.modules.role.infrastructure.persistence.jpa.RocketModuleJpa;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpa;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleRocketModuleHubJpa;
import com.waremx.modules.role.infrastructure.persistence.repositories.RoleRocketModuleHubJpaRepository;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class RoleRocketModuleHubDao implements RoleRocketModuleHubRepository {

    @Inject
    RoleRocketModuleHubJpaRepository roleRocketModuleHubJpaRepository;

    @Override
    public Either<MocaErrCodes, RoleRocketModuleHub> saveAssociation(RoleRocketModuleHub roleRocketModuleHub, Role role, RocketModule module) {
        try {
            RoleRocketModuleHubJpa jpa = this.roleRocketModuleHubJpaRepository
                    .saveAndFlush(
                            RoleRocketModuleHubJpa.fromEntity(roleRocketModuleHub, RoleJpa.fromEntity(role), RocketModuleJpa.fromEntity(module))
                    );
            return Either.right(jpa.toEntity());
        } catch (Exception e) {
            return Either.left(MocaErrCodes.ROLE_ERROR_TO_CREATE);
        }
    }
}
