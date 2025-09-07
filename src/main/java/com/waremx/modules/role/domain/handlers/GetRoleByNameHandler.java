package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.enums.RoleEvents;
import com.waremx.modules.role.domain.enums.RoleKeys;
import com.waremx.modules.role.domain.objects.Role;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class GetRoleByNameHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetRoleByNameHandler.class);

    private RoleRepository roleRepository;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        if (Objects.isNull(context)) {
            LOGGER.info("The [GetRoleByNameHandler] handler was not executed");
            return checkNext(null);
        }

        String name = context.<String>get(RoleKeys.IN_ROLE_NAME.getKey()).orElseThrow();

        Optional<Role> found = this.roleRepository.findRoleByName(name);

        if (found.isEmpty()) {
            LOGGER.info("[ERROR]: Role not found with name: {}", name);
            context.err(MocaErrCodes.ROLE_NOT_FOUND);
            context.emit(RoleEvents.EVENT_GET_ROLE_BY.getEvent(), Optional.empty());
            return checkNext(null);
        }

        LOGGER.info("Role found \"{}\"", found);
        context.emit(RoleEvents.EVENT_GET_ROLE_BY.getEvent(), found);
        return checkNext(context);
    }
}
