package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.UserRequestDTO;
import br.com.tiagoamp.timetracker.dto.UserResponseDTO;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userReqDTO) {
        var user = userMapper.toModel(userReqDTO);
        user = userService.create(user);
        var userDTO = userMapper.toResponseDTO(user);
        return ResponseEntity.created(URI.create(userDTO.getId().toString())).body(userDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@NotNull @PathVariable("id") Long id, @Valid @RequestBody UserRequestDTO userReqDTO) {
        var user = userMapper.toModel(userReqDTO);
        user.setId(id);
        user = userService.update(user);
        var userDTO = userMapper.toResponseDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeUser(@NotNull @PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        var users = userService.findUsers();
        var dtos = users.stream().map(userMapper::toResponseDTO).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@NotNull @PathVariable("id") Long id) {
        var user = userService.findUserById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

}
