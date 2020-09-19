package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.CategoryRepository;
import br.com.tiagoamp.timetracker.repository.UserEntity;
import br.com.tiagoamp.timetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private UserRepository userRepo;
    private CategoryRepository categoryRepo;
    private UserMapper userMapper;


    @Autowired
    public UserService(UserRepository userRepo, CategoryRepository categoryRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.userMapper = userMapper;
    }


    public User create(User user) {
        if ( userRepo.findByEmail(user.getEmail()).isPresent() )
            throw new ResourceAlreadyRegisteredException("User e-mail already exists");
        var entity = userRepo.save(userMapper.toEntity(user));
        return userMapper.toModel(entity);
    }

    public User update(User user) {
        findUserEntityIfExists(user.getId());
        var entity = userRepo.save(userMapper.toEntity(user));
        return userMapper.toModel(entity);
    }

    public void delete(Long userId) {
        var userEntity = findUserEntityIfExists(userId);
        var userHasCategories = !categoryRepo.retrieveByUser(userId).isEmpty();
        if (userHasCategories)
            throw new TimeTrackerOperationException("User has Categories registered and cannot be deleted");
        userRepo.delete(userEntity);
    }

    public List<User> findUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toModel).collect(toList());
    }

    public User findUserById(Long id) {
        var entity = findUserEntityIfExists(id);
        return userMapper.toModel(entity);
    }


    private UserEntity findUserEntityIfExists(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userId));
    }

}
