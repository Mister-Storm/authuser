package com.misterstorm.ead.authuser.controllers;

import com.misterstorm.ead.authuser.models.UserModel;
import com.misterstorm.ead.authuser.models.dto.UserInput;
import com.misterstorm.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserModel> registerUser(@RequestBody UserInput userInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userInput));
    }
}
