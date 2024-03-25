package com.cafe.com.cafe.management.dto;

import com.cafe.com.cafe.management.Entity.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Userdto {
    @NotEmpty(message = "id cannot be empty")
    @NotNull(message = "id cannot be null")
    private Integer id;
    @NotEmpty(message = "username cannot be empty")
    private String username;
    private String contactnumber;
    private String email;
    private String password;
    private String status;
    private Role role;
}
