package com.example.service.Impl;

import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.payload.dto.UserDTO;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found with email");
        }
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        User user= userRepository.findById(id).orElseThrow(
                ()->new Exception("User Not Found with given Id"+id)

        );
        return UserMapper.toDTO(user);

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users= userRepository.findAll();
        return UserMapper.toDTOList(users);
    }
}
