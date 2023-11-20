package com.diplomado.homework.services.implement;

import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.repositories.spring.data.RoleRepository;
import com.diplomado.homework.services.RoleService;
import com.diplomado.homework.services.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public Optional<RoleDTO> getRoleById(Long id) {
        return roleRepository.findById(id).map(roleMapper::toDto);
    }

    @Override
    public RoleDTO save(RoleDTO dto) {
        return this.roleMapper
                .toDto(roleRepository.save(this.roleMapper.toEntity(dto)));

    }

    @Override
    public void delete(Long roleId) {
        roleRepository.deleteById(roleId);
    }


}