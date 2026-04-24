package com.example.payload.dto;


import com.example.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String fullname;
    private String phone;
    private UserRole role;
    private LocalDateTime lastlogin;

}
