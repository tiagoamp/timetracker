package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.CategoryRequestDTO;
import br.com.tiagoamp.timetracker.dto.CategoryResponseDTO;
import br.com.tiagoamp.timetracker.dto.UserRequestDTO;
import br.com.tiagoamp.timetracker.dto.UserResponseDTO;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.TimeTrackerException;
import br.com.tiagoamp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userReqDTO) {
        var user = userMapper.toModel(userReqDTO);
        try {
            user = userService.create(user);
            var userDTO = userMapper.toResponseDTO(user);
            return ResponseEntity.created(URI.create(userDTO.getId())).body(userDTO);
        } catch (TimeTrackerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@NotNull @PathVariable("id") String id, @Valid @RequestBody UserRequestDTO userReqDTO) {
        var user = userMapper.toModel(userReqDTO);
        user.setId(id);
        try {
            user = userService.update(user);
            var userDTO = userMapper.toResponseDTO(user);
            return ResponseEntity.ok(userDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeUser(@NotNull @PathVariable("id") String id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        var users = userService.findUsers();
        var dtos = users.stream().map(userMapper::toResponseDTO).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@NotNull @PathVariable("id") String id) {
        var user = userService.findUserById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }


    @PostMapping("{userId}/category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@PathVariable("userId") String userId, @RequestBody CategoryRequestDTO categoryReqDTO) {
        return ResponseEntity.ok(new CategoryResponseDTO());
    }

    @PutMapping("{userId}/category/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("userId") String userId, @PathVariable("categoryId") Long categoryId, @RequestBody CategoryRequestDTO categoryReqDTO) {
        return ResponseEntity.ok(new CategoryResponseDTO());
    }

    @DeleteMapping("{userId}/category/{categoryId}")
    public ResponseEntity<?> removeCategory(@PathVariable("userId") String userId, @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("{userId}/category")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesByUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("{userId}/category/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoriesById(@PathVariable("userId") String userId, @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(new CategoryResponseDTO());
    }

/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
*/

}
