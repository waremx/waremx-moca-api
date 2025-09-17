package com.waremx.modules.role.application.repositories;

import com.waremx.modules.role.domain.objects.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> create(Role role);
    Optional<Role> findRoleByName(String name);
    Optional<Role> disable(Role role);
    Optional<Role> update(String name, Role role);
}
