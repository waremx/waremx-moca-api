package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;


import static com.waremx.modules.role.domain.enums.RoleEvents.CREATE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleEvents.GET_ROLE_BY;
import static com.waremx.modules.role.domain.enums.RoleEvents.DISABLE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleEvents.ENABLE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleEvents.UPDATE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_ROLE_NAME;

@AllArgsConstructor
public class GetRoleByNameHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetRoleByNameHandler.class);

    private RoleRepository roleRepository;
    private String event;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        if (Objects.isNull(context)) {
            LOGGER.info("The [GetRoleByNameHandler] handler was not executed");
            return checkNext(null);
        }

        String name = context.<String>get(INPUT_ROLE_NAME.getKey()).orElseThrow();

        Optional<Role> found = this.roleRepository.findRoleByName(name);

        if (found.isPresent() && this.event.equals(CREATE_ROLE.getEvent())) {
            LOGGER.error("[ERROR]: This role already exists: {}", name);
            context.err(MocaErrCodes.ROLE_ALREADY_EXISTS);
            context.emit(CREATE_ROLE.getEvent(), Optional.empty());
            return checkNext(null);
        }

        if (found.isPresent() && found.get().getIsActive().equals(Boolean.FALSE)) {
            LOGGER.error("[ERROR]: Resource not available: {}", name);
            context.err(MocaErrCodes.ROLE_NOT_AVAILABLE);
            context.emit(event, Optional.empty());
            return checkNext(null);
        }

        if (found.isEmpty() && isValid(this.event)) {
            LOGGER.error("[ERROR]: Role not found with name: {}", name);
            context.err(MocaErrCodes.ROLE_NOT_FOUND);
            context.emit(event, Optional.empty());
            return checkNext(null);
        }

        LOGGER.info("Role found \"{}\"", found);
        context.emit(event, found);
        return checkNext(context);
    }

    private boolean isValid(String event) {
        return event.equals(GET_ROLE_BY.getEvent()) || event.equals(DISABLE_ROLE.getEvent()) ||
               event.equals(UPDATE_ROLE.getEvent()) || event.equals(ENABLE_ROLE.getEvent());
    }
}
