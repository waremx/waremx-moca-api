package com.waremx.modules.role.infrastructure.rest;

import com.waremx.common.application.services.CreateService;
import com.waremx.common.core.errors.MocaErr;
import com.waremx.common.core.errors.MocaErrApiResponse;
import com.waremx.modules.role.infrastructure.rest.dtos.CreateRoleDto;
import com.waremx.modules.role.infrastructure.rest.dtos.RoleDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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

    @POST()
    @Path("/create")
    @Operation(summary = "Create new role")
    @APIResponse(
            responseCode = "200",
            description = "Operation completed successfully"
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format. Please check the request body",
            content = @Content(schema = @Schema(implementation = MocaErr.class),
                    examples = {
                            @ExampleObject(value = MocaErrApiResponse.ALREADY_EXISTS),
                    })
    )
    @APIResponse(
            responseCode = "500",
            description = "Error to create",
            content = @Content(schema = @Schema(implementation = MocaErr.class),
                    examples = {
                            @ExampleObject(value = MocaErrApiResponse.ERROR_TO_CREATE),
                    })
    )
    @RequestBody(
            description = "Role to create",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = CreateRoleDto.class),
                    examples = @ExampleObject(
                            name = "Example Role",
                            value = "{ \"roleName\": \"SOMETHING_ROLE\" }"
                    )
            )
    )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create() {
        return null;
    }
}
