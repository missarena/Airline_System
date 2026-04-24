package com.example.service.Impl;

import com.example.config.JwtProvider;
import com.example.enums.UserRole;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.payload.dto.UserDTO;
import com.example.payload.response.AuthResponse;
import com.example.repository.UserRepository;
import com.example.service.AuthService;
import com.example.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    // private final AuthService authService;

    /*
        1.check if email already exists
        2. Encode password using BCrypt
        3.Save user in db
        4.Generate JWT token
        5.return token and user information
     */

    @Override
    public AuthResponse signup(UserDTO req) throws Exception {
        User existingUser=userRepository.findByEmail(req.getEmail());
        if(existingUser!=null){
            throw new Exception("email already register");
        }
        if(req.getRole()== UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("You cannot sign up system admins!");
        }
        User newUser=User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(req.getRole())
                .fullName(req.getFullname())
                .lastlogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser=userRepository.save(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),savedUser.getPassword()
        );

        String jwt= jwtProvider.generateToken(
                authentication,savedUser.getId()
        );
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(savedUser));
        authResponse.setTitle("Welcome "+savedUser.getFullName());
        authResponse.setMessage("Registered Successfully");

         return authResponse;
    }

    //1.load user by email
    //2.Compare password with BCrypt
    //3.Update 'lastlogin time
    //4.Generate JWt token
    //Return token and user info

    @Override
    public AuthResponse login(String email, String password) throws Exception {

        Authentication authentication = authenticate(email, password);

        User user = userRepository.findByEmail(email);
        user.setLastlogin(LocalDateTime.now());
        userRepository.save(user);

        String jwt = jwtProvider.generateToken(authentication, user.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(user));
        authResponse.setTitle("Welcome " + user.getFullName());
        authResponse.setMessage("Registered Successfully!");

        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws Exception {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(
                password, userDetails.getPassword()
        )) {
            throw new Exception("invalid password!");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }


}
