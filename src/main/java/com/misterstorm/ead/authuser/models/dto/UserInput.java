package com.misterstorm.ead.authuser.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.misterstorm.ead.authuser.models.UserModel;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInput {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private String oldPassword;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserModel createUserModel() {
        return new UserModel.Builder()
                .anUser()
                .withId(this.id)
                .withUsername(this.username)
                .withEmail(this.email)
                .withPassword(this.password)
                .withFullName(this.fullName)
                .withPhoneNumber(this.phoneNumber)
                .withCpf(this.cpf)
                .withImageUrl(this.imageUrl)
                .build();
    }
}
