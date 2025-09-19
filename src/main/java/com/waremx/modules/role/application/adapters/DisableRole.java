package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.DisableService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.contexts.RoleContext;
import com.waremx.modules.role.domain.handlers.FilterInputHandler;
import com.waremx.modules.role.domain.handlers.GetRoleByNameHandler;
import com.waremx.modules.role.domain.handlers.ToDisableRoleHandler;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.waremx.modules.role.domain.enums.RoleEvents.DISABLE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleEvents.GET_ROLE_BY;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_ROLE_NAME;

@ApplicationScoped
public class DisableRole implements DisableService<String, RoleDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisableRole.class);

    @Inject
    private RoleRepository roleRepository;

    @Override
    public Either<MocaErrCodes, RoleDto> disable(String name) {

        LOGGER.info("[EVENT]: {}", DISABLE_ROLE.getEvent());

        Context<Role, MocaErrCodes> context = new Context<>();
        RoleContext roleContext = new RoleContext();
        context.subscribe(DISABLE_ROLE.getEvent(), roleContext);
        context.set(Prop.bind(INPUT_ROLE_NAME.getKey(), name));

        Context<Role, MocaErrCodes> last = Handler.link(
                new FilterInputHandler(DISABLE_ROLE.getEvent()),
                new GetRoleByNameHandler(roleRepository, GET_ROLE_BY.getEvent()),
                new ToDisableRoleHandler(roleRepository, DISABLE_ROLE.getEvent())
        )
                .execute(context)
                .build();

        if (Objects.isNull(last)) {
            LOGGER.error("[ERROR]: The request could not be processed. Failed in the adapter. [DisableRole]");
            context.clear();
            return Either.left(context.err());
        }

        return roleContext.get()
                .<Either<MocaErrCodes, RoleDto>>map(role -> {
                    context.clear();
                    return Either.right(RoleDto.from(role));
                })
                .orElseGet(() -> Either.left(MocaErrCodes.INTERNAL_SERVER_ERROR));
    }
}
