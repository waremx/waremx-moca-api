package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.CreateService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.contexts.RoleContext;
import com.waremx.modules.role.domain.entities.Role;
import com.waremx.modules.role.domain.handlers.CreateRoleHandler;
import com.waremx.modules.role.infrastructure.rest.dtos.CreateRoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class ForStorageRole implements CreateService<CreateRoleDto, RoleDto> {

    @Inject
    private RoleRepository roleRepository;

    @Override
    public Either<MocaErrCodes, RoleDto> create(CreateRoleDto createRoleDto) {
        Context<Role, MocaErrCodes> context = new Context<>();
        RoleContext roleContext = new RoleContext();
        context.subscribe("create_role", roleContext);
        context.set(Prop.bind("action", "create_role"));
        context.set(Prop.bind("create_role_dto", createRoleDto));

        return Handler.link(
                new CreateRoleHandler(roleRepository)
        )
                .execute(context)
                .<Optional<Role>>build("create_role")
                .get()
                .<Either<MocaErrCodes, RoleDto>>map(role -> Either.right(RoleDto.from(role)))
                .orElseGet(() -> Either.left(MocaErrCodes.ROLE_ERROR_TO_CREATE));

    }
}
