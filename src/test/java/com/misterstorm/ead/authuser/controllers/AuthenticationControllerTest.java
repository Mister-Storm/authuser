package com.misterstorm.ead.authuser.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.models.dto.UserInput;
import com.misterstorm.ead.authuser.service.UserService;
import com.misterstorm.ead.authuser.service.exception.EmailAlreadyExistsException;
import com.misterstorm.ead.authuser.service.exception.UsernameAlreadyExistsException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    private static final String USER_NAME = "User1";
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "123333";
    public static final UUID ID = UUID.randomUUID();
    public static final String MESSAGE_ERROR_USERNAME = "Username Already Exists!";
    private static final String MESSAGE_ERROR_EMAIL = "Email Already Exists!";
    private static ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return status created and the json with the representation of the user " +
            "when user not exists in database")
    void testGetAllUsers() throws Exception {
        UserInput userInput = new UserInput();
        userInput.setId(ID);
        userInput.setUsername(USER_NAME);
        userInput.setPassword(PASSWORD);
        userInput.setEmail(EMAIL);
        String json = mapper.writeValueAsString(userInput);
        when(userService.save(Mockito.any(UserInput.class))).thenReturn(new UserModel.Builder()
                .anUser()
                .withId(ID)
                .withUsername(USER_NAME)
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .build());
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(ID.toString())))
                .andExpect(jsonPath("$.username", Matchers.equalTo(USER_NAME)))
                .andExpect(jsonPath("$.email", Matchers.equalTo(EMAIL)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @DisplayName("should return status conflict and the json with the representation of error " +
            "when the username already exists")
    void testPostWithDontExistingUsername() throws Exception {
        UserInput userInput = new UserInput();
        userInput.setId(ID);
        userInput.setUsername(USER_NAME);
        userInput.setPassword(PASSWORD);
        userInput.setEmail(EMAIL);
        String json = mapper.writeValueAsString(userInput);
        when(userService.save(Mockito.any(UserInput.class))).thenThrow(new UsernameAlreadyExistsException(MESSAGE_ERROR_USERNAME));
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is(MESSAGE_ERROR_USERNAME)))
                .andExpect(jsonPath("$.statusCode", is("409 CONFLICT")))
                .andExpect(jsonPath("$.description", is(MESSAGE_ERROR_USERNAME)));
    }

    @Test
    @DisplayName("should return status conflict and the json with the representation of error " +
            "when the email already exists")
    void testPostWithDontExistingEmail() throws Exception {
        UserInput userInput = new UserInput();
        userInput.setId(ID);
        userInput.setUsername(USER_NAME);
        userInput.setPassword(PASSWORD);
        userInput.setEmail(EMAIL);
        String json = mapper.writeValueAsString(userInput);
        when(userService.save(Mockito.any(UserInput.class))).thenThrow(new EmailAlreadyExistsException(MESSAGE_ERROR_EMAIL));
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is(MESSAGE_ERROR_EMAIL)))
                .andExpect(jsonPath("$.statusCode", is("409 CONFLICT")))
                .andExpect(jsonPath("$.description", is(MESSAGE_ERROR_EMAIL)));
    }


}