package com.diplomado.homework.services;

import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> listUsers();
    Optional<UserDTO> getUserById(Long id);
    UserDTO save(UserDTO userDTO);
    void delete(Long id);

}
