package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.UpdateService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.contexts.RoleContext;
import com.waremx.modules.role.domain.handlers.FilterInputRoleHandler;
import com.waremx.modules.role.domain.handlers.GetRoleByNameHandler;
import com.waremx.modules.role.domain.handlers.UpdateRoleHandler;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.UpdateRoleDto;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.waremx.modules.role.domain.enums.RoleEvents.UPDATE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_ROLE_NAME;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_UPDATE_ROLE_DTO;

@ApplicationScoped
public class UpdateRole implements UpdateService<String, UpdateRoleDto, RoleDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRole.class);

    @Inject
    private RoleRepository roleRepository;

    @Override
    public Either<MocaErrCodes, RoleDto> update(String id, UpdateRoleDto updateRoleDto) {

        LOGGER.info("[EVENT]: {}", UPDATE_ROLE.getEvent());

        Context<Role, MocaErrCodes> context = new Context<>();
        RoleContext roleContext = new RoleContext();
        context.subscribe(UPDATE_ROLE.getEvent(), roleContext);
        context.set(Prop.bind(INPUT_ROLE_NAME.getKey(), id));
        context.set(Prop.bind(INPUT_UPDATE_ROLE_DTO.getKey(), updateRoleDto));

        Context<Role, MocaErrCodes> last = Handler.link(
                new FilterInputRoleHandler(),
                new GetRoleByNameHandler(roleRepository, UPDATE_ROLE.getEvent()),
                new UpdateRoleHandler(roleRepository)
        )
                .execute(context)
                .build();

        if (Objects.isNull(last)) {
            LOGGER.error("[ERROR]: The request could not be processed. Failed in the adapter. [UpdateRole]");
            context.clear();
            return Either.left(context.err());
        }

        Either<MocaErrCodes, RoleDto> result = roleContext.<Role>get().map(RoleDto::from);
        context.clear();
        return result;
    }
}
