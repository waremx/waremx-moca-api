package com.waremx.modules.role.application.repositories;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.domain.objects.RocketModule;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.domain.objects.RoleRocketModuleHub;
import io.vavr.control.Either;

public interface RoleRocketModuleHubRepository {
    Either<MocaErrCodes, RoleRocketModuleHub> saveAssociation(RoleRocketModuleHub roleRocketModuleHub, Role role, RocketModule module);
}
