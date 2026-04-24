package com.example.mapper;

import com.example.model.User;
import com.example.payload.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .fullname(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .lastlogin(user.getLastlogin())
                .phone(user.getPhone())
                .build();
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
