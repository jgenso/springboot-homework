package com.diplomado.homework.web.rest;

import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;
import com.diplomado.homework.services.RoleService;
import com.diplomado.homework.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        return ResponseEntity.ok().body(userService.listUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) {
        return ResponseEntity
                .ok()
                .body(userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("Resource not found exception for the id: " + id)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody final UserDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("A new user cannot already have an id.");
        }

        UserDTO user = userService.save(dto);

        return ResponseEntity.created(new URI("/v1/users/" + user.getId())).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> edit(@RequestBody final UserDTO dto,
                                                @PathVariable final Long id) throws URISyntaxException {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Invalid user id, null value");
        }
        if (!Objects.equals(dto.getId(), id)) {
            throw new IllegalArgumentException("Invalid id");
        }

        return ResponseEntity
                .ok()
                .body(this.userService.save(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}