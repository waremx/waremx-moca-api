package com.waremx.modules.role.application.repositories;

import java.util.List;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.domain.objects.Role;

import io.vavr.control.Either;

public interface RoleRepository {
    Either<MocaErrCodes, Role> create(Role role);
    Either<MocaErrCodes, Role> findRoleByName(String name);
    Either<MocaErrCodes, Role> disable(Role role);
    Either<MocaErrCodes, Role> update(String name, Role role);
    Either<MocaErrCodes, List<Role>> findAll(int limit, int offset);
}
