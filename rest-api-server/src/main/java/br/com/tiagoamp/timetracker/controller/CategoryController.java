package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.CategoryRequestDTO;
import br.com.tiagoamp.timetracker.dto.CategoryResponseDTO;
import br.com.tiagoamp.timetracker.mapper.CategoryMapper;
import br.com.tiagoamp.timetracker.security.AuthorizationRules;
import br.com.tiagoamp.timetracker.service.CategoryService;
import br.com.tiagoamp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("user/{userId}/category")
public class CategoryController {

    private CategoryService categoryService;
    private CategoryMapper categoryMapper;
    private AuthorizationRules auth;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper, UserService userService, AuthorizationRules authorizationRules) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.auth = authorizationRules;
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(@PathVariable("userId") Long userId, @Valid @RequestBody CategoryRequestDTO categoryReqDTO) {
        auth.authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(userId);
        var category = categoryMapper.toModel(categoryReqDTO);
        category = categoryService.createCategory(userId, category);
        var categoryDTO = categoryMapper.toResponseDTO(category);
        categoryDTO.setUserId(userId);
        return ResponseEntity.created(URI.create(categoryDTO.getId().toString())).body(categoryDTO);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryRequestDTO categoryReqDTO) {
        auth.authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(userId);
        var category = categoryMapper.toModel(categoryReqDTO);
        category.setId(categoryId);
        category = categoryService.update(userId, category);
        var categoryDTO = categoryMapper.toResponseDTO(category);
        categoryDTO.setUserId(userId);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity removeCategory(@PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId) {
        auth.authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(userId);
        categoryService.delete(userId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesByUser(@PathVariable("userId") Long userId) {
        auth.authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(userId);
        var categories = categoryService.findCategories(userId);
        var dtos = categories.stream()
                .map(categoryMapper::toResponseDTO)
                .map(dto -> {
                    dto.setUserId(userId);
                    return dto;
                }).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoriesById(@PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId) {
        auth.authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(userId);
        var category = categoryService.findCategoryById(userId, categoryId);
        var dto = categoryMapper.toResponseDTO(category);
        dto.setUserId(userId);
        return ResponseEntity.ok(dto);
    }

}
