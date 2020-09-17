package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.CategoryMapper;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private UserRepository userRepo;
    private CategoryRepository categoryRepo;
    private TimeEntryRepository timeEntryRepo;
    private UserMapper userMapper;
    private CategoryMapper categoryMapper;


    @Autowired
    public UserService(UserRepository userRepo, CategoryRepository categoryRepo, TimeEntryRepository timeEntryRepo, UserMapper userMapper, CategoryMapper categoryMapper) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.timeEntryRepo = timeEntryRepo;
        this.userMapper = userMapper;
        this.categoryMapper = categoryMapper;
    }


    public User create(User user) {
        if ( userRepo.findByEmail(user.getEmail()).isPresent() )
            throw new ResourceAlreadyRegisteredException("User e-mail already exists");
        var entity = userRepo.save(userMapper.toEntity(user));
        return userMapper.toModel(entity);
    }

    public User update(User userWithId) {
        userRepo.findById(userWithId.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User id: "+userWithId.getId()));
        var entity = userRepo.save(userMapper.toEntity(userWithId));
        return userMapper.toModel(entity);
    }

    public void delete(Long userId) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userId));
        userRepo.delete(userEntity);
    }

    public List<User> findUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toModel).collect(toList());
    }

    public User findUserById(Long id) {
        var entity = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + id));
        return userMapper.toModel(entity);
    }

    public Category createCategory(Long userId, Category category) {
        var userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userId));
        var categoriesEntityOfThisUser = categoryRepo.retrieveByUser(userId);
        var isNameAlreadyRegistered = categoriesEntityOfThisUser.stream()
                .anyMatch(cat -> cat.getName().equals(category.getName()));
        if (isNameAlreadyRegistered) throw new ResourceAlreadyRegisteredException("Category name already exists");
        var categoryEntity = categoryMapper.toEntity(category);
        categoryEntity.setUser(userEntity);
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
        var isCategoryUsedInTimeEntries = !timeEntryRepo.retrieveByCategory(categoryId).isEmpty();
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
        userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userId));
        return categoryRepo.retrieveByUser(userId).stream()
                .filter(cat -> cat.getId().intValue() == categoryId.intValue())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category id: " + categoryId));
    }

}
