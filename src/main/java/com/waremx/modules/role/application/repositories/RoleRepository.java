package com.waremx.modules.role.application.repositories;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.domain.objects.Role;

import io.vavr.control.Either;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> create(Role role);
    Optional<Role> findRoleByName(String name);
    Optional<Role> disable(Role role);
    Either<MocaErrCodes, Role> update(String name, Role role);
}
