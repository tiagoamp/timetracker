package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.CategoryRequestDTO;
import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.CategoryMapper;
import br.com.tiagoamp.timetracker.mapper.CategoryMapperImpl;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.service.CategoryService;
import br.com.tiagoamp.timetracker.service.TimeService;
import br.com.tiagoamp.timetracker.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static br.com.tiagoamp.timetracker.util.JsonUtil.toJson;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackageClasses = { CategoryMapper.class, CategoryMapperImpl.class})  // allows mapstruct mappers dep injection
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private UserService userService;
    @MockBean
    private TimeService timeService;

    @Autowired
    private CategoryMapperImpl categoryMapper;

    @Autowired
    CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("When Post request with invalid category Should result validation messages")
    public void whenPostRequestToCategories_messagesResponse() throws Exception {
        CategoryRequestDTO invalidCategoryReq = new CategoryRequestDTO();
        String catJson = toJson(invalidCategoryReq);
        final Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/{userId}/category", userId)
                .content(catJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("ValidationException")))
                .andExpect(jsonPath("$.details.name").exists());
    }

    @Test
    @DisplayName("When Post request with valid category Should result correct response")
    public void whenPostRequestToCategories_correctResponse() throws Exception {
        CategoryRequestDTO categoryReq = new CategoryRequestDTO("cat name", "cat description");
        String catJson = toJson(categoryReq);
        Category category = categoryMapper.toModel(categoryReq);
        category.setId(10L);
        final Long userId = 1L;
        Mockito.when(categoryService.createCategory(Mockito.anyLong(), Mockito.any(Category.class))).thenReturn(category);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/{userId}/category", userId)
                .content(catJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$._links", is(not(emptyArray()))));
    }

    @Test
    @DisplayName("When Put request with valid category Should result correct response")
    public void whenPutRequestToCategories_correctResponse() throws Exception {
        CategoryRequestDTO categoryReq = new CategoryRequestDTO("cat name", "cat description");
        String catJson = toJson(categoryReq);
        Category category = categoryMapper.toModel(categoryReq);
        category.setId(10L);
        final Long userId = 1L;
        Mockito.when(categoryService.update(Mockito.anyLong(), Mockito.any(Category.class))).thenReturn(category);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/{userId}/category/{categoryId}", userId, category.getId())
                .content(catJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$._links", is(not(emptyArray()))));
    }

    @Test
    @DisplayName("When Delete category request with category associated to time entry Should result error message")
    public void whenDelRequestToCategory_messagesResponse() throws Exception {
        Mockito.doThrow(new TimeTrackerOperationException("Message")).when(categoryService).delete(Mockito.anyLong(), Mockito.anyLong());
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/{userId}/category/{categoryId}", 1L, 10L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("TimeTrackerOperationException")));
    }

    @Test
    @DisplayName("When Delete category request of existing id Should delete category")
    public void whenDelRequestToCategory_correctResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/{userId}/category/{categoryId}", 1L, 10L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("When Get All request and no Categories registered Should result empty list")
    public void whenGetAllRequestToCategories_emptyResponse() throws Exception {
        Mockito.when(categoryService.findCategories(Mockito.anyLong())).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{userId}/category", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    @DisplayName("When Get All request Should return registered categories list")
    public void whenGetAllRequestToCategories_correctResponse() throws Exception {
        var registeredCategories = Arrays.asList(new Category(), new Category());
        Mockito.when(categoryService.findCategories(Mockito.anyLong())).thenReturn(registeredCategories);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{userId}/category", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(registeredCategories.size())));
    }

    @Test
    @DisplayName("When Get by id request with non-existing category Should return error message")
    public void whenGetByIdRequestToCategories_messagesResponse() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Message")).when(categoryService).findCategoryById(Mockito.anyLong(), Mockito.anyLong());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{userId}/category/{categoryId}", 1L, 10L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", is("ResourceNotFoundException")));
    }

    @Test
    @DisplayName("When Get by id request of existing category Should result user")
    public void whenGetByIdRequestToCategories_correctResponse() throws Exception {
        Mockito.when(categoryService.findCategoryById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(new Category(10L, "name", "desc"));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{userId}/category/{categoryId}", 1L, 10L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$._links", is(not(emptyArray()))));;
    }

}