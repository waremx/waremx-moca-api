package com.waremx.modules.role.infrastructure.persistence;

import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.entities.Role;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpa;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class RoleDao implements RoleRepository {

    @Inject
    RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> create(Role role) {
        return Optional.ofNullable(
                this.roleJpaRepository.saveAndFlush(RoleJpa.fromEntity(role))
                        .toEntity()
        );
    }
}
