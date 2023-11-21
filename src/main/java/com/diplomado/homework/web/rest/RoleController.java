package com.diplomado.homework.web.rest;

import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.RoleWithDetailDTO;
import com.diplomado.homework.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Tag(name = "Role", description = "Role management APIs")
@RestController
@RequestMapping("/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(
            summary = "List all registered roles",
            description = "Get a List of Role objects. The response is list of roles, with each object with id and name.",
            tags = { "Role" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = RoleDTO.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    @GetMapping
    public ResponseEntity<List<RoleDTO>> listRoles() {
        return ResponseEntity.ok().body(roleService.listRoles());
    }

    @Operation(
            summary = "Retrieve a Role object with the related users",
            description = "Get an Role object by specifying its id, the response includes a list of users that have that role. Each user has id, username, firstname, lastname, active fields",
            tags = { "Role" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RoleWithDetailDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
    @GetMapping("/{id}/users")
    public ResponseEntity<RoleWithDetailDTO> listUsersForRole(@PathVariable final Long id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(roleService.listUsersForRole(id));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Retrieve a Role by specifying its id",
            description = "Get an Role object by specifying its id, the response includes id and name fields",
            tags = { "Role" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RoleDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable final Long id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(roleService.getRoleById(id).orElseThrow(() -> new IllegalArgumentException("Resource not found exception for the id: " + id)));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Create a new Role",
            description = "Create a new role, request body contains only the name of the role",
            tags = { "Role" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RoleDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody final RoleDTO dto) {
        try {
            if (dto.getId() != null) {
                throw new IllegalArgumentException("A new role cannot already have an id.");
            }

            RoleDTO role = roleService.save(dto);

            return ResponseEntity.created(new URI("/v1/roles/" + role.getId())).body(role);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Update Role info using its id",
            description = "Update a role, request body contains the id and name of the role, id in request body should match the id in the path",
            tags = { "Role" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RoleDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> edit(@RequestBody final RoleDTO dto,
                                                @PathVariable final Long id) {
        try {
            if (dto.getId() == null) {
                throw new IllegalArgumentException("Invalid role id, null value");
            }
            if (!Objects.equals(dto.getId(), id)) {
                throw new IllegalArgumentException("Invalid id");
            }

            return ResponseEntity
                    .ok()
                    .body(this.roleService.save(dto));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


    @Operation(
            summary = "Delte Role using the id",
            description = "Delete a role, path parameter should be the role id",
            tags = { "Role" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(implementation = Integer.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        try {
            roleService.delete(id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}