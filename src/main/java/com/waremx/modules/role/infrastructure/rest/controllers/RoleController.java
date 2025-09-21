package com.waremx.modules.role.infrastructure.rest.controllers;

import com.waremx.common.application.services.CreateService;
import com.waremx.common.application.services.DisableService;
import com.waremx.common.application.services.GetByService;
import com.waremx.common.application.services.UpdateService;
import com.waremx.common.core.entities.MocaApiResponse;
import com.waremx.common.core.entities.MocaResponse;
import com.waremx.common.core.entities.MocaResponseCodes;
import com.waremx.common.core.entities.MocaResponseMapper;
import com.waremx.common.core.errors.MocaErrApiResponse;
import com.waremx.common.core.errors.MocaErrResponse;
import com.waremx.modules.role.infrastructure.rest.dtos.CreateRoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.UpdateRoleDto;
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

    @Inject
    private DisableService<String, RoleDto> disableRoleService;

    @Inject
    private UpdateService<String, UpdateRoleDto, RoleDto> updateRoleService;

    @POST()
    @Path("/create")
    @Operation(summary = "Create new role")
    @APIResponse(
            responseCode = "201",
            description = "Operation completed successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Role Created",
                                    value = MocaApiResponse.CREATE_ROLE_RESPONSE
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format. This value is not permitted",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaErrResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Already Exists",
                                    value = MocaErrApiResponse.ALREADY_EXISTS
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "500",
            description = "Error to create",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaErrResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Error To Create",
                                    value = MocaErrApiResponse.ERROR_TO_CREATE
                            )
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
    @Operation(summary = "Get role by name")
    @APIResponse(
            responseCode = "200",
            description = "Operation completed successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Role Created",
                                    value = MocaApiResponse.ROLE_RESPONSE
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Resource not found.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaErrResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Not Found",
                                    value = MocaErrApiResponse.NOT_FOUND
                            )
                    }
            )
    )
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name) {
        return this.getRoleByNameService.getByService(name)
                .map(roleDto -> MocaResponseMapper.toResponse(MocaResponseCodes.GET_ROLE_BY_NAME, roleDto))
                .getOrElseGet(mocaErrCodes -> MocaResponseMapper.toErr(mocaErrCodes, "/v1/roles/" + name));
    }

    @PATCH
    @Path("/disable/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Disable role by name")
    @APIResponse(
            responseCode = "200",
            description = "Role disabled successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Role Created",
                                    value = MocaApiResponse.ROLE_RESPONSE
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Resource not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaErrResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Not Found",
                                    value = MocaErrApiResponse.NOT_FOUND
                            )
                    }
            )
    )
    public Response disable(@PathParam("name") String name) {
        return this.disableRoleService.disable(name)
                .map(roleDto -> MocaResponseMapper.toResponse(MocaResponseCodes.DISABLE_ROLE_BY_NAME, roleDto))
                .getOrElseGet(mocaErrCodes -> MocaResponseMapper.toErr(mocaErrCodes, "/v1/roles/" + name));
    }

    @PATCH
    @Path("/update/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update role by name")
    @APIResponse(
            responseCode = "200",
            description = "Operation completed successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Role Created",
                                    value = MocaApiResponse.ROLE_RESPONSE
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format. Please check the request body",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaErrResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Error To Update",
                                    value = MocaErrApiResponse.INVALID_FORMAT
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Resource not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaErrResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Not Found",
                                    value = MocaErrApiResponse.NOT_FOUND
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "500",
            description = "Internal server error while updating",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MocaErrResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Error To Update",
                                    value = MocaErrApiResponse.ERROR_TO_UPDATE
                            )
                    }
            )
    )
    @RequestBody(
            description = "Update role request",
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
    public Response updateRole(@PathParam("name") String name, @Valid UpdateRoleDto updateRoleDto) {
        return this.updateRoleService.update(name, updateRoleDto)
                .map(roleDto -> MocaResponseMapper.toResponse(MocaResponseCodes.UPDATE_ROLE, roleDto))
                .getOrElseGet(mocaErrCodes -> MocaResponseMapper.toErr(mocaErrCodes, "/v1/roles/update/" + name));
    }
}
