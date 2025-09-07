package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.GetByService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.contexts.RoleContext;
import com.waremx.modules.role.domain.enums.RoleEvents;
import com.waremx.modules.role.domain.enums.RoleKeys;
import com.waremx.modules.role.domain.handlers.FilterInputHandler;
import com.waremx.modules.role.domain.handlers.GetRoleByNameHandler;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class GetRoleBy implements GetByService<String, RoleDto> {

    @Inject
    private RoleRepository roleRepository;

    @Override
    public Either<MocaErrCodes, RoleDto> getByService(String roleName) {
        String event = RoleEvents.EVENT_GET_ROLE_BY.getEvent();
        Context<Role, MocaErrCodes> context = new Context<>();
        RoleContext roleContext = new RoleContext();
        context.subscribe(event, roleContext);
        context.set(Prop.bind(RoleKeys.ACTION.getKey(), event));
        context.set(Prop.bind(RoleKeys.IN_ROLE_NAME.getKey(), roleName));

        Context last = Handler.link(
               new FilterInputHandler(event),
               new GetRoleByNameHandler(roleRepository)
        )
                .execute(context)
                .build();

        if (Objects.isNull(last)) {
            return Either.left(context.err());
        }

        return roleContext.get()
                .<Either<MocaErrCodes, RoleDto>>map(role -> Either.right(RoleDto.from(role)))
                .orElseGet(() -> Either.left(MocaErrCodes.INTERNAL_SERVER_ERROR));

    }
}
