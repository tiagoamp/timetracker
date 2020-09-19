package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.UserEntity;
import br.com.tiagoamp.timetracker.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;
    @Mock
    private CategoryService categoryService;

    @Spy  // injects this specific instance to target class
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService service;


    @Test
    @DisplayName("When existing e-mail should throw exception")
    void create_shouldThrowError() {
        var newUser = new User("existing@email.com", "Name", "");
        Mockito.when(userRepo.findByEmail(newUser.getEmail())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(ResourceAlreadyRegisteredException.class, () -> service.create(newUser));
    }

    @Test
    @DisplayName("When new e-mail should create user")
    void create_shouldCreateUser() {
        // given
        var newUser = new User(1L, "new@email.com", "Name", "");
        var userEntity = userMapper.toEntity(newUser);
        Mockito.when(userRepo.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepo.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        // when
        var result = service.create(newUser);
        // then
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("When user does not exist should throw exception")
    void update_shouldThrowError() {
        var newUser = new User(1L,"blah@email.com", "Name", "pass");
        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(newUser));
    }

    @Test
    @DisplayName("When user exists should update info")
    void update_shouldUpdateUser() throws ResourceNotFoundException {
        // given
        var user = new User(1L,"existing@email.com", "Name", "pass");
        var userEntity = userMapper.toEntity(user);
        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(userEntity));
        Mockito.when(userRepo.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        // when
        var result = service.update(user);
        // then
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("When user id does not exist should throw exception")
    void delete_shouldThrowError1() {
        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    @DisplayName("When user has categories registered should throw exception")
    void delete_shouldThrowError2() {
        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(new UserEntity()));
        Mockito.when(categoryService.findCategories(Mockito.anyLong())).thenReturn(Arrays.asList(new Category()));
        assertThrows(TimeTrackerOperationException.class, () -> service.delete(1L));
    }

    @Test
    @DisplayName("When user id exists should delete user")
    void delete_shouldDeleteUser() {
        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(new UserEntity()));
        service.delete(1L);
    }

    @Test
    @DisplayName("When does not exist users should return empty list")
    void findUsers_shouldReturnEmptyList() {
        Mockito.when(userRepo.findAll()).thenReturn(new ArrayList<>());
        var result = service.findUsers();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("When users exist should return users list")
    void findUsers_shouldReturnList() {
        var userEntities = Arrays.asList(new UserEntity(), new UserEntity());
        Mockito.when(userRepo.findAll()).thenReturn(userEntities);
        var result = service.findUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("When user id does not exist should throw exception")
    void findUserById_shouldThrowError() {
        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findUserById(1L));
    }

    @Test
    @DisplayName("When user id exists should return user")
    void findUserById_shouldReturnUser() {
        // given
        var user = new User(1L,"existing@email.com", "Name", "pass");
        var userEntity = userMapper.toEntity(user);
        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(userEntity));
        // when
        var result = service.findUserById(user.getId());
        // then
        assertEquals(user, result);
        assertNotNull(result.getId());
    }

}