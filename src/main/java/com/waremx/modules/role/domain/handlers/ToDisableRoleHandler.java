package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ToDisableRoleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToDisableRoleHandler.class);

    private RoleRepository roleRepository;
    private String event;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        LOGGER.info("[HANDLER]: ToDisableRoleHandler");

        Either<MocaErrCodes, Role> found = context.result();

        if (found.isLeft()) {
            LOGGER.error("[ERROR]: The role was not found within the context of the request");
            context.err(MocaErrCodes.INTERNAL_SERVER_ERROR);
            return checkNext(null);
        }

        Role disabledRole = Role.builder()
                .name(found.get().getName())
                .updatedBy("ymoyamac") //TODO: Add the user who creates the context information role when authentication is available.
                .updatedAt(LocalDateTime.now())
                .build();

        Either<MocaErrCodes, Role> updated = this.roleRepository.disable(disabledRole);

        if (updated.isLeft()) {
            LOGGER.error("[ERROR]: This role could not be updated: {}", found.get().getName());
            context.err(MocaErrCodes.ROLE_ERROR_TO_UPDATE);
            return checkNext(null);
        }

        LOGGER.info("Role updated \"{}\"", updated);
        context.emit(event, updated);
        return checkNext(context);
    }
}
