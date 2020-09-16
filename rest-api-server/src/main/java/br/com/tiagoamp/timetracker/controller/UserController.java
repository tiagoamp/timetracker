package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.CategoryRequestDTO;
import br.com.tiagoamp.timetracker.dto.CategoryResponseDTO;
import br.com.tiagoamp.timetracker.dto.UserRequestDTO;
import br.com.tiagoamp.timetracker.dto.UserResponseDTO;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.mapper.CategoryMapper;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;
    private CategoryMapper categoryMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, CategoryMapper categoryMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.categoryMapper = categoryMapper;
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


    @PostMapping("{userId}/category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@PathVariable("userId") Long userId, @Valid @RequestBody CategoryRequestDTO categoryReqDTO) {
        var category = categoryMapper.toModel(categoryReqDTO);
        category = userService.createCategory(userId, category);
        var categoryDTO = categoryMapper.toResponseDTO(category);
        categoryDTO.setUserId(userId);
        return ResponseEntity.created(URI.create(categoryDTO.getId().toString())).body(categoryDTO);
    }

    @PutMapping("{userId}/category/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryRequestDTO categoryReqDTO) {
        var category = categoryMapper.toModel(categoryReqDTO);
        category.setId(categoryId);
        category = userService.update(userId, category);
        var categoryDTO = categoryMapper.toResponseDTO(category);
        categoryDTO.setUserId(userId);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("{userId}/category/{categoryId}")
    public ResponseEntity<?> removeCategory(@PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId) {
        userService.delete(userId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{userId}/category")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesByUser(@PathVariable("userId") Long userId) {
        var categories = userService.findCategories(userId);
        var dtos = categories.stream()
                .map(categoryMapper::toResponseDTO)
                .map(dto -> {
                    dto.setUserId(userId);
                    return dto;
                }).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{userId}/category/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoriesById(@PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId) {
        var category = userService.findCategoryById(userId, categoryId);
        var dto = categoryMapper.toResponseDTO(category);
        dto.setUserId(userId);
        return ResponseEntity.ok(dto);
    }

}
