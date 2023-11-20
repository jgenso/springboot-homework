package com.diplomado.homework.services;

import com.diplomado.homework.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDTO> listRoles();
    Optional<RoleDTO> getRoleById(Long roleId);
    RoleDTO save(RoleDTO roleDTO);
    void delete(Long id);

}
