package com.waremx.modules.role.application.repositories;

import com.waremx.modules.role.domain.entities.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> create(Role role);
}
