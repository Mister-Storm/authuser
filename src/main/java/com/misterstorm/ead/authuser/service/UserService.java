package com.misterstorm.ead.authuser.service;

import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.models.dto.UserInput;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    UserModel findById(UUID uuid);

    UserModel deleteById(UUID uuid);

    UserModel save(UserInput userInput);
}
