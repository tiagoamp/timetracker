package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.TimeTrackerException;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private TimeEntryRepository timeEntryRepo;

    @Autowired
    private UserMapper userMapper;


    public User create(User user) {
        if ( userRepo.findByEmail(user.getEmail()).isPresent() )
            throw new ResourceAlreadyRegisteredException("User e-mail already exists");
        UserEntity entity = userRepo.save(userMapper.toEntity(user));
        return userMapper.toModel(entity);
    }

    public User update(User userWithId) {
        userRepo.findById(userWithId.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User id: "+userWithId.getId()));
        UserEntity entity = userRepo.save(userMapper.toEntity(userWithId));
        return userMapper.toModel(entity);
    }

    public void delete(Long userId) throws ResourceNotFoundException {
        var userEntity = findUserEntityIfExists(userId);
        userRepo.delete(userEntity);
    }

    public List<User> findUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toModel).collect(toList());
    }

    public User findUserById(Long id) {
        UserEntity entity = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id: "+id));
        return userMapper.toModel(entity);
    }

    public Category createCategory(Long userId, Category category) throws ResourceNotFoundException {
        var userEntity = findUserEntityIfExists(userId);
        var categoriesEntityOfThisUser = categoryRepo.retrieveByUser(userId);
        var nameAlreadyExists = categoriesEntityOfThisUser.stream()
                .filter(cat -> cat.getName().equals(category.getName()))
                .findFirst().isPresent();
        if (nameAlreadyExists) throw new ResourceNotFoundException("Category name already exists");
        var categoryEntity = CategoryEntity.from(category);
        categoryEntity.setUser(userEntity);
        categoryEntity = categoryRepo.save(categoryEntity);
        return categoryEntity.toModel();
    }

    public Category update(Long userId, Category category) throws ResourceNotFoundException {
        CategoryEntity categoryEntity = findCategoryEntityIfExists(userId, category.getId());
        categoryEntity.updateInfoFrom(category);
        categoryEntity = categoryRepo.save(categoryEntity);
        return categoryEntity.toModel();
    }

    public void delete(Long userId, Integer categoryId) throws ResourceNotFoundException {
        var categoryEntity = findCategoryEntityIfExists(userId, categoryId);
        var isCategoryUsedInTimeEntries = !timeEntryRepo.retrieveByCategory(categoryId).isEmpty();
        if (isCategoryUsedInTimeEntries)
            throw new ResourceNotFoundException("Category is associated to existing time entry and cannot be deleted");
        categoryRepo.delete(categoryEntity);
    }

    public List<Category> findCategories(Long userId) {
        return categoryRepo.retrieveByUser(userId).stream()
                .map(CategoryEntity::toModel).collect(toList());
    }

    public Category findCategoryById(Long userId, Integer categoryId) throws TimeTrackerException, ResourceNotFoundException {
        return findCategoryEntityIfExists(userId, categoryId).toModel();
    }


    private UserEntity findUserEntityIfExists(Long id) throws ResourceNotFoundException {
        return userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User id: "+id));
    }

    private CategoryEntity findCategoryEntityIfExists(Long userId, Integer categoryId) throws ResourceNotFoundException {
        findUserEntityIfExists(userId);
        return categoryRepo.retrieveByUser(userId).stream()
                .filter(cat -> cat.getId().intValue() == categoryId.intValue())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category id: "+categoryId));
    }

}
