package com.diplomado.homework.web.rest;

import com.diplomado.homework.domain.entities.User;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;
import com.diplomado.homework.services.RoleService;
import com.diplomado.homework.services.UserService;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Tag(name = "User", description = "User management APIs")
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "List all registered users",
            description = "Get a List of User objects. The response is a list of users, with each object with id, username, password, email, and maybe user details and user roles(depending on the request parameter detailed).",
            tags = { "User" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers(@RequestParam(required = false, defaultValue = "false") boolean detailed) {
        try {
            if (detailed) {
                return ResponseEntity.ok().body(userService.listUsersDetailed());
            } else {
                return ResponseEntity.ok().body(userService.listUsers());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Retrieve a User by specifying its id",
            description = "Get an User object by specifying its id, the response includes id, username, password, email, userDetail and roles fields",
            tags = { "User" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("Resource not found exception for the id: " + id)));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Create a new User",
            description = "Create a new user, request body contains only the name, password, email fields, userDetail and roles fields are optional",
            tags = { "User" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody final UserDTO dto) {
        try {
            if (dto.getId() != null) {
                throw new IllegalArgumentException("A new user cannot already have an id.");
            }

            UserDTO user = userService.save(dto);

            return ResponseEntity.created(new URI("/v1/users/" + user.getId())).body(user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Update User info using its id",
            description = "Update an user, request body contains the id, username, password, email fields, userDetail and roles are optional",
            tags = { "User" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> edit(@RequestBody final UserDTO dto,
                                                @PathVariable final Long id) {
        try {
            if (dto.getId() == null) {
                throw new IllegalArgumentException("Invalid user id, null value");
            }
            if (!Objects.equals(dto.getId(), id)) {
                throw new IllegalArgumentException("Invalid id");
            }

            return ResponseEntity
                    .ok()
                    .body(this.userService.update(dto));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Assign a role to an user",
            description = "Assign a role to an existing user",
            tags = { "User" })
    @PostMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserDTO> assignRole(@PathVariable final Long id, @PathVariable final Long roleId) {
        try {
            return ResponseEntity
                    .ok()
                    .body(this.userService.assignRole(id, roleId));
        } catch (java.util.NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Deactivates an existing role tied to an user",
            description = "Deactivates an existing role tied to an user",
            tags = { "User" })
    @PatchMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserDTO> inactivateRole(@PathVariable final Long id, @PathVariable final Long roleId) {
        try {
            return ResponseEntity
                    .ok()
                    .body(this.userService.inactivateRole(id, roleId));
        } catch (java.util.NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}