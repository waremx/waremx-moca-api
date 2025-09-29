package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.CreateService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.application.repositories.RocketModuleRepository;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.contexts.RoleContext;
import com.waremx.modules.role.domain.handlers.FilterInputRoleHandler;
import com.waremx.modules.role.domain.handlers.GetModuleHandler;
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
import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_MODULE_NAME;

@ApplicationScoped
public class ForStorageRole implements CreateService<CreateRoleDto, RoleDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForStorageRole.class);

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private RocketModuleRepository rocketModuleRepository;

    @Override
    public Either<MocaErrCodes, RoleDto> create(CreateRoleDto createRoleDto) {

        LOGGER.info("[EVENT]: {}", CREATE_ROLE.getEvent());

        Context<Role, MocaErrCodes> context = new Context<>();
        RoleContext roleContext = new RoleContext();
        context.subscribe(CREATE_ROLE.getEvent(), roleContext);
        context.set(Prop.bind(INPUT_CREATE_ROLE_DTO.getKey(), createRoleDto));
        context.set(Prop.bind(INPUT_ROLE_NAME.getKey(), createRoleDto.getName()));
        context.set(Prop.bind(INPUT_MODULE_NAME.getKey(), createRoleDto.getModule()));

        Context<Role, MocaErrCodes> last = Handler.link(
                new FilterInputRoleHandler(),
                new GetRoleByNameHandler(roleRepository, CREATE_ROLE.getEvent()),
                new GetModuleHandler(rocketModuleRepository),
                //obtener el modulo
                //validar el role y el modulo si estan asociados -> Aquí termina la ejecución de la petición, si el role ya existe y ya esta asociado al modulo
                //Tu role que estas tratando de crear ya esta asociado a un modulo?
                //insertar en la tabla hub si cumple la condicion -> Aquí termina la ejecucion de esta peticion, si el role ya existe, pero no esta asociado al modulo que mandaron por el DTO
                new CreateRoleHandler(roleRepository) // -> Este handler solo se ejecuta si el role no existe en la tabla "roles" y no esta asociado a ningun modulo, es nuevo
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
