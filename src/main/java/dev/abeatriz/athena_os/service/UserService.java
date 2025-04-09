package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.user.UserCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.user.UserDetailDTO;
import dev.abeatriz.athena_os.entity.User;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.entity.enums.UserStatus;
import dev.abeatriz.athena_os.mapper.UserMapper;
import dev.abeatriz.athena_os.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User create(UserCreateUpdateDTO createDTO) {
        if (userRepository.findByEmail(createDTO.email()).isPresent()) {
            throw new DataIntegrityViolationException("Já existe um usuário com esse email: " + createDTO.email());
        }

        var user = new User();
        user.setEmail(createDTO.email());
        user.setPassword(passwordEncoder.encode(createDTO.email()));
        user.setStatus(UserStatus.ATIVO);
        user.setRole(UserRole.valueOf(createDTO.role()));

        return userRepository.save(user);
    }


    @Transactional
    public UserDetailDTO me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt) authentication.getPrincipal();
        var email = jwt.getClaimAsString("email");
        var user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return userMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDetailDTO> findAll() {
        var users = userRepository.findAll();
        return userMapper.toDTO(users);
    }

    @Transactional(readOnly = true)
    public UserDetailDTO listById(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return userMapper.toDTO(userEntity);
    }

    @Transactional
    public UserDetailDTO update(UserCreateUpdateDTO updateDTO, Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userEntity.update(updateDTO);
        return userMapper.toDTO(userEntity);
    }

    @Transactional()
    public UserDetailDTO disable(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userEntity.disable();
        return userMapper.toDTO(userEntity);
    }


}
