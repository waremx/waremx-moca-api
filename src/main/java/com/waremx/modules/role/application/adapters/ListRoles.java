package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.ListService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.contexts.RoleContext;
import com.waremx.modules.role.domain.handlers.ListRolesHandler;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.waremx.modules.role.domain.enums.RoleEvents.EVENT;
import static com.waremx.modules.role.domain.enums.RoleEvents.LIST_ROLE;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_LIMIT;
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_OFFSET;

@ApplicationScoped
public class ListRoles implements ListService<RoleDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListRoles.class);

    @Inject
    private RoleRepository roleRepository;

    @Override
    public Either<MocaErrCodes, List<RoleDto>> list(int limit, int offset) {

        LOGGER.info("[EVENT]: {}", LIST_ROLE.getEvent());

        if (limit < 0 || offset < 0) {
            return Either.left(MocaErrCodes.ROLE_INVALID_QUERY_PARAMS);
        }

        Context<List<Role>, MocaErrCodes> context = new Context<>();
        RoleContext roleContext = new RoleContext();
        context.subscribe(LIST_ROLE.getEvent(), roleContext);
        context.set(Prop.bind(EVENT.getEvent(), LIST_ROLE.getEvent()));
        context.set(Prop.bind(INPUT_LIMIT.getKey(), limit));
        context.set(Prop.bind(INPUT_OFFSET.getKey(), offset));

        Context<List<Role>, MocaErrCodes> last = Handler.link(
            new ListRolesHandler(roleRepository)
        )
                .execute(context)
                .build();

        if (last == null) {
            LOGGER.error("[ERROR]: The request could not be processed. Failed in the adapter. [ListRoles]");
            context.clear();
            return Either.left(context.err());
        }

        Either<MocaErrCodes, List<RoleDto>> result = roleContext
                .<List<Role>>get()
                .map(list -> list.stream().map(RoleDto::from).toList());

        context.clear();
        return result;

    }
    
}
