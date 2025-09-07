package com.waremx.modules.role.infrastructure.rest.controllers;

import com.waremx.common.application.services.CreateService;
import com.waremx.common.application.services.GetByService;
import com.waremx.common.core.entities.MocaApiResponse;
import com.waremx.common.core.entities.MocaResponseCodes;
import com.waremx.common.core.entities.MocaResponseMapper;
import com.waremx.common.core.errors.MocaErr;
import com.waremx.common.core.errors.MocaErrApiResponse;
import com.waremx.modules.role.infrastructure.rest.dtos.CreateRoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/v1/roles")
public class RoleController {

    @Inject
    private CreateService<CreateRoleDto, RoleDto> createRoleService;

    @Inject
    private GetByService<String, RoleDto> getRoleByNameService;

    @POST()
    @Path("/create")
    @Operation(summary = "Create new role")
    @APIResponse(
            responseCode = "201",
            description = "Operation completed successfully."
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format. Please check the request body",
            content = @Content(
                    schema = @Schema(implementation = MocaErr.class),
                    examples = {
                            @ExampleObject(value = MocaErrApiResponse.ALREADY_EXISTS),
                    }
            )
    )
    @APIResponse(
            responseCode = "500",
            description = "Error to create",
            content = @Content(
                    schema = @Schema(implementation = MocaErr.class),
                    examples = {
                            @ExampleObject(value = MocaErrApiResponse.ERROR_TO_CREATE),
                    }
            )
    )
    @RequestBody(
            description = "Role to create",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Role Example",
                                    value = MocaApiResponse.INSERT
                            )
                    }
            )
    )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid CreateRoleDto createRoleDto) {
        return this.createRoleService.create(createRoleDto)
                .map(roleDto -> MocaResponseMapper.toResponse(MocaResponseCodes.CREATE_ROLE, roleDto))
                .getOrElseGet(mocaErrCodes -> MocaResponseMapper.toErr(mocaErrCodes, "/v1/roles/create"));
    }

    @GET
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name) {
        return this.getRoleByNameService.getByService(name)
                .map(roleDto -> MocaResponseMapper.toResponse(MocaResponseCodes.CREATE_ROLE, roleDto))
                .getOrElseGet(mocaErrCodes -> MocaResponseMapper.toErr(mocaErrCodes, "/v1/roles/" + name));
    }
}
