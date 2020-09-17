package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.CategoryMapper;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

    @Mock
    private TimeEntryRepository timeEntryRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private CategoryRepository categoryRepo;

    @Spy  // injects this specific instance to target class
    private TimeEntryMapper timeMapper = Mappers.getMapper(TimeEntryMapper.class);
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Spy
    private CategoryMapper catMapper = Mappers.getMapper(CategoryMapper.class);

    @InjectMocks
    private TimeService service;


    @Test
    @DisplayName("When user does not exist should throw exception")
    void create_shouldThrowError1() {
        var category = new Category(10L, "name", "description");
        var user = new User(1L, "email@email.com", "name", "passwd");
        var entry = new TimeEntry(null, LocalDateTime.now(), LocalDateTime.now(), "annot", category, user);
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(entry));
    }

    @Test
    @DisplayName("When category does not exist should throw exception")
    void create_shouldThrowError2() {
        var category = new Category(10L, "name", "description");
        var user = new User(1L, "email@email.com", "name", "passwd");
        var userEntity = userMapper.toEntity(user);
        var entry = new TimeEntry(null, LocalDateTime.now(), LocalDateTime.now(), "annot", category, user);
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(userEntity));
        Mockito.when(categoryRepo.retrieveByUser(user.getId())).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> service.create(entry));
    }

    @Test
    @DisplayName("When correct time entry should create time entry")
    void create_shouldCreateTimeEntry() {
        // given
        var userEntity = new UserEntity(1L, "email@email.com", "name", "passwd");
        var categoryEntity = new CategoryEntity(10L, "name", "description");
        categoryEntity.setUser(userEntity);
        var timeEntryEntity = new TimeEntryEntity(100L, LocalDateTime.now(), LocalDateTime.now(), "annot", categoryEntity, userEntity);
        var entry = timeMapper.toModel(timeEntryEntity);
        Mockito.when(userRepo.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        Mockito.when(categoryRepo.retrieveByUser(userEntity.getId())).thenReturn(Arrays.asList(categoryEntity));
        Mockito.when(timeEntryRepo.save(Mockito.any(TimeEntryEntity.class))).thenReturn(timeEntryEntity);
        // when
        var result = service.create(entry);
        // then
        assertNotNull(result.getId());
        assertNotNull(result.getCategory().getId());
        assertNotNull(result.getUser().getId());
    }

}