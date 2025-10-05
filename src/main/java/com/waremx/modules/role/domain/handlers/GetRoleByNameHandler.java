package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        LOGGER.info("[HANDLER]: GetRoleByNameHandler");

        String name = context.<String>get(INPUT_ROLE_NAME.getKey()).orElseThrow();

        Either<MocaErrCodes, Role> found = this.roleRepository.findRoleByName(name);

        if (found.isRight() && this.event.equals(CREATE_ROLE.getEvent())) {
            LOGGER.error("[ERROR]: This role already exists: {}", name);
            context.set(Prop.bind("role", found.get()));
            return checkNext(context);
        }

        if (found.isRight() && found.get().getIsActive().equals(Boolean.FALSE)) {
            LOGGER.error("[ERROR]: Resource not available: {}", name);
            context.err(MocaErrCodes.ROLE_NOT_AVAILABLE);
            return checkNext(null);
        }

        if (found.isLeft() && isValid(this.event)) {
            LOGGER.error("[ERROR]: Role not found with name: {}", name);
            context.err(MocaErrCodes.ROLE_NOT_FOUND);
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
