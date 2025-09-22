package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.CreateService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.contexts.RoleContext;
import com.waremx.modules.role.domain.handlers.FilterInputRoleHandler;
import com.waremx.modules.role.domain.handlers.GetRoleByNameHandler;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.domain.handlers.CreateRoleHandler;
import com.waremx.modules.role.infrastructure.rest.dtos.CreateRoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.waremx.modules.role.domain.enums.RoleEvents.CREATE_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_CREATE_ROLE_DTO;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_ROLE_NAME;

@ApplicationScoped
public class ForStorageRole implements CreateService<CreateRoleDto, RoleDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForStorageRole.class);

    @Inject
    private RoleRepository roleRepository;

    @Override
    public Either<MocaErrCodes, RoleDto> create(CreateRoleDto createRoleDto) {

        LOGGER.info("[EVENT]: {}", CREATE_ROLE.getEvent());

        Context<Role, MocaErrCodes> context = new Context<>();
        RoleContext roleContext = new RoleContext();
        context.subscribe(CREATE_ROLE.getEvent(), roleContext);
        context.set(Prop.bind(INPUT_CREATE_ROLE_DTO.getKey(), createRoleDto));
        context.set(Prop.bind(INPUT_ROLE_NAME.getKey(), createRoleDto.getName()));

        Context<Role, MocaErrCodes> last = Handler.link(
                new FilterInputRoleHandler(),
                new GetRoleByNameHandler(roleRepository, CREATE_ROLE.getEvent()),
                new CreateRoleHandler(roleRepository)
        )
                .execute(context)
                .build();

        if (last == null) {
            LOGGER.error("[ERROR]: The request could not be processed. Failed in the adapter. [ForStorageRole]");
            context.clear();
            return Either.left(context.err());
        }

        Either<MocaErrCodes, RoleDto> result = roleContext.<Role>get().map(RoleDto::from);
        context.clear();
        return result;
    }
}
