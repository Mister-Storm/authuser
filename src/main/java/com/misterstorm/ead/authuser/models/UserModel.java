package com.misterstorm.ead.authuser.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.misterstorm.ead.authuser.enums.UserStatus;
import com.misterstorm.ead.authuser.enums.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.FIELD)
    private UUID id;
    @Access(AccessType.FIELD)
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Access(AccessType.FIELD)
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Access(AccessType.FIELD)
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Access(AccessType.FIELD)
    @Column(nullable = false, length = 150)
    private String fullName;
    @Access(AccessType.FIELD)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Access(AccessType.FIELD)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Access(AccessType.FIELD)
    @Column(length = 20)
    private String phoneNumber;
    @Access(AccessType.FIELD)
    @Column(length = 20)
    private String cpf;
    @Access(AccessType.FIELD)
    @Column
    private String imageUrl;
    @Access(AccessType.FIELD)
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @Access(AccessType.FIELD)
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }
    public static class Builder {
        private UserModel userModel;

        public Builder() {
            this.userModel = new UserModel();
        }
        public Builder anUser() {
            return this;
        }
        public Builder withId(UUID id) {
            this.userModel.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.userModel.username = username;
            return this;
        }
        public Builder withEmail(String email) {
            this.userModel.email = email;
            return this;
        }
        public Builder withPassword(String password) {
            this.userModel.password = password;
            return this;
        }
        public Builder withFullName(String fullName) {
            this.userModel.fullName = fullName;
            return this;
        }
        public Builder withPhoneNumber(String phoneNumber) {
            this.userModel.phoneNumber = phoneNumber;
            return this;
        }
        public Builder withCpf(String cpf) {
            this.userModel.cpf = cpf;
            return this;
        }
        public Builder withImageUrl(String imageUrl) {
            this.userModel.imageUrl = imageUrl;
            return this;
        }
        public Builder withUserStatus(UserStatus userStatus) {
            this.userModel.userStatus = userStatus;
            return this;
        }
        public Builder withUserType(UserType userType) {
            this.userModel.userType = userType;
            return this;
        }
        public Builder withCreationDate(LocalDateTime creationDate) {
            this.userModel.creationDate = creationDate;
            return this;
        }
        public Builder withLastUpdateDate(LocalDateTime lastUpdateDate) {
            this.userModel.lastUpdateDate = lastUpdateDate;
            return this;
        }
        public UserModel build() {
            return this.userModel;
        }
    }

}
