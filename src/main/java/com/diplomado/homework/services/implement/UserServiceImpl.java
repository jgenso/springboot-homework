package com.diplomado.homework.services.implement;

import com.diplomado.homework.domain.entities.User;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;
import com.diplomado.homework.repositories.spring.data.RoleRepository;
import com.diplomado.homework.repositories.spring.data.UserRepository;
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
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
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
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public UserDTO save(UserDTO dto) {
        return this.userMapper
                .toDto(userRepository.save(this.userMapper.toEntity(dto)));

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
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}