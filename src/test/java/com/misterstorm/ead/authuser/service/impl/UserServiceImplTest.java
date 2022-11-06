package com.misterstorm.ead.authuser.service.impl;

import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.models.dto.UserInput;
import com.misterstorm.ead.authuser.repositories.UserRepository;
import com.misterstorm.ead.authuser.service.exception.EmailAlreadyExistsException;
import com.misterstorm.ead.authuser.service.exception.UserNotFoundException;
import com.misterstorm.ead.authuser.service.exception.UsernameAlreadyExistsException;
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

    @Test
    @DisplayName("shouldnt save a user when username already exists in database")
    void testInsertUserWithTheSameUsername() {
        when(userRepository.existsByUsername(any(String.class))).thenReturn(Boolean.TRUE);
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.save(getUserInput()));
        Mockito.verify(userRepository, times(0)).save(any(UserModel.class));

    }

    @Test
    @DisplayName("shouldnt save a user when email already exists in database")
    void testInsertUserWithTheSameEmail() {
        when(userRepository.existsByUsername(any(String.class))).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(Boolean.TRUE);
        assertThrows(EmailAlreadyExistsException.class, () -> userService.save(getUserInput()));
        Mockito.verify(userRepository, times(0)).save(any(UserModel.class));

    }
    @Test
    @DisplayName("should save a new user")
    void testInsertNewUser() {
        when(userRepository.existsByUsername(any(String.class))).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(Boolean.FALSE);
        var user = userService.save(getUserInput());
        Mockito.verify(userRepository, times(1)).save(any(UserModel.class));
        assertEquals(getUserInput().getUsername(), user.getUsername());
        assertEquals(getUserInput().getEmail(), user.getEmail());
        assertNotNull(user.getUserStatus());
        assertNotNull(user.getUserType());
        assertNotNull(user.getCreationDate());
        assertNotNull(user.getLastUpdateDate());

    }

    private List<UserModel> createUserList() {
        return List.of(new UserModel.Builder().anUser().withUsername("User1").build(),
                new UserModel.Builder().anUser().withUsername("User2").build(),
                new UserModel.Builder().anUser().withUsername("User3").build());
    }
    private static UserInput getUserInput() {
        var user =  new UserInput();
        user.setUsername("User1");
        user.setEmail("user1@user.com");
        return user;
    }


}