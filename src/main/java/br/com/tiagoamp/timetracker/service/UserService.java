package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.TimeTrackerException;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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


    public User create(User user) throws TimeTrackerException {
        if ( userRepo.findByEmail(user.getEmail()).isPresent() )
            throw new TimeTrackerException("User e-mail already exists");
        user.setId(UUID.randomUUID().toString());
        UserEntity entity = userRepo.save(userMapper.toEntity(user));
        return userMapper.toModel(entity);
    }

    public User update(User user) throws TimeTrackerException {
        var userEntity = findUserEntityIfExists(user.getId());
        userEntity.updateInfoFrom(user);
        return userMapper.toModel(userEntity);
    }

    public void delete(String userId) throws TimeTrackerException {
        var userEntity = findUserEntityIfExists(userId);
        userRepo.delete(userEntity);
    }

    public List<User> findUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toModel).collect(toList());
    }

    public User findUserById(String id) throws TimeTrackerException {
        UserEntity entity = findUserEntityIfExists(id);
        return userMapper.toModel(entity);
    }

    public Category createCategory(String userId, Category category) throws TimeTrackerException {
        var userEntity = findUserEntityIfExists(userId);
        var categoriesEntityOfThisUser = categoryRepo.retrieveByUser(userId);
        var nameAlreadyExists = categoriesEntityOfThisUser.stream()
                .filter(cat -> cat.getName().equals(category.getName()))
                .findFirst().isPresent();
        if (nameAlreadyExists) throw new TimeTrackerException("Category name already exists");
        var categoryEntity = CategoryEntity.from(category);
        categoryEntity.setUser(userEntity);
        categoryEntity = categoryRepo.save(categoryEntity);
        return categoryEntity.toModel();
    }

    public Category update(String userId, Category category) throws TimeTrackerException {
        CategoryEntity categoryEntity = findCategoryEntityIfExists(userId, category.getId());
        categoryEntity.updateInfoFrom(category);
        categoryEntity = categoryRepo.save(categoryEntity);
        return categoryEntity.toModel();
    }

    public void delete(String userId, Integer categoryId) throws TimeTrackerException {
        var categoryEntity = findCategoryEntityIfExists(userId, categoryId);
        var isCategoryUsedInTimeEntries = !timeEntryRepo.retrieveByCategory(categoryId).isEmpty();
        if (isCategoryUsedInTimeEntries)
            throw new TimeTrackerException("Category is associated to existing time entry and cannot be deleted");
        categoryRepo.delete(categoryEntity);
    }

    public List<Category> findCategories(String userId) {
        return categoryRepo.retrieveByUser(userId).stream()
                .map(CategoryEntity::toModel).collect(toList());
    }

    public Category findCategoryById(String userId, Integer categoryId) throws TimeTrackerException {
        return findCategoryEntityIfExists(userId, categoryId).toModel();
    }


    private UserEntity findUserEntityIfExists(String id) throws TimeTrackerException {
        return userRepo.findById(id)
            .orElseThrow(() -> new TimeTrackerException("User does not exist"));
    }

    private CategoryEntity findCategoryEntityIfExists(String userId, Integer categoryId) throws TimeTrackerException {
        findUserEntityIfExists(userId);
        return categoryRepo.retrieveByUser(userId).stream()
                .filter(cat -> cat.getId().intValue() == categoryId.intValue())
                .findFirst()
                .orElseThrow(() -> new TimeTrackerException("There is no such category id for informed user"));
    }

}
