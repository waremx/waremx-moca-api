package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.infrastructure.rest.dtos.UpdateRoleDto;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static com.waremx.modules.role.domain.enums.RoleEvents.UPDATE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.*;

@AllArgsConstructor
public class UpdateRoleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRoleHandler.class);
    private RoleRepository roleRepository;


    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        LOGGER.info("[HANDLER]: UpdateRoleHandler");

        UpdateRoleDto updateRoleDto = context.<UpdateRoleDto>get(INPUT_UPDATE_ROLE_DTO.getKey()).orElseThrow();
        Either<MocaErrCodes, Role> found = context.<Either<MocaErrCodes, Role>>get(RESULT.getKey()).orElseThrow();
        String id = context.<String>get(INPUT_ROLE_NAME.getKey()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        if (updateRoleDto.getName() != null) {
            if (updateRoleDto.getName().isBlank()) {
                LOGGER.error("[ERROR]: The role name could not be empty");
                context.err(MocaErrCodes.ROLE_NOT_BLANK_VALUE);
                return checkNext(null);
            }

            String[] words = updateRoleDto.getName().trim().toUpperCase().split("_");
            if ((words.length == 2 && (words[0].isBlank() || words[0].length() < 3)) || words.length <= 1 || !words[words.length - 1].equals("ROLE")) {
                LOGGER.error("[ERROR]: The value you are trying to update is not a valid value {}", words[0]);
                context.err(MocaErrCodes.ROLE_FORMAT_ERROR);
                return checkNext(null);
            }
        }
        
        if (updateRoleDto.getDisplayName() != null) {
            if (updateRoleDto.getDisplayName().isBlank()) {
                LOGGER.error("[ERROR]: The role display name could not be empty");
                context.err(MocaErrCodes.ROLE_NOT_BLANK_VALUE);
                return checkNext(null);
            }

            
        }
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

        Either<MocaErrCodes, Role> updated = this.roleRepository.update(id, toUpdate);

        if (updated.isLeft()) {
            LOGGER.error("[ERROR]: Error to update role \"{}\" failed", updated);
            context.err(updated.getLeft());
            return checkNext(null);
        }

        LOGGER.info("[SUCCESS]: Role \"{}\" updated", updated);
        context.emit(UPDATE_ROLE.getEvent(), updated);
        return checkNext(context);
    }
}
