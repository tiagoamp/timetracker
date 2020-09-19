package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.CategoryMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.repository.CategoryEntity;
import br.com.tiagoamp.timetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {

    private CategoryRepository categoryRepo;
    private UserService userService;
    private TimeService timeService;
    private CategoryMapper categoryMapper;


    @Autowired
    public CategoryService(CategoryRepository categoryRepo, UserService userService, TimeService timeService, CategoryMapper categoryMapper) {
        this.categoryRepo = categoryRepo;
        this.userService = userService;
        this.timeService = timeService;
        this.categoryMapper = categoryMapper;
    }


    public Category createCategory(Long userId, Category category) {
        var user = userService.findUserById(userId);
        var categoriesEntity = categoryRepo.retrieveByUser(userId);
        var isNameAlreadyRegistered = categoriesEntity.stream()
                .anyMatch(cat -> cat.getName().equals(category.getName()));
        if (isNameAlreadyRegistered)
            throw new ResourceAlreadyRegisteredException("Category name already exists");
        var categoryEntity = categoryMapper.toEntity(category);
        categoryEntity.setUserWithId(user.getId());
        categoryEntity = categoryRepo.save(categoryEntity);
        return categoryMapper.toModel(categoryEntity);
    }

    public Category update(Long userId, Category category) {
        findCategoryEntityIfExists(userId, category.getId());
        var categoryEntity = categoryMapper.toEntity(category);
        categoryEntity = categoryRepo.save(categoryEntity);
        return categoryMapper.toModel(categoryEntity);
    }

    public void delete(Long userId, Long categoryId) {
        var categoryEntity = findCategoryEntityIfExists(userId, categoryId);
        var isCategoryUsedInTimeEntries = !timeService.findByCategory(categoryId).isEmpty();
        if (isCategoryUsedInTimeEntries)
            throw new TimeTrackerOperationException("Category is associated to existing time entry and cannot be deleted");
        categoryRepo.delete(categoryEntity);
    }

    public List<Category> findCategories(Long userId) {
        return categoryRepo.retrieveByUser(userId).stream()
                .map(categoryMapper::toModel).collect(toList());
    }

    public Category findCategoryById(Long userId, Long categoryId) {
        return categoryMapper.toModel( findCategoryEntityIfExists(userId, categoryId) );
    }


    private CategoryEntity findCategoryEntityIfExists(Long userId, Long categoryId) {
        userService.findUserById(userId);
        return categoryRepo.retrieveByUser(userId).stream()
                .filter(cat -> cat.getId().longValue() == categoryId.longValue())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category id: " + categoryId));
    }

}
