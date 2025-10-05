package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RocketModuleRepository;
import com.waremx.modules.role.domain.objects.RocketModule;
import com.waremx.modules.role.domain.objects.Role;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_MODULE_NAME;

@AllArgsConstructor
public class GetModuleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetModuleHandler.class);

    private RocketModuleRepository rocketModuleRepository;


    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        LOGGER.info("[HANDLER]: GetModuleHandler");

        String moduleName = context.<String>get(INPUT_MODULE_NAME.getKey()).orElseThrow();

        Either<MocaErrCodes, RocketModule> found = this.rocketModuleRepository.findModuleByName(moduleName);

        if (found.isLeft()) {
            LOGGER.error("[ERROR]: Module not found with name: {}", moduleName);
            context.err(MocaErrCodes.MOD_NOT_FOUND);
            return checkNext(null);
        }

        context.set(Prop.bind("module", found.get()));

        return checkNext(context);
    }
}
