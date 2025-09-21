package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.waremx.modules.role.domain.enums.RoleEvents.LIST_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_LIMIT;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_OFFSET;

@AllArgsConstructor
public class ListRolesHandler extends Handler<List<Role>, MocaErrCodes> {

    private RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListRolesHandler.class);

    @Override
    public Handler<List<Role>, MocaErrCodes> execute(Context<List<Role>, MocaErrCodes> context) {
        LOGGER.info("[HANDLER]: ListRolesHandler");

        int limit = context.<Integer>get(INPUT_LIMIT.getKey()).get();
        int offset = context.<Integer>get(INPUT_OFFSET.getKey()).get();

        Either<MocaErrCodes, List<Role>> roles = this.roleRepository.findAll(limit, offset);

        if (roles.isLeft()) {
            LOGGER.error("[ERROR]: Resource not available");
            context.err(roles.getLeft());
            return checkNext(null);
        }

        LOGGER.info("Roles counter: {}", roles.get().size());
        context.emit(LIST_ROLE.getEvent(), roles);
        return checkNext(context);
    }
    
}
