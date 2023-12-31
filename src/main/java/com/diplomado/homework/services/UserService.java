package com.diplomado.homework.services;

import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> listUsers();

    List<UserDTO> listUsersDetailed();
    Optional<UserDTO> getUserById(Long id);
    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    UserDTO assignRole(Long userId, Long roleId);

    UserDTO inactivateRole(Long userId, Long roleId);
    void delete(Long id);

}
