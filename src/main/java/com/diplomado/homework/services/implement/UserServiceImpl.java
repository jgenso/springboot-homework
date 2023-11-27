package com.diplomado.homework.services.implement;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.domain.entities.User;
import com.diplomado.homework.domain.entities.UserRole;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;
import com.diplomado.homework.repositories.spring.data.RoleRepository;
import com.diplomado.homework.repositories.spring.data.UserRepository;
import com.diplomado.homework.repositories.spring.data.UserRoleRepository;
import com.diplomado.homework.services.RoleService;
import com.diplomado.homework.services.UserService;
import com.diplomado.homework.services.mapper.RoleMapper;
import com.diplomado.homework.services.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> listUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> listUsersDetailed() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDtoDetailed).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDtoDetailed);
    }

    @Override
    public UserDTO save(UserDTO dto) {
        User user = userRepository.save(this.userMapper.toEntity(dto));
        user.getUserRoles().stream().forEach(ur -> userRoleRepository.save(ur));
        return this.userMapper
                .toDto(user);

    }

    @Override
    public UserDTO update(UserDTO dto) {
        LocalDateTime createdAt = userRepository.getReferenceById(dto.getId()).getCreatedAt();
        User user = this.userMapper.toEntity(dto);
        user.setCreatedAt(createdAt);
        return this.userMapper
                .toDto(userRepository.save(user));

    }

    @Override
    public UserDTO assignRole(Long userId, Long roleId) {
        User user = userRepository.getReferenceById(userId);
        Role role = roleRepository.getReferenceById(roleId);

        UserRole userRole = new UserRole(true, user, role);

        userRoleRepository.save(userRole);

        return userMapper.toDtoDetailed(userRepository.getReferenceById(userId));
    }

    @Override
    public UserDTO inactivateRole(Long userId, Long roleId) {
        User user = userRepository.getReferenceById(userId);
        UserRole userRole = user.getUserRoles().stream().filter(ur -> ur.getRole().getId() == roleId).findFirst().get();
        userRole.setActive(false);

        userRoleRepository.save(userRole);

        return userMapper.toDtoDetailed(userRepository.getReferenceById(userId));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}