package com.diplomado.homework.web.rest;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable final Long id) {
        return ResponseEntity
                .ok()
                .body(roleService.getRoleById(id).orElseThrow(() -> new IllegalArgumentException("Resource not found exception for the id: " + id)));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody final RoleDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("A new role cannot already have an id.");
        }

        RoleDTO role = roleService.save(dto);

        return ResponseEntity.created(new URI("/v1/roles/" + role.getId())).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> edit(@RequestBody final RoleDTO dto,
                                                @PathVariable final Long id) throws URISyntaxException {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Invalid role id, null value");
        }
        if (!Objects.equals(dto.getId(), id)) {
            throw new IllegalArgumentException("Invalid id");
        }

        return ResponseEntity
                .ok()
                .body(this.roleService.save(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        roleService.delete(id);

        return ResponseEntity.noContent().build();
    }
}