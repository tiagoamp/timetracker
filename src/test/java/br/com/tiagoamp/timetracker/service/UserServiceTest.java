package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.mapper.UserMapperImpl;
import br.com.tiagoamp.timetracker.model.TimeTrackerException;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.CategoryRepository;
import br.com.tiagoamp.timetracker.repository.TimeEntryRepository;
import br.com.tiagoamp.timetracker.repository.UserEntity;
import br.com.tiagoamp.timetracker.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;
    @Mock
    private CategoryRepository categoryRepo;
    @Mock
    private TimeEntryRepository timeEntryRepo;

    @Spy  // injects this specific instance to target class
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService service;


    @Test
    @DisplayName("When existing e-mail should throw exception")
    void create_shouldThrowError() {
        var newUser = new User("existing@email.com", "Name", "");
        Mockito.when(userRepo.findByEmail(newUser.getEmail())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(TimeTrackerException.class, () -> service.create(newUser));
    }

    @Test
    @DisplayName("When new e-mail should create user")
    void create_shouldCreateUser() throws TimeTrackerException {
        // given
        var newUser = new User("test-id", "new@email.com", "Name", "");
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
        var newUser = new User("test-id","existing@email.com", "Name", "pass");
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(TimeTrackerException.class, () -> service.update(newUser));
    }

    @Test
    @DisplayName("When user exists should update info")
    void update_shouldUpdateUser() throws TimeTrackerException {
        // given
        var user = new User("test-id","existing@email.com", "Name", "pass");
        var userEntity = userMapper.toEntity(user);
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        // when
        var result = service.update(user);
        // then
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("When user id does not exist should throw exception")
    void delete_shouldThrowError() {
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(TimeTrackerException.class, () -> service.delete("test-id"));
    }

    @Test
    @DisplayName("When user id exists should delete user")
    void delete_shouldDeleteUser() throws TimeTrackerException {
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(new UserEntity()));
        service.delete("test-id");
    }

    @Test
    void findUsers() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void createCategory() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void findCategories() {
    }

    @Test
    void findCategoryById() {
    }
}