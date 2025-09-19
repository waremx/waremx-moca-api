package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.infrastructure.rest.dtos.UpdateRoleDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.waremx.modules.role.domain.enums.RoleEvents.UPDATE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.*;

@AllArgsConstructor
public class UpdateRoleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRoleHandler.class);
    private RoleRepository roleRepository;
    private String event;


    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        if (Objects.isNull(context)) {
            LOGGER.info("The [UpdateRoleHandler] handler was not executed");
            return checkNext(null);
        }

        UpdateRoleDto updateRoleDto = context.<UpdateRoleDto>get(INPUT_UPDATE_ROLE_DTO.getKey()).orElseThrow();
        Optional<Role> found = context.<Optional<Role>>get(RESULT.getKey()).orElseThrow();
        String id = context.<String>get(INPUT_ROLE_NAME.getKey()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        //TODO: Validate if role name is valid
        //TODO: Validate role name not null, not empty
        //TODO: Validate whether the role already exists

        Role toUpdate = Role.builder()
                .name(updateRoleDto.getName() != null ? updateRoleDto.getName() : found.get().getName())
                .displayName(updateRoleDto.getDisplayName() != null ? updateRoleDto.getDisplayName() : found.get().getDisplayName())
                .createdBy(found.get().getCreatedBy())
                .updatedBy(found.get().getUpdatedBy())
                .createdAt(now)
                .updatedAt(now)
                .isActive(true)
                .isProtected(updateRoleDto.getIsProtected() != null ? updateRoleDto.getIsProtected() : found.get().getIsProtected())
                .build();

        Optional<Role> updated = this.roleRepository.update(id, toUpdate);

        if (updated.isEmpty()) {
            LOGGER.error("[ERROR]: Error to update role \"{}\" failed", updated);
            context.err(MocaErrCodes.ROLE_ERROR_TO_UPDATE);
            context.emit(UPDATE_ROLE.getEvent(), Optional.empty());
            return checkNext(null);
        }

        LOGGER.info("[SUCCESS]: Role \"{}\" updated", updated);
        context.emit(UPDATE_ROLE.getEvent(), updated);
        return checkNext(context);
    }
}
