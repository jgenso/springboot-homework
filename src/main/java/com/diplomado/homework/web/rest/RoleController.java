package com.diplomado.homework.web.rest;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.RoleWithDetailDTO;
import com.diplomado.homework.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> listRoles() {
        return ResponseEntity.ok().body(roleService.listRoles());
    }

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