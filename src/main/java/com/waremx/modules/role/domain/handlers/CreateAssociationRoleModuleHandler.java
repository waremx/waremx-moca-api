package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRocketModuleHubRepository;
import com.waremx.modules.role.domain.enums.RoleEvents;
import com.waremx.modules.role.domain.objects.RocketModule;
import com.waremx.modules.role.domain.objects.Role;

import com.waremx.modules.role.domain.objects.RoleRocketModuleHub;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_MODULE_NAME;
import static com.waremx.modules.role.domain.enums.RoleKeys.RESULT;

@AllArgsConstructor
public class CreateAssociationRoleModuleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAssociationRoleModuleHandler.class);

    private RoleRocketModuleHubRepository roleRocketModuleHubRepository;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        LOGGER.info("[HANDLER]: CreateAssociationRoleModuleHandler");

        Role created = context.<Either<MocaErrCodes, Role>>get(RESULT.getKey())
                .map(Either::get)
                .orElseGet(() -> context.<Role>get("role").orElse(null));
        RocketModule module = context.<RocketModule>get("module").orElseThrow();
        String moduleName = context.<String>get(INPUT_MODULE_NAME.getKey()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        LOGGER.info("Role: {}", created);
        LOGGER.info("Module: {}", module);

        //dAY5A0Be3VPgGhj08TxSi

        RoleRocketModuleHub roleRocketModuleHub = RoleRocketModuleHub.builder()
                .hubId(UUID.randomUUID().toString().replace("-", "").substring(0, 21))
                .roleId(created.getRoleId())
                .hubRoleName(created.getName())
                .moduleId(moduleName)
                .createdBy("admin")
                .updatedBy("admin")
                .createdAt(now)
                .updatedAt(now)
                .build();
        Either<MocaErrCodes, RoleRocketModuleHub> associated = this.roleRocketModuleHubRepository.saveAssociation(roleRocketModuleHub, created, module);

        if (associated.isLeft()) {
            LOGGER.error("[ERROR]: Error to create association \"{}\": {} failed", created.getName(), module.getModuleId());
            context.err(associated.getLeft());
            return checkNext(null);
        }

        context.emit(RoleEvents.CREATE_ROLE.getEvent(), Either.right(created));

        return checkNext(context);
    }
}
