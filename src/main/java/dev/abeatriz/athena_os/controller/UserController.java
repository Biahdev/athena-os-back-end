package dev.abeatriz.athena_os.controller;


import dev.abeatriz.athena_os.dto.user.UserCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.user.UserDetailDTO;
import dev.abeatriz.athena_os.mapper.UserMapper;
import dev.abeatriz.athena_os.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserDetailDTO> create(@RequestBody @Valid UserCreateUpdateDTO json) {
        var newUser = userService.create(json);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(newUser));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailDTO> me() {
        var user = userService.me();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDTO> findById(@PathVariable Long id) {
        var user = userService.listById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDetailDTO>> findAll() {
        var users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailDTO> update(@RequestBody @Valid UserCreateUpdateDTO json, @PathVariable Long id) {
        var updatedUser = userService.update(json, id);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<UserDetailDTO> disable(@PathVariable Long id) {
        var user = userService.disable(id);
        return ResponseEntity.ok(user);
    }


}
