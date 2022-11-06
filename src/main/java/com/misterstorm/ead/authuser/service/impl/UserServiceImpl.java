package com.misterstorm.ead.authuser.service.impl;

import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.repositories.UserRepository;
import com.misterstorm.ead.authuser.service.UserService;
import com.misterstorm.ead.authuser.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

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
}
