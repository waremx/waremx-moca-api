package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRocketModuleHubRepository;
import com.waremx.modules.role.domain.objects.Role;

import com.waremx.modules.role.domain.objects.RoleRocketModuleHub;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_MODULE_NAME;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_ROLE_NAME;

@AllArgsConstructor
public class ValidateRoleAndModuleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateRoleAndModuleHandler.class);

    private RoleRocketModuleHubRepository rocketModuleHubRepository;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        LOGGER.info("[HANDLER]: ValidateRoleAndModuleHandler");

        String roleName = context.<String>get(INPUT_ROLE_NAME.getKey()).orElseThrow();
        String moduleName = context.<String>get(INPUT_MODULE_NAME.getKey()).orElseThrow();

        Either<MocaErrCodes, List<RoleRocketModuleHub>> list = this.rocketModuleHubRepository.listAssociation(roleName, moduleName);

        if (list.isLeft()) {
            LOGGER.error("[ERROR]: Not modules");
            context.err(list.getLeft());
            return checkNext(null);
        }

        boolean isAssociated = list.get().stream().anyMatch(row -> row.getModuleId().equals(moduleName));

        if (isAssociated) {
            LOGGER.error("[ERROR]: This role is already associated with this module");
            context.err(MocaErrCodes.MOD_ALREADY_ASSOCIATED);
            return checkNext(null);
        }

        //Saltar el handler CreateRoleHandler, por que no vamos a insertar nuevamente el role, si el role ya existe solo se debe de crear la asociacion
        return checkNext(context);
    }
}
