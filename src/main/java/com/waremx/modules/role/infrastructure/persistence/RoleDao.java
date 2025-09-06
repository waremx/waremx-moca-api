package com.waremx.modules.role.infrastructure.persistence;

import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.entities.Role;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpa;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class RoleDao implements RoleRepository {

    @Inject
    RoleJpaRepository roleJpaRepository;

    @Transactional
    @Override
    public Optional<Role> create(Role role) {
        try {
            RoleJpa created = this.roleJpaRepository.saveAndFlush(RoleJpa.fromEntity(role));
            return Optional.ofNullable(created.toEntity());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
