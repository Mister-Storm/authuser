package com.misterstorm.ead.authuser.service.impl;

import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.repositories.UserRepository;
import com.misterstorm.ead.authuser.service.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepository);
    }
    @Test
    @DisplayName("should return all database users")
    void testWithAllUsers() {
        List<UserModel> userList = this.createUserList();
        when(userRepository.findAll()).thenReturn(userList);
        assertIterableEquals(userList, userService.findAll());
    }

    @Test
    @DisplayName("should return a user when existent in database")
    void testWithOneExistentUser() {
        UserModel user = new UserModel.Builder().anUser().withUsername("User1").build();
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        assertEquals(user, userService.findById(uuid));
    }

    @Test
    @DisplayName("should throw an exception when user inexistent in database")
    void testWithOneInexistentUser() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(uuid));
    }

    @Test
    @DisplayName("should return a user when existent in database")
    void testDeleteWithOneExistentUser() {
        UserModel user = new UserModel.Builder().anUser().withUsername("User1").build();
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        userService.deleteById(uuid);
        Mockito.verify(userRepository, times(1)).delete(any(UserModel.class));
    }

    @Test
    @DisplayName("should throw an exception when user inexistent in database")
    void testDeleteWithOneInexistentUser() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deleteById(uuid));
    }

    private List<UserModel> createUserList() {
        return List.of(new UserModel.Builder().anUser().withUsername("User1").build(),
                new UserModel.Builder().anUser().withUsername("User2").build(),
                new UserModel.Builder().anUser().withUsername("User3").build());
    }


}