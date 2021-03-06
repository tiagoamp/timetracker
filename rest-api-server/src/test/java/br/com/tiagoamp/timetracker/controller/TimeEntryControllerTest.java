package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.TimeEntryRequestDTO;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapperImpl;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.security.AuthorizationRules;
import br.com.tiagoamp.timetracker.service.CategoryService;
import br.com.tiagoamp.timetracker.service.TimeService;
import br.com.tiagoamp.timetracker.service.TokenService;
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

import java.time.LocalDateTime;
import java.util.Arrays;

import static br.com.tiagoamp.timetracker.util.JsonUtil.toJson;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackageClasses = {TimeEntryMapper.class, TimeEntryMapperImpl.class})  // allows mapstruct mappers dep injection
class TimeEntryControllerTest {

    @MockBean
    private TimeService timeService;
    @MockBean
    private UserService userService;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private AuthorizationRules auth;

    @Autowired
    private TimeEntryMapperImpl timeMapper;

    @Autowired
    TimeEntryController timeController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("When Post request with invalid time entry Should result validation messages")
    public void whenPostRequestToUsers_messagesResponse() throws Exception {
        TimeEntryRequestDTO invalidTimeReq = new TimeEntryRequestDTO();
        String timeJson = toJson(invalidTimeReq);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/user/{userId}/time", 1L)
            .content(timeJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title", is("ValidationException")))
            .andExpect(jsonPath("$.details.categoryId").exists())
            .andExpect(jsonPath("$.details.startTime").exists());
    }

    @Test
    @DisplayName("When Post request with valid time entry Should result correct response")
    public void whenPostRequestToUsers_correctResponse() throws Exception {
        TimeEntryRequestDTO timeReq = new TimeEntryRequestDTO(10L, LocalDateTime.now(), LocalDateTime.now(), null);
        String timeJson = toJson(timeReq);
        TimeEntry entry = timeMapper.toModel(timeReq);
        entry.setCategory(new Category(10L, "cat name", "cat desc"));
        entry.setId(100L);
        Mockito.when(timeService.create(Mockito.anyLong(), Mockito.any(TimeEntry.class))).thenReturn(entry);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/user/{userid}/time", 1L)
            .content(timeJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.userId").exists())
            .andExpect(jsonPath("$._links", is(not(emptyArray()))))
            .andExpect(jsonPath("$.categoryId").exists())
            .andExpect(jsonPath("$.categoryName").exists())
            .andExpect(jsonPath("$.durationInMinutes").exists());
    }

    @Test
    @DisplayName("When Put request with valid time entry Should result correct response")
    public void whenPutRequestToTimeEntry_correctResponse() throws Exception {
        TimeEntryRequestDTO entryReq = new TimeEntryRequestDTO(10L, LocalDateTime.now(), LocalDateTime.now(), "ann");
        String timeJson = toJson(entryReq);
        TimeEntry timeEntry = timeMapper.toModel(entryReq);
        timeEntry.setCategory(new Category(10L, "cat name", "cat desc"));
        timeEntry.setId(100L);
        final Long userId = 1L;
        Mockito.when(timeService.update(Mockito.anyLong(), Mockito.any(TimeEntry.class))).thenReturn(timeEntry);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/{userId}/time/{timeId}", userId, timeEntry.getId())
                .content(timeJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$._links", is(not(emptyArray()))))
                .andExpect(jsonPath("$.categoryId").exists())
                .andExpect(jsonPath("$.categoryName").exists())
                .andExpect(jsonPath("$.durationInMinutes").exists());
    }

    @Test
    @DisplayName("When Delete time entry request of existing id Should delete entry")
    public void whenDelRequestToTimeEntry_correctResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/{userId}/time/{timeId}", 1L, 100L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("When Get time entries of user request Should return list")
    public void whenGetRequestToTimeEntryOfUser_correctResponse() throws Exception {
        var entries = Arrays.asList(new TimeEntry(), new TimeEntry());
        Mockito.when(timeService.findTimeEntriesOfUser(Mockito.anyLong())).thenReturn(entries);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{userId}/time", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(entries.size())));
    }

    @Test
    @DisplayName("When Get request by id Should result correct response")
    public void whenGetRequestById_correctResponse() throws Exception {
        Mockito.when(timeService.findTimeEntryById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(new TimeEntry());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{userid}/time/{timeId}", 1L, 100L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}