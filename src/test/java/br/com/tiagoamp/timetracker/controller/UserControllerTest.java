package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.UserRequestDTO;
import br.com.tiagoamp.timetracker.mapper.UserMapper;
import br.com.tiagoamp.timetracker.mapper.UserMapperImpl;
import br.com.tiagoamp.timetracker.model.User;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static br.com.tiagoamp.timetracker.util.JsonUtil.toJson;

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
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
    }

    @Test
    @DisplayName("When Post request with valid user Should result correct response")
    public void whenPostRequestToUsers_correctResponse() throws Exception {
        UserRequestDTO userReq = new UserRequestDTO("email@email.com","User Test","password");
        String userJson = toJson(userReq);
        User user = userMapper.toModel(userReq);
        user.setId("id-test");
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/user")
            .content(userJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("When Put request with invalid user Should result validation messages")
    public void whenPutRequestToUsers_messagesResponse() throws Exception {
        UserRequestDTO invalidUserReq = new UserRequestDTO();
        String userJson = toJson(invalidUserReq);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/{id}", "id-test")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
    }

    @Test
    @DisplayName("When Put request with valid user Should result correct response")
    public void whenPutRequestToUsers_correctResponse() throws Exception {
        UserRequestDTO userReq = new UserRequestDTO("email@email.com","User Test","password");
        String userJson = toJson(userReq);
        User user = userMapper.toModel(userReq);
        user.setId("id-test");
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/{id}", user.getId())
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

}