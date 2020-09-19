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
    private UserService userService;
    @Mock
    private CategoryService categoryService;

    @Spy  // injects this specific instance to target class
    private TimeEntryMapper timeMapper = Mappers.getMapper(TimeEntryMapper.class);

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

}