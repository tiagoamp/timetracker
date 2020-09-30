package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.CategoryEntity;
import br.com.tiagoamp.timetracker.repository.TimeEntryEntity;
import br.com.tiagoamp.timetracker.repository.TimeEntryRepository;
import br.com.tiagoamp.timetracker.repository.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

    @Mock
    private TimeEntryRepository timeEntryRepo;
    @Mock
    private UserService userService;
    @Mock
    private CategoryService categoryService;

    @Spy  // injects this specific instance to target class
    private TimeEntryMapper timeMapper = Mappers.getMapper(TimeEntryMapper.class);
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private TimeService service;


    @Test
    @DisplayName("When user does not exist should throw exception")
    void create_shouldThrowError1() {
        var category = new Category(10L, "name", "description");
        var user = new User(1L, "email@email.com", "name", "passwd");
        var entry = new TimeEntry(null, LocalDateTime.now(), LocalDateTime.now(), "annot", category);
        Mockito.when(userService.findUserById(user.getId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.create(user.getId(), entry));
    }

    @Test
    @DisplayName("When category does not exist should throw exception")
    void create_shouldThrowError2() {
        var user = new User(1L, "email@email.com", "name", "passwd");
        var category = new Category(10L, "name", "description");
        var entry = new TimeEntry(null, LocalDateTime.now(), LocalDateTime.now(), "annot", category);
        Mockito.when(userService.findUserById(user.getId())).thenReturn(user);
        Mockito.when(categoryService.findCategories(user.getId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.create(user.getId(), entry));
    }

    @Test
    @DisplayName("When correct time entry should create time entry")
    void create_shouldCreateTimeEntry() {
        // given
        var user = new User(1L, "email@email.com", "name", "passwd");
        var categoryEntity = new CategoryEntity(10L, "name", "description");
        categoryEntity.setUserWithId(user.getId());
        var timeEntryEntity = new TimeEntryEntity(100L, LocalDateTime.now(), LocalDateTime.now(), "annot", categoryEntity);
        var entry = timeMapper.toModel(timeEntryEntity);
        Mockito.when(userService.findUserById(user.getId())).thenReturn(user);
        Mockito.when(categoryService.findCategories(user.getId())).thenReturn(Arrays.asList(new Category()));
        Mockito.when(timeEntryRepo.save(Mockito.any(TimeEntryEntity.class))).thenReturn(timeEntryEntity);
        // when
        var result = service.create(user.getId(), entry);
        // then
        assertNotNull(result.getId());
        assertNotNull(result.getCategory().getId());
    }

    @Test
    @DisplayName("When user does not exist for update should throw exception")
    void updateTimeEntry_shouldThrowError1() {
        var timeEntry = new TimeEntry(100L, LocalDateTime.now(), LocalDateTime.now(), "ann", null);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, timeEntry));
    }

    @Test
    @DisplayName("When time entry does not exist for update should throw exception")
    void updateTimeEntry_shouldThrowError2() {
        var userId = 1L;
        var timeEntry = new TimeEntry(100L, LocalDateTime.now(), LocalDateTime.now(), "ann", null);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(timeEntryRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(userId, timeEntry));
    }

    @Test
    @DisplayName("When time entry exists should update info")
    void update_shouldUpdateCategory() throws ResourceNotFoundException {
        // given
        var userEntity = new UserEntity(1L, "email@email.com", "name", "password");
        var user = userMapper.toModel(userEntity);
        var category = new Category(10L, "cat name", "cat desc");
        var categoryEntity = new CategoryEntity(10L, "cat name", "cat desc", userEntity);
        var timeEntry = new TimeEntry(100L, LocalDateTime.now(), LocalDateTime.now(), "ann", category);
        var entity = timeMapper.toEntity(timeEntry);
        entity.setCategoryEntity(categoryEntity);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(user);
        Mockito.when(timeEntryRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(entity));
        Mockito.when(timeEntryRepo.save(Mockito.any(TimeEntryEntity.class))).thenReturn(entity);
        // when
        var result = service.update(user.getId(), timeEntry);
        // then
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("When time entry does not belongs to informed user should delete category")
    void delete_shouldThrowError() {
        var userEntity = new UserEntity(1L, "email@email.com", "name", "password");
        var otherUserEntity = new UserEntity(2L, "email@email.com", "name", "password");
        var user = userMapper.toModel(userEntity);
        var categoryEntity = new CategoryEntity(10L, "cat name", "cat desc", otherUserEntity);
        var timeEntity = new TimeEntryEntity(100L, LocalDateTime.now(), LocalDateTime.now(), "ann", categoryEntity);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(user);
        Mockito.when(timeEntryRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(timeEntity));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(user.getId(), timeEntity.getId()));
    }

    @Test
    @DisplayName("When time entry exists should delete category")
    void delete_shouldDeleteTimeEntry() {
        var userEntity = new UserEntity(1L, "email@email.com", "name", "password");
        var user = userMapper.toModel(userEntity);
        var categoryEntity = new CategoryEntity(10L, "cat name", "cat desc", userEntity);
        var timeEntity = new TimeEntryEntity(100L, LocalDateTime.now(), LocalDateTime.now(), "ann", categoryEntity);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(user);
        Mockito.when(timeEntryRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(timeEntity));
        service.delete(1L, 10L);
    }

}