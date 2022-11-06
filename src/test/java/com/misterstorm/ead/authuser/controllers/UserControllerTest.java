package com.misterstorm.ead.authuser.controllers;

import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.service.UserService;
import com.misterstorm.ead.authuser.service.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    public static final String USER_NAME = "User1";
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("given a request to the users endpoint " +
            "then should return status ok and the json with the representation of the user")
    void testGetAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("given a request to the users endpoint with a specific id " +
            "when the user exists in the database, " +
            "then should return status ok and the json with the representation of the user")
    void testGetWithExistingUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(userService.findById(uuid)).thenReturn(new UserModel.Builder().anUser().withUsername(USER_NAME).build());
        mockMvc.perform(get("/users/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USER_NAME)));
    }

    @Test
    @DisplayName("given a request to the users endpoint with a specific id " +
            "when the user do not exists in the database, " +
            "then should return status 404 and the json with the representation of error")
    void testGetWithDontExistingUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        String exceptionMessage = getExceptionMessage(uuid);
        when(userService.findById(uuid)).thenThrow(new UserNotFoundException(exceptionMessage));
        mockMvc.perform(get("/users/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(exceptionMessage)))
                .andExpect(jsonPath("$.statusCode", is("404 NOT_FOUND")))
                .andExpect(jsonPath("$.description", is(exceptionMessage)));
    }

    @Test
    @DisplayName("given a request to delete users endpoint with a specific id " +
            "when the user exists in the database, " +
            "then should return status noContent ")
    void testDeleteWithExistingUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(delete("/users/" + uuid))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("given a request to delete users endpoint with a specific id " +
            "when the user do not exists in the database, " +
            "then should return status 404 and the json with the representation of error")
    void testDeleteWithDontExistingUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        String exceptionMessage = getExceptionMessage(uuid);
        when(userService.deleteById(uuid)).thenThrow(new UserNotFoundException(exceptionMessage));
        mockMvc.perform(delete("/users/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(exceptionMessage)))
                .andExpect(jsonPath("$.statusCode", is("404 NOT_FOUND")))
                .andExpect(jsonPath("$.description", is(exceptionMessage)));
    }


    private static String getExceptionMessage(UUID uuid) {
        return "User " + uuid + " not found";
    }
}