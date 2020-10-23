package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.CategoryRepository;
import br.com.tiagoamp.timetracker.repository.UserEntity;
import br.com.tiagoamp.timetracker.repository.UserRepository;
import br.com.tiagoamp.timetracker.security.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.tiagoamp.timetracker.model.Role.ROLE_USER;
import static java.util.stream.Collectors.toList;

@Service
public class UserService implements UserDetailsService {

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
        if (user.getRole() == null) user.setRole(ROLE_USER);
        var entity = userRepo.save(userMapper.toEntity(user));
        return userMapper.toModel(entity);
    }

    public User update(User user) {
        UserEntity userEntity = findUserEntityIfExists(user.getId());
        user.setRole(userEntity.getRole());  // keeps previous Role
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

    @Override  // used for authentication
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("Authentication failed"));
        User user = userMapper.toModel(userEntity);
        return UserAuth.fromModel(user);
    }


    private UserEntity findUserEntityIfExists(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userId));
    }

}
