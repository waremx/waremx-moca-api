package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.CreateService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.infrastructure.rest.dtos.CreateRoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ForStorageRole implements CreateService<CreateRoleDto, RoleDto> {

    @Override
    public Either<MocaErrCodes, RoleDto> create(CreateRoleDto inputDto) {
        return null;
    }
}
