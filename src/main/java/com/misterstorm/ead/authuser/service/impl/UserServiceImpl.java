package com.misterstorm.ead.authuser.service.impl;

import com.misterstorm.ead.authuser.enums.UserStatus;
import com.misterstorm.ead.authuser.enums.UserType;
import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.models.dto.UserInput;
import com.misterstorm.ead.authuser.repositories.UserRepository;
import com.misterstorm.ead.authuser.service.UserService;
import com.misterstorm.ead.authuser.service.exception.EmailAlreadyExistsException;
import com.misterstorm.ead.authuser.service.exception.UserNotFoundException;
import com.misterstorm.ead.authuser.service.exception.UsernameAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException ("User " + userId + " not found"));
    }

    @Override
    public UserModel deleteById(UUID userId) {
        UserModel user = this.findById(userId);
        userRepository.delete(user);
        return user;
    }

    @Override
    public UserModel save(UserInput userInput) {
        if(userRepository.existsByUsername(userInput.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + userInput.getUsername() + " already exists!");
        }
        if(userRepository.existsByEmail(userInput.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + userInput.getEmail() + " already exists!");
        }
        LocalDateTime actualDate = LocalDateTime.now(ZoneId.of("UTC"));
        var userModel = userInput.createUserModel();
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(actualDate);
        userModel.setLastUpdateDate(actualDate);
        userRepository.save(userModel);
        return userModel;
    }
}
