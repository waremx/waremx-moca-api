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

@AllArgsConstructor
public class GetRoleByName extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetRoleByName.class);

    private RoleRepository roleRepository;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        if (Objects.isNull(context)) {
            return checkNext(null);
        }

        String name = context.<String>get("role_name").orElseThrow();

        Optional<Role> found = this.roleRepository.findRoleByName(name);

        if (found.isEmpty()) {
            LOGGER.info("[ERROR]: Role not found with name: {}", name);
            context.err(MocaErrCodes.ROLE_NOT_FOUND);
            context.emit("get_role_by", Optional.empty());
            return checkNext(null);
        }

        LOGGER.info("[SUCCESS]: Role \"{}\" created", found);
        context.emit("get_role_by", found);
        return checkNext(context);
    }
}
