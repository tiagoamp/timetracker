package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.UserRequestDTO;
import br.com.tiagoamp.timetracker.error.ResourceAlreadyRegisteredException;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.mapper.UserMapperImpl;
import br.com.tiagoamp.timetracker.model.TimeTrackerException;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.service.UserService;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static br.com.tiagoamp.timetracker.util.JsonUtil.toJson;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackageClasses = {UserMapper.class, UserMapperImpl.class})  // allows mapstruct mappers dep injection
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserMapperImpl userMapper;

    @Autowired
    UserController userController;


    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("When Post request with invalid user Should result validation messages")
    public void whenPostRequestToUsers_messagesResponse() throws Exception {
        UserRequestDTO invalidUserReq = new UserRequestDTO();
        String userJson = toJson(invalidUserReq);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/user")
            .content(userJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title", is("ValidationException")))
            .andExpect(jsonPath("$.details.name").exists())
            .andExpect(jsonPath("$.details.password").exists())
            .andExpect(jsonPath("$.details.email").exists());
    }

    @Test
    @DisplayName("When Post request with valid user Should result correct response")
    public void whenPostRequestToUsers_correctResponse() throws Exception {
        UserRequestDTO userReq = new UserRequestDTO("email@email.com","User Test","password");
        String userJson = toJson(userReq);
        User user = userMapper.toModel(userReq);
        user.setId(1L);
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/user")
            .content(userJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$._links", is(not(emptyArray()))));
    }

    @Test
    @DisplayName("When Put request with invalid user Should result validation messages")
    public void whenPutRequestToUsers_messagesResponse() throws Exception {
        UserRequestDTO invalidUserReq = new UserRequestDTO();
        String userJson = toJson(invalidUserReq);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/{id}", 1L)
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("ValidationException")))
                .andExpect(jsonPath("$.details.name").exists())
                .andExpect(jsonPath("$.details.password").exists())
                .andExpect(jsonPath("$.details.email").exists());
    }

    @Test
    @DisplayName("When Put request with valid user Should result correct response")
    public void whenPutRequestToUsers_correctResponse() throws Exception {
        UserRequestDTO userReq = new UserRequestDTO("email@email.com","User Test","password");
        String userJson = toJson(userReq);
        User user = userMapper.toModel(userReq);
        user.setId(1L);
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/{id}", user.getId())
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$._links", is(not(emptyArray()))));
    }

    @Test
    @DisplayName("When Delete request with non-existing id Should result error message")
    public void whenDelRequestToUsers_messagesResponse() throws Exception {
        Mockito.doThrow(new TimeTrackerException("Message")).when(userService).delete(Mockito.anyLong());
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/{id}", "id-test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When Delete request of existing id Should delete user")
    public void whenDelRequestToUsers_correctResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/{id}", "id-test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("When Get All request and no Users registered Should result empty list")
    public void whenGetAllRequestToUsers_emptyResponse() throws Exception {
        Mockito.when(userService.findUsers()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    @DisplayName("When Get All request Should return registered users list")
    public void whenGetAllRequestToUsers_correctResponse() throws Exception {
        var registeredUsers = Arrays.asList(new User(), new User());
        Mockito.when(userService.findUsers()).thenReturn(registeredUsers);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(registeredUsers.size())));
    }

    @Test
    @DisplayName("When Get by id request with non-existing user Should return error message")
    public void whenGetByIdRequestToUsers_messagesResponse() throws Exception {
        Mockito.doThrow(new TimeTrackerException("Message")).when(userService).findUserById(Mockito.anyLong());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When Get by id request of existing user Should result user")
    public void whenGetByIdRequestToUsers_correctResponse() throws Exception {
        Mockito.when(userService.findUserById(Mockito.anyLong())).thenReturn(new User(1L, "email@email.com", "name", "pass"));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{id}", "id-test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }


}