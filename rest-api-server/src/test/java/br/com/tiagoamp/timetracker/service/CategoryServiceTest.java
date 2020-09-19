package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.CategoryMapper;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.CategoryEntity;
import br.com.tiagoamp.timetracker.repository.CategoryRepository;
import br.com.tiagoamp.timetracker.repository.TimeEntryEntity;
import br.com.tiagoamp.timetracker.repository.TimeEntryRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepo;
    @Mock
    private TimeEntryRepository timeRepo;
    @Mock
    private UserService userService;

    @Spy  // injects this specific instance to target class
    private CategoryMapper catMapper = Mappers.getMapper(CategoryMapper.class);

    @InjectMocks
    private CategoryService service;


    @Test
    @DisplayName("When user does not exist should throw exception")
    void createCategory_shouldThrowError1() {
        var user = new User(1L,"not-exist@email.com", "Name", "");
        var category = new Category("cat name", "cat desc");
        Mockito.when(userService.findUserById(user.getId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.createCategory(user.getId(), category));
    }

    @Test
    @DisplayName("When category name already exist should throw exception")
    void createCategory_shouldThrowError2() {
        var user = new User(1L,"email@email.com", "Name", "");
        var category = new Category("cat name", "cat desc");
        var categoryEntity = catMapper.toEntity(category);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(user);
        Mockito.when(categoryRepo.retrieveByUser(user.getId())).thenReturn(Arrays.asList(categoryEntity));
        assertThrows(ResourceAlreadyRegisteredException.class, () -> service.createCategory(user.getId(), category));
    }

    @Test
    @DisplayName("When new category should create category")
    void createCategory_shouldCreateCategory() {
        // given
        var user = new User(1L, "user@email.com", "Name", "");
        var category = new Category("cat name", "cat desc");
        var categoryEntity = catMapper.toEntity(category);
        categoryEntity.setId(10L);
        Mockito.when(userService.findUserById(user.getId())).thenReturn(user);
        Mockito.when(categoryRepo.retrieveByUser(user.getId())).thenReturn(new ArrayList<>());
        Mockito.when(categoryRepo.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);
        // when
        var result = service.createCategory(user.getId(), category);
        // then
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("When user does not exist for update should throw exception")
    void updateCategory_shouldThrowError1() {
        var category = new Category("cat name", "cat desc");
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, category));
    }

    @Test
    @DisplayName("When category does not exist for update should throw exception")
    void updateCategory_shouldThrowError2() {
        var userId = 1L;
        var category = new Category("cat name", "cat desc");
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(categoryRepo.retrieveByUser(userId)).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> service.update(userId, category));
    }

    @Test
    @DisplayName("When category exists should update info")
    void update_shouldUpdateCategory() throws ResourceNotFoundException {
        // given
        var userId = 1L;
        var category = new Category(10L, "cat name", "cat desc");
        var categoryEntity = catMapper.toEntity(category);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(categoryRepo.retrieveByUser(userId)).thenReturn(Arrays.asList(categoryEntity));
        Mockito.when(categoryRepo.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);
        // when
        var result = service.update(userId, category);
        // then
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("When category associated to time entries should not delete (exception)")
    void deleteCategory_shouldThrowError() {
        var categoryEntity = new CategoryEntity(10L, "name", "desc", null);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(categoryRepo.retrieveByUser(Mockito.anyLong())).thenReturn(Arrays.asList(categoryEntity));
        Mockito.when(timeRepo.retrieveByCategory(Mockito.anyLong())).thenReturn(Arrays.asList(new TimeEntryEntity()));
        assertThrows(TimeTrackerOperationException.class, () -> service.delete(1L, 10L));
    }

    @Test
    @DisplayName("When category id exists and is not associate to time entries should delete category")
    void delete_shouldDeleteCategory() {
        var categoryEntity = new CategoryEntity(10L, "name", "desc", null);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(categoryRepo.retrieveByUser(Mockito.anyLong())).thenReturn(Arrays.asList(categoryEntity));
        Mockito.when(timeRepo.retrieveByCategory(Mockito.anyLong())).thenReturn(new ArrayList<>());
        service.delete(1L, 10L);
    }

    @Test
    @DisplayName("When does not exist categories should return empty list")
    void findCategories_shouldReturnEmptyList() {
        Mockito.when(categoryRepo.retrieveByUser(Mockito.anyLong())).thenReturn(new ArrayList<>());
        var result = service.findCategories(1L);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("When categories exist should return categories list")
    void findCategories_shouldReturnList() {
        var categoriesEntities = Arrays.asList(new CategoryEntity(), new CategoryEntity());
        Mockito.when(categoryRepo.retrieveByUser(Mockito.anyLong())).thenReturn(categoriesEntities);
        var result = service.findCategories(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("When category id does not exist should throw exception")
    void findCategoryById_shouldThrowError() {
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(categoryRepo.retrieveByUser(Mockito.anyLong())).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> service.findCategoryById(1L, 10L));
    }

    @Test
    @DisplayName("When category id exists should return category")
    void findCategoryById_shouldReturnUser() {
        // given
        final Long categoryId = 10L;
        var category = new Category(categoryId, "name", "desc");
        var categoryEntity = catMapper.toEntity(category);
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(categoryRepo.retrieveByUser(Mockito.anyLong())).thenReturn(Arrays.asList(categoryEntity));
        // when
        var result = service.findCategoryById(1L, categoryId);
        // then
        assertEquals(category, result);
        assertNotNull(result.getId());
    }

}