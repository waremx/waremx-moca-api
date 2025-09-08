package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.common.core.patterns.Handler;
import com.waremx.modules.role.infrastructure.rest.dtos.CreateRoleDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.waremx.modules.role.domain.enums.RoleEvents.CREATE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_CREATE_ROLE_DTO;

@AllArgsConstructor
public class CreateRoleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateRoleHandler.class);

    private final RoleRepository roleRepository;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        if (Objects.isNull(context)) {
            LOGGER.info("The [CreateRoleHandler] handler was not executed");
            return checkNext(null);
        }

        CreateRoleDto createRoleDto = context.<CreateRoleDto>get(INPUT_CREATE_ROLE_DTO.getKey()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        //TODO: Add the user who creates the context information role when authentication is available.
        Role newRole = Role.builder()
                .name(createRoleDto.getName())
                .displayName(createRoleDto.getDisplayName())
                .createdBy("admin")
                .updatedBy("admin")
                .createdAt(now)
                .updatedAt(now)
                .isActive(true)
                .isProtected(createRoleDto.getIsProtected())
                .build();

        Optional<Role> created = this.roleRepository.create(newRole);

        if (created.isEmpty()) {
            LOGGER.error("[ERROR]: Error to create role \"{}\" failed", createRoleDto);
            context.err(MocaErrCodes.ROLE_ERROR_TO_CREATE);
            context.emit(CREATE_ROLE.getEvent(), Optional.empty());
            return checkNext(null);
        }

        LOGGER.info("[SUCCESS]: Role \"{}\" created", createRoleDto);
        context.emit(CREATE_ROLE.getEvent(), created);
        return checkNext(context);
    }
}
