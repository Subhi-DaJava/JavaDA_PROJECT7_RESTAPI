package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserSaveDTO {
    private Integer userId;
    @Size(max = 125)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @Size(max = 125)
    @NotBlank(message = "UserName is mandatory")
    private String username;
    @Size(max = 125)
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Size(max = 125)
    @NotBlank(message = "Role is mandatory")
    private String role;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
